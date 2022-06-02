package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor

class AnyRule(
    _length: Int? = null
): Rule() {

    private val length = _length ?: 1

    override fun test(c: Cursor) =
        c.makeToken(length)

    override fun toString() =
        "$"

}