package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.Token
import dev.nickmatt.parseknife.ParseKnifeError

open class ManyRule(
    _root: Any
): Rule() {

    private val root = infer(_root)

    override fun test(c: Cursor) =
            c.branch { ParseKnifeError.collectUntil {
        c.consume(root)
    } }

    override fun toString() =
        "$root+"

}