package dev.nickmatt.parseknife

import dev.nickmatt.parseknife.rule.Rule

/**
 * Represents a moving position within a source
 */
@ExperimentalJsExport
class Cursor(
    val source: Source,
    _index: Int? = null
) {

    companion object {
        fun make(source: String, index: Int? = null) =
            Cursor(Source(source), index)
    }

    var index = _index ?: 0

    /**
     * Gets the character at this index plus the given offset
     */
    operator fun get(offset: Int) =
        source.text.getOrNull(index + offset)?.toString()
            ?: throw ParseKnifeError(offset, "Unexpected end of source")

    /**
     * Convenience method for incrementing index
     */
    operator fun plusAssign(i: Int) {
        index += i
    }

    /**
     * Convenience method for testing a rule and moving past its token
     */
    fun consume(r: Rule): Token {
        val t = r.makeToken(this)
        index += t.value.length
        return t
    }

    /**
     * Convenience method for testing consecutive rules and grouping the resulting tokens
     */
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

    /**
     * Convenience method for grouping a series of Tokens as children of a new parent
     */
    @JsName("makeTokenFromChildren")
    fun makeToken(vararg children: Token): Token {
        val last = children.last()
        val result = Token.make(source, index,
            last.index + last.value.length - index)
        return result.withChildren(*children)
    }

    /**
     * Returns a token containing the next `length` characters
     */
    fun makeToken(length: Int = 1) =
        Token.make(source, index, length)

}