package dev.nickmatt.parseknife.test

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.Source
import dev.nickmatt.parseknife.rule.AnyRule

fun testAnyRules() {

    val source = Source("abc")

    val rule1 = AnyRule()
    val token1 = rule1.makeToken(Cursor(source))
    assert(token1.children.isEmpty())
    assert(token1.meta.isEmpty())
    assert(token1.value == source.text[0].toString())

    val rule2 = AnyRule(2)
    val token2 = rule2.makeToken(Cursor(source))
    assert(token2.children.isEmpty())
    assert(token2.meta.isEmpty())
    assert(token2.value == source[0..1])
}