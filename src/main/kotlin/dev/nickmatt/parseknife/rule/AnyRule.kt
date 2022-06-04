package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.ParseKnifeError
import dev.nickmatt.parseknife.Token

open class AnyRule(
    _length: Int? = null
): Rule() {

    private val length = _length ?: 1

    override fun test(cursor: Cursor): Token {
        if (cursor.source.text.length - cursor.index < length)
            throw ParseKnifeError(cursor, "Expected $length more characters")
        return cursor.makeToken(length)
    }

    override fun toString() =
        length.toString()

}