package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.ParseKnifeError

/**
 * Passes whether a given rule passes or not
 *
 * Returns a token of length 0 when its root does not pass
 */
open class MaybeRule(
    _root: Any
): Rule() {

    private val root = infer(_root)

    override fun test(cursor: Cursor) = try {

        cursor.makeToken(root.makeToken(cursor))

    } catch (e: ParseKnifeError) {

        cursor.makeToken(0)
    }

    override fun toString() =
        "$root?"

}