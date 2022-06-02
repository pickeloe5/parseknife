package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.Token
import dev.nickmatt.parseknife.error.UnexpectedCharacterError

class EofRule: Rule() {
    override fun test(c: Cursor): Token {
        if (c.index != c.source.text.length)
            throw UnexpectedCharacterError(c)
        return makeToken(c.makeToken(0))
    }
}