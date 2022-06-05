package dev.nickmatt.parseknife.meta.rule

import dev.nickmatt.parseknife.rule.ThenRule
import dev.nickmatt.parseknife.rule.r

private val decorator = r.many(r.or('+', '*', '?', '!', '^'))

internal val term = ThenRule(
    termValue,
    r.maybe(decorator)
        .withMeta("ruleName", "decorator")
)
    .withWhitespaceSensitivity()
    .withMeta("ruleName", "term")