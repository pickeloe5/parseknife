package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor

class ThenRule(
    private vararg val children: Rule
): Rule {

    override fun test(c: Cursor) = c.branch {
        children.forEach(c::consume)
    }

}