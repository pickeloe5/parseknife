package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor

class ThenRule(
    vararg _children: Any
): Rule {

    private val children = _children.map { Rule.infer(it) }

    override fun test(c: Cursor) = c.branch {
        children.forEach(c::consume)
    }

}