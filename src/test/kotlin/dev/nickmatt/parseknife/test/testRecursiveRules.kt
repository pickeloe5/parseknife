package dev.nickmatt.parseknife.test

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.error.UnexpectedCharacterError
import dev.nickmatt.parseknife.rule.Rule
import dev.nickmatt.parseknife.rule.r
import java.lang.AssertionError

fun testRecursiveRules() {
    val cursor = Cursor("(a(a))")

    lateinit var gRule: Rule
    val rule = r.ref {
        r.many(r.or('a', gRule))
    }
    gRule = r('(', rule, ')')

    assert(rule.test(cursor) == cursor.length) {"Expected full source to pass rule's test"}
    cursor.index = 1
    assert(rule.test(cursor) == 4) {"Expected indices 1 through 4 to pass rule's test"}

    try {
        cursor.index = 4
        rule.test(cursor)
        assert(false) {"Expected test to fail at index 4"}
    } catch (e: Error) {
        if (e is AssertionError)
            throw e
        if (e !is UnexpectedCharacterError)
            return assert(false) {"Test should have failed with an UnexpectedCharacterError"}
        assert(e.index == 4) {"Test at index 4 should have failed at index 4"}
    }

    println("Successfully tested RecursiveRules")
}