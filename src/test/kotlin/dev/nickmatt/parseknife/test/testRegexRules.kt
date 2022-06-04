package dev.nickmatt.parseknife.test

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.Source
import dev.nickmatt.parseknife.rule.RegexRule

fun testRegexRules() {
    val source = Source("abc")

    val rule1 = RegexRule(Regex("[a-z]"))
    val token1 = rule1.makeToken(Cursor(source))
    assert(token1.children.isEmpty())
    assert(token1.meta.isEmpty())
    assert(token1.value == source[0..0])

    val rule2 = RegexRule(Regex("a(?<groupName>b)c"))
    val token2 = rule2.makeToken(Cursor(source))
    assert(token2.meta.isEmpty())
    assert(token2.value == source[0..2])

    val token2Group = token2.query {
        it.meta["groupName"] == "groupName"
    }
    assert(token2Group.children.isEmpty())
    assert(token2Group.value == source[1..1])
}