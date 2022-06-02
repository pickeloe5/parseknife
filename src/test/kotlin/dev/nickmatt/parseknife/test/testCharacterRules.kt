package dev.nickmatt.parseknife.test

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.error.UnexpectedCharacterError
import dev.nickmatt.parseknife.rule.r
import java.lang.AssertionError

fun testCharacterRules() {
    val cursor = Cursor("a")
    val rule = r(cursor[0]!!)

    val token = rule.makeToken(cursor)
    assert(token.value == "a") {"Should have matched exactly one character"}

    cursor.index++
    try {
        rule.makeToken(cursor)
        assert(false) {"Should have failed the character rule's test"}
    } catch (e: Error) {
        if (e is AssertionError)
            throw e
        assert(e is UnexpectedCharacterError) {"Should have thrown an UnexpectedCharacterError"}
    }

    println("Successfully tested CharacterRules")
}