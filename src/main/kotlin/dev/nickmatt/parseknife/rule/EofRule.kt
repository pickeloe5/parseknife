package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.Token
import dev.nickmatt.parseknife.ParseKnifeError

open class EofRule: Rule() {
    override fun test(c: Cursor): Token {
        if (c.index != c.source.text.length)
            throw ParseKnifeError(c, "Expected end of file")
        return c.makeToken(0)
    }
}