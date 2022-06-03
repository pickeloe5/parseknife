package dev.nickmatt.parseknife

import dev.nickmatt.parseknife.rule.Rule

fun makeParse(rule: Rule) = fun(source: Source) =
    rule.makeToken(Cursor(source))

fun<T> makeParse(rule: Rule, transform: (Token) -> T): (Source) -> T {
    val parse = makeParse(rule)
    return fun(source: Source) =
        transform(parse(source))
}