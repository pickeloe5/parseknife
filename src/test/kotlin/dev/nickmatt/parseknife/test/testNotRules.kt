package dev.nickmatt.parseknife.test

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.Source
import dev.nickmatt.parseknife.rule.ManyRule
import dev.nickmatt.parseknife.rule.NotRule

fun testNotRules() {

    val source = Source("aaab")

    val rule1 = NotRule('b')
    val token1 = rule1.makeToken(Cursor(source))
    assert(token1.children.isEmpty())
    assert(token1.value == source.text[0].toString())

    val rule2 = ManyRule(rule1)
    val token2 = rule2.makeToken(Cursor(source))
    assert(token2.children.size == 3)
    assert(token2.value == source[0, 3])

    println("Successfully tested NotRules")
}