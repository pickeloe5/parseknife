package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.Token
import dev.nickmatt.parseknife.ParseKnifeError

open class CharacterRule(
    private val expected: Char
): Rule() {

    companion object {
        private fun split(s: String) = s.map {CharacterRule(it)}.toTypedArray()
        val WHITESPACE = OrRule(*split(" \t\n"))
        val ALPHASCORE = OrRule(*split("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_"))
        val DIGIT = OrRule(*split("0123456789"))
    }

    override fun test(c: Cursor): Token {
        val received = c[0]
            ?: throw ParseKnifeError(c, "Unexpected end of source")
        if (received != expected)
            throw ParseKnifeError(c, this)
        return c.makeToken()
    }

    override fun toString() =
        "'$expected'"

}