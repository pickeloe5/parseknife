package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.ParseKnifeError

open class MaybeRule(
    _root: Any
): Rule() {

    private val root = infer(_root)

    override fun test(c: Cursor) = try {
        val child = root.makeToken(c)
        val t = c.makeToken(child.length)
        t.children.add(child)
        t
    } catch (e: ParseKnifeError) {
        c.makeToken(0)
    }

    override fun toString() =
        "$root?"

}