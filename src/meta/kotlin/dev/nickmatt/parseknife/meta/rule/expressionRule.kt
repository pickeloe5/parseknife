package dev.nickmatt.parseknife.meta.rule

import dev.nickmatt.parseknife.rule.RegexRule
import dev.nickmatt.parseknife.rule.Rule
import dev.nickmatt.parseknife.rule.ThenRule
import dev.nickmatt.parseknife.rule.r

internal val expression: Rule = Rule.refer {
    val and = ThenRule(
        term, r.maybe(
            r.many(
                ThenRule(
                    RegexRule.WHITESPACE,
                    term
                ).withWhitespaceSensitivity()
            )))
        .withWhitespaceSensitivity()
        .withMeta("ruleName", "and")

    ThenRule(and, r.maybe(r.many(r('|', and))))
        .withWhitespaceSensitivity()
        .withMeta("ruleName", "or")
}