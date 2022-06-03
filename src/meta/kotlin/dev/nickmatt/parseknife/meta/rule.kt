package dev.nickmatt.parseknife.meta

import dev.nickmatt.parseknife.rule.CharacterRule
import dev.nickmatt.parseknife.rule.Rule
import dev.nickmatt.parseknife.rule.ThenRule
import dev.nickmatt.parseknife.rule.r

val expression: Rule = Rule.refer {
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

private val ruleName = r.many(CharacterRule.ALPHASCORE)
    .withMeta("ruleName", "ruleName")

private val integer = r.many(CharacterRule.DIGIT)
    .withMeta("ruleName", "integer")

private val character = run {
    val escape = '\\'
    val delimiter = '\''
    ThenRule(
        delimiter,
        r.or(
            ThenRule(
                escape,
                r.any()
            ).withWhitespaceSensitivity(),
            r.not(delimiter)
        ).withMeta("ruleName", "content"),
        delimiter
    )
}
    .withWhitespaceSensitivity()
    .withMeta("ruleName", "character")

private val group = r('(', expression, ')')
    .withMeta("ruleName", "group")

private val decorator = r.many(r.or('+', '*', '?', '!', '^'))
private val term = ThenRule(
    r.or(integer, character, ruleName, group)
        .withMeta("ruleName", "termValue"),
    r.maybe(decorator)
        .withMeta("ruleName", "decorator")
)
    .withWhitespaceSensitivity()
    .withMeta("ruleName", "term")

private val eof = r.eof()
    .withMeta("ruleName", "endOfFile")

val rule = run {
    r(
        r.many(
            r(ruleName, '=', expression, ';')
                .withMeta("ruleName", "rule")
        ).withMeta("ruleName", "language"),
        eof
    ).withMeta("ruleName", "file")
}