package dev.nickmatt.parseknife

import dev.nickmatt.parseknife.rule.Rule

class Cursor(
    val source: Source,
    _index: Int? = null
) {

    var index = _index ?: 0

    constructor(sourceText: String, _index: Int? = null):
            this(Source(sourceText), _index)

    operator fun get(i: Int) =
        source.text.getOrNull(index + i)
            ?: throw ParseKnifeError(i, "Unexpected end of source")

    operator fun plusAssign(i: Int) {
        index += i
    }

    fun consume(r: Rule): Token {
        val t = r.makeToken(this)
        index += t.length
        return t
    }

    inline fun branch(meth: () -> Array<Token>): Token {
        val start = index
        val children: Array<Token>
        try {
            children = meth()
        } finally {
            index = start
        }
        return makeToken(*children)
    }

    fun makeToken(vararg children: Token): Token {
        val last = children.last()
        val result = Token(source, index,
            last.index + last.length - index)
        return result.withChildren(*children)
    }

    fun makeToken(length: Int = 1) =
        Token(source, index, length)

}