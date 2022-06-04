package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.Token
import dev.nickmatt.parseknife.ParseKnifeError

open class EofRule: Rule() {
    override fun test(cursor: Cursor): Token {

        if (cursor.index != cursor.source.text.length)
            throw ParseKnifeError(cursor, "Expected end of file")

        return cursor.makeToken(0)
    }

    override fun toString() =
        "\$eof"
}