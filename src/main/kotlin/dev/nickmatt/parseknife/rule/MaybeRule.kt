package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.error.ParseKnifeError

class MaybeRule(
    _root: Any
): Rule {

    private val root = Rule.infer(_root)

    override fun test(c: Cursor) = try {
        root.test(c)
    } catch (e: ParseKnifeError) {
        0
    }

}