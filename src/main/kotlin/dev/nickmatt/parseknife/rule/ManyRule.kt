package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.ParseKnifeError

open class ManyRule(
    _root: Any
): Rule() {

    private val root = infer(_root)

    override fun test(cursor: Cursor) = cursor.branch {
        ParseKnifeError.collectUntil {
            cursor.consume(root)
        }
    }

    override fun toString() =
        "$root+"

}