package dev.nickmatt.parseknife.meta

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.Source
import dev.nickmatt.parseknife.Token
import dev.nickmatt.parseknife.error.UnexpectedTokenError
import dev.nickmatt.parseknife.makeParse
import dev.nickmatt.parseknife.meta.error.BadDecoratorError
import dev.nickmatt.parseknife.meta.error.BadTermKind
import dev.nickmatt.parseknife.meta.error.UndefinedRuleError
import dev.nickmatt.parseknife.rule.Rule
import dev.nickmatt.parseknife.rule.ThenRule
import dev.nickmatt.parseknife.rule.r

private val termKinds = arrayOf("wildcard", "char", "group", "ruleName")

private class TransformTable(
    private val cursor: Cursor,
    language: Token
) {

    companion object {
        private val escapeCodes = mapOf(
            Pair("\\\\", '\\'),
            Pair("\\'", '\''),
            Pair("\\t", '\t'),
            Pair("\\n", '\n')
        )
    }

    private val ruleTokens = mutableMapOf<String, Token>()
    val rules = mutableMapOf<String, Rule>()

    init {
        for (it in language.children) {
            it.expectMeta(cursor, "ruleName", "rule")
            val name = it.queryByMeta(cursor, "ruleName", "ruleName", 1)
            val value = it.queryByMeta(cursor, "ruleName", "or", 1)
            ruleTokens[name.value] = value
        }
        ruleTokens.keys.forEach {addAttempt(it)}
    }

    private fun addAttempt(name: String, history: Array<String> = arrayOf()) {
        if (name in rules || name in history)
            return
        rules[name] = transformValue(
            ruleTokens[name] ?: throw UndefinedRuleError(name)
        , arrayOf(*history, name)).withMeta("ruleName", name)
    }

    private fun resolveReference(name: String, history: Array<String>): Rule {
        if (name !in rules && name !in history)
            addAttempt(name, history)
        return rules[name] ?: r.ref {rules[name]!!}
    }

    private fun transformTerm(parent: Token, history: Array<String>): Rule {
        val value = parent.queryByMeta(cursor, "ruleName", "termValue", 1)
            .children.toTypedArray().find {
                it.meta["ruleName"] in termKinds
            } ?: throw BadTermKind(cursor)
        val decorator = parent.queryByMetaOrNull(cursor, "ruleName", "decorator", 1)

        var result = when (value.meta["ruleName"]) {
            "wildcard" -> r.any()
            "char" -> {
                val content = value.queryByMeta(cursor, "ruleName", "content", 1).value
                r.char(escapeCodes[content] ?: content[0])
            }
            "group" -> transformValue(value, Array(history.size + 1) {
                if (it < history.size) history[it]
                else value.value
            })
            "ruleName" -> resolveReference(value.value, history)
            else -> throw BadTermKind(cursor)
        }
        if (decorator != null) for (c in decorator.value) result = when (c) {
            '?' -> r.maybe(result)
            '+' -> r.many(result)
            '!' -> r.not(result)
            '*' -> r.maybe(r.many(result))
            '^' -> (result as ThenRule).withWhitespaceSensitivity()
            else -> throw BadDecoratorError(decorator.value)
        }
        return result
    }

    private fun transformExpression(
        t: Token, termKey: String, makeTerm: (Token) -> Rule, makeExpression: (Array<Rule>) -> Rule
    ): Rule {
        val terms = t.queryAllByMeta(cursor, "ruleName", termKey)
        if (terms.size == 1)
            return makeTerm(terms[0])
        return makeExpression(terms.map(makeTerm).toTypedArray())
    }

    private fun transformValue(parent: Token, history: Array<String>): Rule {
        val value = parent.queryByMeta(cursor, "ruleName", "or", 1)
        return transformExpression(value, "and", { and ->
            transformExpression(and, "term", {
                transformTerm(it, history)
            }) {r.then(*it)}
        }) {r.or(*it)}
    }

}

val parse: (Source) -> Map<String, Rule> = makeParse(rule, ::transform)
fun parse(source: String) =
    parse(Source(source))
fun transform(c: Cursor, t: Token) =
    TransformTable(c, t.queryByMeta(c, "ruleName", "language")).rules.toMap()