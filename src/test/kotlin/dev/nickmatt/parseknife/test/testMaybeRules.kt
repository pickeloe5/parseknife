package dev.nickmatt.parseknife.test

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.rule.MaybeRule

fun testMaybeRules() {
    val cursor = Cursor.make("a")
    val rule = MaybeRule(cursor[0])

    assert(rule.makeToken(cursor).value == "a") {"Expected rule to match 1 character at index 0"}

    cursor += 1
    assert(rule.makeToken(cursor).value.isEmpty()) {"Expected rule to match an empty string at index 1"}

    println("Successfully tested MaybeRules")
}