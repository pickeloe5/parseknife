package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.Token
import dev.nickmatt.parseknife.error.UnexpectedCharacterError

class CharacterRule(
    private vararg val whitelist: Char
): Rule() {

    companion object {
        val WHITESPACE = CharacterRule(' ', '\t', '\n')
        val ALPHASCORE = CharacterRule(*"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_".toCharArray())
    }

    override fun test(c: Cursor): Token {
        val received = c[0]
        if (received == null || !whitelist.contains(received))
            throw UnexpectedCharacterError(c, received, *whitelist)
        return c.makeToken()
    }

    override fun toString() =
        if (whitelist.size == 1) "'${whitelist[0]}'"
        else "('${whitelist.joinToString("'|'")}')"

}