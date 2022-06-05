package dev.nickmatt.parseknife

import dev.nickmatt.parseknife.rule.Rule

/**
 * Convenience class for error reporting
 * Most if not all errors thrown throughout parsing should extend this
 */
open class ParseKnifeError(
    val index: Int,
    _message: String,
): Error(_message) {

    companion object {

        fun makeMessage(index: Int, source: Source, message: String): String {
            val (line, column) = source.makeCoords(index)
            return "($line, $column) $message"
        }

        fun makeMessage(index: Int, source: Source, expected: Rule) =
            makeMessage(index, source, "Expected: $expected")

        inline fun doUntil(atLeastOnce: Boolean = true, meth: () -> Unit) {
            if (atLeastOnce)
                meth()
            while (true) try {
                meth()
            } catch (e: ParseKnifeError) {
                return
            }
        }

        inline fun<reified T> collectUntil(atLeastOnce: Boolean = true, meth: () -> T): Array<T> {
            val result = mutableListOf<T>()
            doUntil(atLeastOnce) { result.add(meth()) }
            return result.toTypedArray()
        }

        fun<T> doCatching(reject: ((ParseKnifeError) -> Unit)?, meth: () -> T) =
            try {
                meth()
            } catch (e: ParseKnifeError) {
                reject?.invoke(e)
                null
            }
    }

    constructor(_index: Int, _message: String, source: Source): this(_index,
        makeMessage(_index, source, _message))

    constructor(_index: Int, expected: Rule):
            this(_index, expected.toString())

    constructor(_index: Int, expected: Rule, source: Source): this(_index,
        makeMessage(_index, source, expected))

    constructor(token: Token, _message: String):
            this(token.index, _message, token.source)

    constructor(token: Token, expected: Rule):
            this(token.index, expected, token.source)

    constructor(cursor: Cursor, _message: String):
            this(cursor.index, _message, cursor.source)

    constructor(cursor: Cursor, expected: Rule):
            this(cursor.index, expected, cursor.source)

}