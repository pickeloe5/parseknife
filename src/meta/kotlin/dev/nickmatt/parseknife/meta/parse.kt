package dev.nickmatt.parseknife.meta

import dev.nickmatt.parseknife.Source
import dev.nickmatt.parseknife.Token
import dev.nickmatt.parseknife.ParseKnifeError
import dev.nickmatt.parseknife.makeParse
import dev.nickmatt.parseknife.rule.Rule
import dev.nickmatt.parseknife.rule.ThenRule
import dev.nickmatt.parseknife.rule.r

private val termKinds = arrayOf("integer", "character", "group", "ruleName")

private class BadTermKindError(token: Token): ParseKnifeError(token,
    "Expected term to be integer, character, group, or reference")

private class UndefinedRuleError(name: String): Error(
    "Tried to reference undefined rule: $name")

private operator fun Token.get(name: String) =
    query { it.meta["ruleName"] == name }
private fun Token.filter(name: String) =
    queryAll { it.meta["ruleName"] == name }

private val escapeCodes = mapOf(
    Pair("\\\\", '\\'),
    Pair("\\'", '\''),
    Pair("\\t", '\t'),
    Pair("\\n", '\n')
)

private class TransformTable(
    language: Token
) {

    private val ruleTokens = mutableMapOf<String, Token>()
    val rules = mutableMapOf<String, Rule>()

    init {
        val ruleTokenArray = language.filter("rule")
        for (t in ruleTokenArray) {
            val name = t["ruleName"].value
            ruleTokens[name] = t
        }
        ruleTokens.keys.forEach { addAttempt(it) }
    }

    // TODO - Move this lookup history and name into a "State" Pair
    private fun addAttempt(name: String, history: Array<String> = arrayOf()) {
        if (name in rules || name in history)
            return
        rules[name] = transformValue(
            ruleTokens[name]
                ?: throw UndefinedRuleError(name),
            arrayOf(*history, name)
        ).withMeta("ruleName", name)
    }

    private fun resolveReference(name: String, history: Array<String>): Rule {
        if (name !in rules && name !in history)
            addAttempt(name, history)
        return rules[name] ?: r.ref {rules[name]!!}
    }

    private fun transformTermValue(value: Token, history: Array<String>) =
            when (value.meta["ruleName"]) {
        "integer" -> r.any(Integer.parseInt(value.value))
        "character" -> {
            val content = value["content"].value
            r.char(escapeCodes[content] ?: content[0])
        }
        "group" -> transformValue(value, Array(history.size + 1) {
            if (it < history.size) history[it]
            else value.value
        })
        "ruleName" -> try {
            resolveReference(value.value, history)
        } catch (e: UndefinedRuleError) {
            throw ParseKnifeError(value, e.message!!)
        }
        else -> throw value.QueryFailedError()
    }

    private fun transformTermDecorator(rule: Rule, decorator: Token): Rule {
        var result = rule
        if (decorator.value.isEmpty())
            return result
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

    private fun transformTerm(parent: Token, history: Array<String>): Rule {
        val value = parent["termValue"].children.find {
            it.meta["ruleName"] in termKinds
        }!!
        val result = transformTermValue(value, history)
        return transformTermDecorator(result, parent["decorator"])
    }

    private fun transformExpression(
        t: Token, termKey: String, makeTerm: (Token) -> Rule
    ): Array<Rule> {
        val terms = t.filter(termKey)
        if (terms.size == 1)
            return arrayOf(makeTerm(terms[0]))
        return terms.map(makeTerm).toTypedArray()
    }

    private fun transformValue(parent: Token, history: Array<String>): Rule {
        val value = parent["or"]
        val ands = transformExpression(value, "and") { and ->
            val terms = transformExpression(and, "term") {
                transformTerm(it, history)
            }
            if (terms.size == 1)
                terms[0]
            else
                r.then(*terms)
        }
        if (ands.size == 1)
            return ands[0]
        return r.or(*ands)
    }

}

val parse: (Source) -> Map<String, Rule> = makeParse(rule, ::transform)

fun parse(source: String) =
    parse(Source(source))

fun transform(t: Token) = TransformTable(
    t["language"]
).rules.toMap()