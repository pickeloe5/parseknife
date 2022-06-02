package dev.nickmatt.parseknife.meta

import dev.nickmatt.parseknife.rule.CharacterRule
import dev.nickmatt.parseknife.rule.Rule
import dev.nickmatt.parseknife.rule.ThenRule
import dev.nickmatt.parseknife.rule.r
import kotlin.math.exp

val name = r.many(CharacterRule.ALPHASCORE)
    .withMeta("ruleName", "ruleName")
val eof = r.eof()
    .withMeta("ruleName", "eof")

lateinit var recursiveExpression: Rule
val expression = Rule.refer {
    val escapedContent = ThenRule('\\', r.any())
        .withWhitespaceSensitivity()
    val char = ThenRule(
        '\'',
        r.or(escapedContent, r.not('\''))
            .withMeta("ruleName", "content"),
        '\''
    )
        .withWhitespaceSensitivity()
        .withMeta("ruleName", "char")

    val group = r('(', recursiveExpression, ')')
        .withMeta("ruleName", "group")
    val decorator = r.many(r.or('+', '*', '?', '!', '^'))
        .withMeta("ruleName", "decorator")
    val wildcard = r('$')
        .withMeta("ruleName", "wildcard")

    val term = ThenRule(
        r.or(wildcard, char, name, group)
            .withMeta("ruleName", "termValue"),
        r.maybe(decorator)
    )
        .withWhitespaceSensitivity()
        .withMeta("ruleName", "term")

    val and = ThenRule(
        term, r.maybe(
            r.many(
                ThenRule(CharacterRule.WHITESPACE, term).withWhitespaceSensitivity()
            )
        )
    )
        .withWhitespaceSensitivity()
        .withMeta("ruleName", "and")

    ThenRule(and, r.maybe(r.many(r('|', and))))
        .withWhitespaceSensitivity()
        .withMeta("ruleName", "or")
}

val rule = run {
    recursiveExpression = expression
    r(
        r.many(
            r(name, '=', expression, ';')
                .withMeta("ruleName", "rule")
        ).withMeta("ruleName", "language"),
        eof
    ).withMeta("ruleName", "file")
}