package dev.nickmatt.parseknife.test

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.Source
import dev.nickmatt.parseknife.rule.RegexRule
import kotlin.js.Json

fun testRegexRules() {
    val source = Source("abc")

    val rule1 = RegexRule("[a-z]")
    val token1 = rule1.makeToken(Cursor(source))
    assert(token1.children.isEmpty())
    assert(token1.value == source[0, 1])

    val rule2 = RegexRule("a(?<groupName>b)c")
    val token2 = rule2.makeToken(Cursor(source))
    assert(token2.value == source[0, 3])

    val token2Group = (token2.meta["namedGroups"].unsafeCast<Json>())["groupName"] as String
    assert(token2Group == source[1, 1])

    println("Successfully tested RegexRules")
}