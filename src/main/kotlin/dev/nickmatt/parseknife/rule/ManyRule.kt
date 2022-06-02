package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.Token
import dev.nickmatt.parseknife.error.ParseKnifeError

class ManyRule(
    _root: Any
): Rule() {

    private val root = infer(_root)

    override fun test(c: Cursor) = makeToken(c.branch {
        ParseKnifeError.doUntil { it.add(c.consume(root)) }
    })

    override fun toString() =
        "$root+"

}