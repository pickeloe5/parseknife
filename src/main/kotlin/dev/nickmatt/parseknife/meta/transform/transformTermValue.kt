package dev.nickmatt.parseknife.meta.transform

import dev.nickmatt.parseknife.ParseKnifeError
import dev.nickmatt.parseknife.QueryFailedError
import dev.nickmatt.parseknife.Token
import dev.nickmatt.parseknife.meta.UndefinedRuleError
import dev.nickmatt.parseknife.rule.CharacterRule
import dev.nickmatt.parseknife.rule.RuleHelper

private val r = RuleHelper.instance

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

internal fun TransformTable?.transformTermValue(value: Token) =
    when (value.meta["ruleName"]) {
        "endOfFile" -> r.eof()
        "integer" -> r.any(value.value.toInt())
        "character" -> {
            val content = value.queryRegexGroup("content")
            CharacterRule.make(ESCAPE_CODES[content] ?: content[0])
        }
        "string" -> {
            val content = value.queryRegexGroup("content")
                .replace(ESCAPE_REGEX) {
                    ESCAPE_CODES[it.value]?.toString()
                        ?: it.value
                }
            r(content)
        }
        "regex" -> {
            val content = value.queryRegexGroup("content")
                .replace(REGEX_ESCAPE_REGEX) {
                    if (it.value[1] == '/')
                        "/"
                    else it.value
                }
            r.regex(content)
        }
        "group" -> transformValue(value)
        "ruleName" -> {
            if (this == null)
                throw ParseKnifeError(value,
                    "Tried to reference a rule without the context of a table")
            try {
                resolveReference(value.value)
            } catch (e: UndefinedRuleError) {
                throw ParseKnifeError(value, e.message!!)
            }
        }
        else -> throw QueryFailedError(value)
    }