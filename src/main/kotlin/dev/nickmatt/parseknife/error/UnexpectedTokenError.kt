package dev.nickmatt.parseknife.error

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.Token

open class UnexpectedTokenError(_cursor: Cursor, _message: String):
        ParseKnifeError(_cursor, _message) {

    companion object {
        fun makeFromMeta(c: Cursor, t: Token, key: String, vararg expectation: String) =
            UnexpectedTokenError(c, "Expected {meta:$key} to be `${expectation.joinToString("|")}`, received: `${t.meta[key]}`")

        fun makeFromQuery(c: Cursor, t: Token, key: String, vararg expectation: String) = UnexpectedTokenError(c,
            "Expected children~{meta:$key} to be `${expectation.joinToString("|")}`, received: `${t.meta[key]}`")
    }

}