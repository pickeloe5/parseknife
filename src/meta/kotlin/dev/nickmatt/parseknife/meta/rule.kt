package dev.nickmatt.parseknife.meta

import dev.nickmatt.parseknife.rule.Rule
import dev.nickmatt.parseknife.rule.ThenRule
import dev.nickmatt.parseknife.rule.r

private val expression: Rule = Rule.refer {
    val and = ThenRule(
        term, r.maybe(
            r.many(
                ThenRule(whitespace, term).withWhitespaceSensitivity()
    )))
        .withWhitespaceSensitivity()
        .withMeta("ruleName", "and")

    ThenRule(and, r.maybe(r.many(r('|', and))))
        .withWhitespaceSensitivity()
        .withMeta("ruleName", "or")
}

private val whitespace = r.regex("\\s+")

private val ruleName = r.regex("[a-zA-Z_]+")
    .withMeta("ruleName", "ruleName")

private val eofTerm = r('$', 'e', 'o', 'f')
    .withMeta("ruleName", "endOfFile")

private val integer = r.regex("[0-9]+")
    .withMeta("ruleName", "integer")

private val character = r.regex("'(?<content>\\\\.|[^'])'")
    .withMeta("ruleName", "character")

private val string = r.regex("\"(?<content>(\\\\.|[^'])*)\"")
    .withMeta("ruleName", "string")

private val regex = r.regex("/(?<content>(\\\\.|[^/])*)/")
    .withMeta("ruleName", "regex")

private val group = r('(', expression, ')')
    .withMeta("ruleName", "group")

private val decorator = r.many(r.or('+', '*', '?', '!', '^'))
private val term = ThenRule(
    r.or(
        eofTerm,
        integer, character, string, regex,
        ruleName,
        group
    ).withMeta("ruleName", "termValue"),

    r.maybe(decorator)
        .withMeta("ruleName", "decorator")
)
    .withWhitespaceSensitivity()
    .withMeta("ruleName", "term")

private val endOfSource = r.eof()
    .withMeta("ruleName", "endOfFile")

val rule = run { r(
    r.many(

        r(ruleName, '=', expression, ';')
            .withMeta("ruleName", "rule")

    ).withMeta("ruleName", "language"),

    endOfSource

).withMeta("ruleName", "file") }