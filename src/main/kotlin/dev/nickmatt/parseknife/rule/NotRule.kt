package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.Token
import dev.nickmatt.parseknife.ParseKnifeError

/**
 * Passes only when a given rule does not pass
 *
 * Returns a token of length 1
 */
@ExperimentalJsExport
open class NotRule(
    _root: Any
): Rule() {

    private val root: Rule = infer(_root)

    override fun test(cursor: Cursor): Token {

        try {
            root.makeToken(cursor)

        } catch (e: ParseKnifeError) {
            if (cursor.index >= cursor.source.text.length)
                throw ParseKnifeError(cursor, this)
            return cursor.makeToken(1)
        }

        throw ParseKnifeError(cursor, this)
    }

    override fun toString() =
        "$root!"

}