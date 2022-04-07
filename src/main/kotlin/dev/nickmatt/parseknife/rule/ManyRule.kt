package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.error.ParseKnifeError

class ManyRule(
    private val root: Rule
): Rule {

    override fun test(c: Cursor) = c.branch {
        ParseKnifeError.doUntil { c.consume(root) }
    }

}