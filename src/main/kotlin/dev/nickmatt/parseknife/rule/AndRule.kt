package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor

/**
 * Passes if all its child rules pass consecutively
 *
 * Whitespace-insensitivity is the default
 * This is accomplished by consuming any whitespace before every child
 * see AndRule#withWhitespaceSensitivity
 */
open class AndRule(
    vararg _children: Any
): Rule() {

    private val children = _children.map { infer(it) }
    private var whitespaceSensitive = false

    override fun test(cursor: Cursor) = cursor.branch {
        children.map {
            if (!whitespaceSensitive)
                cursor.consume(RegexRule.WHITESPACE)
            cursor.consume(it)
        }.toTypedArray()
    }

    /**
     * Prevents this rule from consuming whitespace before each of its children
     */
    fun withWhitespaceSensitivity(): AndRule {
        whitespaceSensitive = true
        return this
    }

    override fun toString(): String {

        var result = "(${children.joinToString(" ")})"

        if (whitespaceSensitive)
            result += "^"

        return result
    }

}