package dev.nickmatt.parseknife.test

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.ParseKnifeError
import dev.nickmatt.parseknife.rule.r
import java.lang.AssertionError

fun testCharacterRules() {
    val cursor = Cursor("ab")
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
        assert(e is ParseKnifeError) {"Should have thrown a ParseKnifeError"}
    }

    println("Successfully tested CharacterRules")
}