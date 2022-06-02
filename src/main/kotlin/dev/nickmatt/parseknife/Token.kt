package dev.nickmatt.parseknife

import dev.nickmatt.parseknife.error.UnexpectedTokenError

data class Token(
    val index: Int,
    val value: String
) {

    val meta = mutableMapOf<String, Any>()
    val children = mutableListOf<Token>()

    fun withMeta(moreMeta: Map<String, Any>): Token {
        for ((key, value) in moreMeta)
            meta[key] = value
        return this
    }

    fun withChildren(moreChildren: List<Token>): Token {
        for (child in moreChildren)
            children.add(child)
        return this
    }

    fun expectMeta(c: Cursor, key: String, value: Any) {
        if (meta[key] != value)
            throw UnexpectedTokenError.makeFromMeta(c, this, key, value.toString())
    }

    fun queryByMetaOrNull(c: Cursor, key: String, value: Any, depth: Int? = null): Token? {
        if (meta[key] == value)
            return this
        if (depth == 0)
            return null
        for (child in children) return child.queryByMetaOrNull(
            c, key, value,
            if (depth == null) null else depth - 1
        ) ?: continue
        return null
    }

    private fun _queryAllByMeta(c: Cursor, key: String, value: Any, depth: Int? = null): Array<Token> {
        if (meta[key] == value)
            return arrayOf(this)
        if (depth == 0)
            return arrayOf()
        val result = mutableListOf<Token>()
        for (child in children) {
            val nextDepth = if (depth == null) null else depth - 1
            result.addAll(child._queryAllByMeta(c, key, value, nextDepth))
        }
        return result.toTypedArray()
    }

    fun queryAllByMeta(c: Cursor, key: String, value: Any, depth: Int? = null): Array<Token> {
        val result = _queryAllByMeta(c, key, value, depth)
        if (result.isEmpty())
            throw UnexpectedTokenError.makeFromQuery(c, this, key, value.toString())
        return result
    }

    private fun calculateHeight(offset: Int = 0): Int =
        offset + (this.children.map {it.calculateHeight(offset + 1)}.toIntArray().maxOrNull() ?: 0)

//    fun queryByMetaRolling(c: Cursor, key: String, value: Any, depth: Int = calculateHeight()): Token {
//
//    }

    fun queryByMeta(c: Cursor, key: String, value: Any, depth: Int? = null) = queryByMetaOrNull(c, key, value, depth) ?:
        throw UnexpectedTokenError.makeFromQuery(c, this, key, value.toString())

}