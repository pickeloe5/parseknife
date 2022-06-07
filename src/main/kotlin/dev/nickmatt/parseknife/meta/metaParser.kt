@file:OptIn(ExperimentalJsExport::class)

package dev.nickmatt.parseknife.meta

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.Parser
import dev.nickmatt.parseknife.Token
import dev.nickmatt.parseknife.meta.transform.TransformTable
import dev.nickmatt.parseknife.meta.transform.transformValue
import dev.nickmatt.parseknife.rule.*

private val r = RuleHelper.instance

private object rExpression: Rule() {
    override fun test(cursor: Cursor) =
        cursor.makeToken(
            _expression.makeToken(
                cursor))
}

private val eofTerm = r("$", "e", "o", "f")
    .withMeta("ruleName", "endOfFile")

private val integer = r.regex("[0-9]+")
    .withMeta("ruleName", "integer")

private val character = r.regex("'(?<content>\\\\.|[^'])'")
    .withMeta("ruleName", "character")

private val string = r.regex("\"(?<content>(\\\\.|[^\"])*)\"")
    .withMeta("ruleName", "string")

private val regex = r.regex("/(?<content>(\\\\.|[^/])*)/")
    .withMeta("ruleName", "regex")

private val group = r("(", rExpression, ")")
    .withMeta("ruleName", "group")

private val ruleName = r.regex("[a-zA-Z_]+")
    .withMeta("ruleName", "ruleName")

private val termValue = r.or(
    eofTerm,
    integer, character, string, regex,
    ruleName,
    group
).withMeta("ruleName", "termValue")

private val decorator = r.many(r.or("+", "*", "?", "!", "^"))

private val term = AndRule(
    termValue,
    r.maybe(decorator)
        .withMeta("ruleName", "decorator")
)
    .withWhitespaceSensitivity()
    .withMeta("ruleName", "term")

private val _expression = run {
    val and = AndRule(
        term, r.maybe(
            r.many(
                AndRule(
                    RegexRule.WHITESPACE,
                    term
                ).withWhitespaceSensitivity()
            )))
        .withWhitespaceSensitivity()
        .withMeta("ruleName", "and")

    AndRule(and, r.maybe(r.many(r("|", and))))
        .withWhitespaceSensitivity()
        .withMeta("ruleName", "or")
}

private val language = run {
    r(
        r.many(

            r(ruleName, "=", rExpression, ";")
                .withMeta("ruleName", "rule")

        ).withMeta("ruleName", "language"),

        r.eof()
            .withMeta("ruleName", "endOfFile")

    ).withMeta("ruleName", "file")
}

/**
 * Returns a map of names to Rules, from source text
 */

object metaParser: Parser<RuleMap>(language) {

    override fun transform(token: Token) =
        TransformTable.makeRuleMap(token)

    fun language(rootRuleName: String, source: String) =
        Parser.make(this(source)[rootRuleName])

    fun expression(source: String) =
        (null as TransformTable?).transformValue(
            rExpression.makeToken(Cursor.make(source)))

}