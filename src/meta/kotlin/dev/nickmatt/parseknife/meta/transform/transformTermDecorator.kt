package dev.nickmatt.parseknife.meta.transform

import dev.nickmatt.parseknife.ParseKnifeError
import dev.nickmatt.parseknife.Token
import dev.nickmatt.parseknife.rule.Rule
import dev.nickmatt.parseknife.rule.AndRule
import dev.nickmatt.parseknife.rule.r

internal fun TransformTable.transformTermDecorator(rule: Rule, decorator: Token): Rule {
    var result = rule

    if (decorator.value.isEmpty())
        return result
    else makingReference = false

    for (i in decorator.value.indices)
        result = when (decorator.value[i]) {
            '?' -> r.maybe(result)
            '+' -> r.many(result)
            '!' -> r.not(result)
            '*' -> r.maybe(r.many(result))
            '^' -> (result as AndRule).withWhitespaceSensitivity()
            else -> throw ParseKnifeError(decorator,
                "Expected decorator ('?', '+', '!', etc.)")
        }

    return result
}