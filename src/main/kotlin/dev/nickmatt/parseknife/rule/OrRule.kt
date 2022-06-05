package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.Token
import dev.nickmatt.parseknife.ParseKnifeError

/**
 * Passes when any of its provided children pass
 * Short-circuits upon hitting a passing rule
 */
open class OrRule(
    vararg _children: Any
): Rule() {

    private val children = _children.map { infer(it) }

    override fun test(cursor: Cursor): Token {

        val errors = mutableListOf<ParseKnifeError>()

        for (child in children) try {
            return cursor.makeToken(
                child.makeToken(cursor))

        } catch (e: ParseKnifeError) {
            errors.add(e)
        }
        throw errors.maxByOrNull { it.index }!!
    }

    override fun toString() =
        "(${children.joinToString(" | ")})"

}