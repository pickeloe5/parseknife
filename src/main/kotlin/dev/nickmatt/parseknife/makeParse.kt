package dev.nickmatt.parseknife

import dev.nickmatt.parseknife.rule.Rule

fun makeParse(rule: Rule) = fun(source: Source) =
    rule.makeToken(Cursor(source))

fun<T> makeParse(rule: Rule, transform: (Cursor, Token) -> T): (Source) -> T {
    val parse = makeParse(rule)
    return fun(source: Source): T {
        val cursor = Cursor(source)
        return transform(cursor, rule.makeToken(cursor))
    }
}