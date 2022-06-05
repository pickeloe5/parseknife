package dev.nickmatt.parseknife.meta.rule

import dev.nickmatt.parseknife.rule.RegexRule
import dev.nickmatt.parseknife.rule.Rule
import dev.nickmatt.parseknife.rule.AndRule
import dev.nickmatt.parseknife.rule.r

internal val expression: Rule = Rule.refer {
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

    AndRule(and, r.maybe(r.many(r('|', and))))
        .withWhitespaceSensitivity()
        .withMeta("ruleName", "or")
}