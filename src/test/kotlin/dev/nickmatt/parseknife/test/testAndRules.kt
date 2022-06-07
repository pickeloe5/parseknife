package dev.nickmatt.parseknife.test

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.ParseKnifeError
import dev.nickmatt.parseknife.rule.AndRule

fun testAndRules() {
    val cursor = Cursor.make("ab")
    val badCursor = Cursor.make("ac")
    val rule = AndRule(cursor[0], cursor[1])
    assert(rule.makeToken(cursor).value == cursor.source.text) {"Expected test to return length of 2"}

    try {
        rule.makeToken(badCursor)
        assert(false) {"Expected test to fail for bad source"}
    } catch(e: Error) {
        if (e is AssertionError)
            throw e
        if (e !is ParseKnifeError)
            return assert(false) {"Expected failure with UnexpectedCharacterError"}
        assert(e.index == 1) {"Expected failure at index 1"}
    }

    println("Successfully tested AndRules")
}