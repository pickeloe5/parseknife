package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.error.ParseKnifeError

class ManyRule(
    _root: Any
): Rule {

    private val root = Rule.infer(_root)

    override fun test(c: Cursor) = c.branch {
        ParseKnifeError.doUntil { c.consume(root) }
    }

}