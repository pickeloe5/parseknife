package dev.nickmatt.parseknife.meta.rule

import dev.nickmatt.parseknife.rule.AndRule
import dev.nickmatt.parseknife.rule.r

private val decorator = r.many(r.or('+', '*', '?', '!', '^'))

internal val term = AndRule(
    termValue,
    r.maybe(decorator)
        .withMeta("ruleName", "decorator")
)
    .withWhitespaceSensitivity()
    .withMeta("ruleName", "term")