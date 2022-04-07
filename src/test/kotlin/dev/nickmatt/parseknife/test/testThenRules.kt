package dev.nickmatt.parseknife.test

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.error.ParseKnifeError
import dev.nickmatt.parseknife.rule.CharacterRule
import dev.nickmatt.parseknife.rule.ThenRule
import java.lang.AssertionError

fun testThenRules() {
    val cursor = Cursor("ab")
    val badCursor = Cursor("ac")
    val rule = ThenRule(
        CharacterRule(cursor[0]!!),
        CharacterRule(cursor[1]!!)
    )
    val length = rule.test(cursor)
    assert(length == 2) {"Expected test to return length of 2"}

    try {
        rule.test(badCursor)
        assert(false) {"Expected test to fail for bad source"}
    } catch(e: Error) {
        if (e is AssertionError)
            throw e
        if (e !is ParseKnifeError)
            return assert(false) {"Expected failure with UnexpectedCharacterError"}
        assert(e.index == 1) {"Expected failure at index 1"}
    }

    println("Successfully tested ThenRules")
}