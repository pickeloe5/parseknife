package dev.nickmatt.parseknife.meta.transform

import dev.nickmatt.parseknife.ParseKnifeError
import dev.nickmatt.parseknife.Token
import dev.nickmatt.parseknife.meta.RuleMap
import dev.nickmatt.parseknife.meta.TermKind
import dev.nickmatt.parseknife.meta.UndefinedRuleError
import dev.nickmatt.parseknife.rule.Rule
import dev.nickmatt.parseknife.rule.ThenRule
import dev.nickmatt.parseknife.rule.r

class TransformTable(
    language: Token
) {

    companion object {
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
    }

    private val ruleTokens = mutableMapOf<String, Token>()
    private val rules = mutableMapOf<String, Rule>()
    private val resolveHistory = mutableListOf<String>()

    // For wrapping rules that are only references to other rules
    // This is an uncommon use case, but a hidden killer
    // Token metadata would be changed in unpredictable ways
    // Because the resolved reference would itself be modified
    // Its metadata overwritten with that of the referencing rule
    private var makingReference = false

    init {
        for (t in language.queryMany("rule"))
            ruleTokens[t.query("ruleName").value] = t
        ruleTokens.keys.forEach { addAttempt(it) }
    }

    fun makeRuleMap() =
        RuleMap(rules)

    private fun addAttempt(name: String) {
        if (name in rules || name in resolveHistory)
            return
        resolveHistory.add(name)
        try {
            val definition = ruleTokens[name]
                ?: throw UndefinedRuleError(name)
            var value = transformValue(definition)
            if (makingReference)
                value = r.wrap(value)
            rules[name] = value
                .withMeta("ruleName", name)
        } finally {
            resolveHistory.removeLast()
        }
    }

    private fun resolveReference(name: String): Rule {
        if (name !in rules && name !in resolveHistory)
            addAttempt(name)
        return rules[name] ?: r.ref {rules[name]!!}
    }

    private fun transformTermValue(value: Token) =
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

    private fun transformTermDecorator(rule: Rule, decorator: Token): Rule {
        var result = rule
        if (decorator.value.isEmpty())
            return result
        makingReference = false
        for (i in decorator.value.indices) result = when (decorator.value[i]) {
            '?' -> r.maybe(result)
            '+' -> r.many(result)
            '!' -> r.not(result)
            '*' -> r.maybe(r.many(result))
            '^' -> (result as ThenRule).withWhitespaceSensitivity()
            else -> throw ParseKnifeError(decorator,
                "Expected decorator ('?', '+', '!', etc.)")
        }
        return result
    }

    private fun transformTerm(parent: Token): Rule {
        val value = parent.query("termValue").children.find {
            it.meta["ruleName"] in TermKind.ruleNames
        } ?: throw ParseKnifeError(parent, "Expected term value (e.g. int, char, string, etc.)")

        if (!makingReference && value.meta["ruleName"] == "ruleName")
            makingReference = true

        var result = transformTermValue(value)

        result = transformTermDecorator(result,
            parent.query("decorator"))

        return result
    }

    private fun transformExpression(
        t: Token, termKey: String, makeTerm: (Token) -> Rule
    ): Array<Rule> {
        val terms = t.queryMany(termKey)
        if (terms.size == 1)
            return arrayOf(makeTerm(terms[0]))
        return terms.map(makeTerm).toTypedArray()
    }

    private fun transformValue(parent: Token): Rule {
        val value = parent.query("or")

        val ands = transformExpression(value, "and") { and ->
            val terms = transformExpression(
                and, "term", ::transformTerm)

            if (terms.size != 1) {
                makingReference = false
                r.then(*terms)
            } else terms[0]
        }

        return if (ands.size != 1) {
            makingReference = false
            r.or(*ands)
        } else ands[0]
    }

}