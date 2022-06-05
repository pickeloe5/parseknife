package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.Token
import dev.nickmatt.parseknife.ParseKnifeError

/**
 * Passes only when the next character matches the expectation
 *
 * @param expected The character this rule should test for
 */
open class CharacterRule(
    private val expected: Char
): Rule() {
    override fun test(cursor: Cursor): Token {

        val received = cursor[0]

        if (received != expected)
            throw ParseKnifeError(cursor, this)

        return cursor.makeToken()
    }

    override fun toString() =
        "'$expected'"
}