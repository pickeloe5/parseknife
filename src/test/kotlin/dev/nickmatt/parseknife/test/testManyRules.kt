package dev.nickmatt.parseknife.test

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.error.UnexpectedCharacterError
import dev.nickmatt.parseknife.rule.CharacterRule
import dev.nickmatt.parseknife.rule.ManyRule
import java.lang.AssertionError

fun testManyRules() {
    val cursor = Cursor("aab")
    val rule = ManyRule(CharacterRule('a'))

    assert(rule.test(cursor) == 2) {"Expected test to match first two characters"}
    cursor.index = 1
    assert(rule.test(cursor) == 1) {"Expected test at index 1 to match next character"}

    try {
        cursor.index = 2
        rule.test(cursor)
        assert(false) {"Expected test at index 2 to fail"}
    } catch (e: Error) {
        if (e is AssertionError)
            throw e
        if (e !is UnexpectedCharacterError)
            return assert(false) {"Test should have failed with an UnexpectedCharacterError"}
        assert(e.index == 2) {"Test at index 2 should have failed immediately"}
    }

    println("Successfully tested ManyRules")
}