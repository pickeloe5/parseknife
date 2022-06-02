package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.error.ParseKnifeError

class MaybeRule(
    _root: Any
): Rule() {

    private val root = infer(_root)

    override fun test(c: Cursor) = try {
        root.makeToken(c)
    } catch (e: ParseKnifeError) {
        c.makeToken(0) // With Rule.wrap added, we can likely remove this empty token by default
    }

    override fun toString() =
        "$root?"

}