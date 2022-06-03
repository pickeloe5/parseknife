package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.Token
import dev.nickmatt.parseknife.ParseKnifeError

open class NotRule(
    _root: Any
): Rule() {
    private val root: Rule = infer(_root)

    override fun test(c: Cursor): Token {
        try {
            root.makeToken(c)
        } catch (e: ParseKnifeError) {
            return c.makeToken(1)
        }
        throw ParseKnifeError(c, this)
    }

    override fun toString() =
        "$root!"

}