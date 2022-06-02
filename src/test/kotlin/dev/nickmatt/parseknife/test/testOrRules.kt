package dev.nickmatt.parseknife.test

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.rule.r

fun testOrRules() {
    val cursor = Cursor("abc")
    val rule = r.or('a', 'b')

    assert(rule.makeToken(cursor).value == "a") {"Expected test to pass with length of 1"}
    assert(cursor.index == 0) {"Expected test to not mutate location index"}

    cursor.index = 1
    assert(rule.makeToken(cursor).value == "b") {"Expected test to pass with length of 1"}
    assert(cursor.index == 1) {"Expected test to not mutate location index"}

    println("Successfully tested OrRules")
}