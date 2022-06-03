package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor

open class ThenRule(
    vararg _children: Any
): Rule() {

    companion object {
        private val WHITESPACE = MaybeRule(ManyRule(CharacterRule.WHITESPACE))
    }

    private val children = _children.map { infer(it) }
    private var whitespaceSensitive = false

    override fun test(c: Cursor) = c.branch {
        children.map {
            if (!whitespaceSensitive)
                c.consume(WHITESPACE)
            c.consume(it)
        }.toTypedArray()
    }

    fun withWhitespaceSensitivity(): ThenRule {
        whitespaceSensitive = true
        return this
    }

    override fun toString(): String {
        val result = "(${children.joinToString(" ")})"
        return if (whitespaceSensitive) "$result^"
            else result
    }

}