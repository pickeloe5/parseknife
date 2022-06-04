package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor

open class ThenRule(
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

    fun withWhitespaceSensitivity(): ThenRule {
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