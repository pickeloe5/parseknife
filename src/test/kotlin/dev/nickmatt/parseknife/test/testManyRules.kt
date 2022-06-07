package dev.nickmatt.parseknife.test

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.ParseKnifeError
import dev.nickmatt.parseknife.rule.ManyRule

fun testManyRules() {
    val cursor = Cursor.make("aab")
    val rule = ManyRule('a')

    assert(rule.makeToken(cursor).value == "aa") {"Expected test to match first two characters"}
    cursor.index = 1
    assert(rule.makeToken(cursor).value == "a") {"Expected test at index 1 to match next character"}

    try {
        cursor.index = 2
        rule.makeToken(cursor)
        assert(false) {"Expected test at index 2 to fail"}
    } catch (e: Error) {
        if (e is AssertionError)
            throw e
        if (e !is ParseKnifeError)
            return assert(false) {"Test should have failed with a ParseKnifeError"}
        assert(e.index == 2) {"Test at index 2 should have failed immediately"}
    }

    println("Successfully tested ManyRules")
}