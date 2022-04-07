package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.error.UnexpectedCharacterError

class CharacterRule(
    private vararg val whitelist: Char
): Rule {

    override fun test(c: Cursor): Int {
        val received = c[0]
        if (received == null || !whitelist.contains(received))
            throw UnexpectedCharacterError(c, received, *whitelist)
        return 1
    }

}