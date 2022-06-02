package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.Token

class ThenRule(
    vararg _children: Any
): Rule() {

    companion object {
        private val whitespace =
            MaybeRule(ManyRule(CharacterRule(' ', '\t', '\n')))
    }

    private val children = _children.map { infer(it) }
    private var whitespaceSensitive = false

    override fun test(c: Cursor): Token {
        val t = makeToken(c.branch {
            for (i in children.indices) {
                if (!whitespaceSensitive)
                    c += whitespace.makeToken(c).value.length
                it.add(c.consume(children[i]))

            }
        })
        return t
    }

    fun withWhitespaceSensitivity(): ThenRule {
        whitespaceSensitive = true
        return this
    }

    override fun toString() =
        "(${children.joinToString(" ")})"

}