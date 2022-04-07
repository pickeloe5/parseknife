package dev.nickmatt.parseknife.test

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.rule.CharacterRule
import dev.nickmatt.parseknife.rule.MaybeRule

fun testMaybeRules() {
    val cursor = Cursor("a")
    val rule = MaybeRule(CharacterRule(cursor.source[0]))

    assert(rule.test(cursor) == 1) {"Expected rule to match 1 character at index 0"}

    cursor += 1
    assert(rule.test(cursor) == 0) {"Expected rule to match an empty string at index 1"}

    println("Successfully tested MaybeRules")
}