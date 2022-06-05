package dev.nickmatt.parseknife.meta.transform

import dev.nickmatt.parseknife.ParseKnifeError
import dev.nickmatt.parseknife.Token
import dev.nickmatt.parseknife.meta.UndefinedRuleError
import dev.nickmatt.parseknife.rule.r

private val REGEX_ESCAPE_REGEX = Regex("\\\\.")
private val ESCAPE_REGEX = Regex("\\\\(?<code>[tbnr'\"\\\\])")
private val ESCAPE_CODES = mapOf(
    Pair("\\t", '\t'),
    Pair("\\b", '\b'),
    Pair("\\n", '\n'),
    Pair("\\r", '\r'),
    Pair("\\'", '\''),
    Pair("\\\"", '"'),
    Pair("\\\\", '\\')
)

internal fun TransformTable.transformTermValue(value: Token) =
    when (value.meta["ruleName"]) {
        "endOfFile" -> r.eof()
        "integer" -> r.any(Integer.parseInt(value.value))
        "character" -> {
            val content = value.queryRegexGroup("content").value
            r.char(ESCAPE_CODES[content] ?: content[0])
        }
        "string" -> {
            val content = value.queryRegexGroup("content").value
                .replace(ESCAPE_REGEX) {
                    ESCAPE_CODES[it.value]?.toString()
                        ?: it.value
                }
            r(content)
        }
        "regex" -> {
            val content = value.queryRegexGroup("content").value
                .replace(REGEX_ESCAPE_REGEX) {
                    if (it.value[1] == '/')
                        "/"
                    else it.value
                }
            r.regex(content)
        }
        "group" -> transformValue(value)
        "ruleName" -> try {
            resolveReference(value.value)
        } catch (e: UndefinedRuleError) {
            throw ParseKnifeError(value, e.message!!)
        }
        else -> throw value.QueryFailedError()
    }