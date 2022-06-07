package dev.nickmatt.parseknife

import kotlin.js.json

/**
 * Represents a section of source
 *
 * Has structured, hierarchical children
 *
 * As well as metadata
 */
@ExperimentalJsExport
data class Token(
    val source: Source,
    val index: Int,
    val value: String
) {

    companion object {
        fun make(source: Source, index: Int, length: Int) =
            Token(source, index, source[index, length])
    }

    val meta = json()

    var children = arrayOf<Token>()

    var parentOrNull: Token? = null
    var parent: Token
        get() = parentOrNull ?: throw OrphanedTokenError(this)
        private set(value) { parentOrNull = value }

    /**
     * Builder pattern utility for adding children
     */
    fun withChildren(vararg tokens: Token): Token {
        for (t in tokens)
            t.parent = this
        children += tokens
        return this
    }

    /**
     * Builder pattern utility for adding metadata
     */
    fun withMeta(k: String, v: Any?): Token {
        meta[k] = v
        return this
    }

    /**
     * Convenience method for deep, breadth-first traversal
     */
    @JsName("walkAtDepth")
    fun walk(maxDepth: Int? = null, meth: (Token) -> Boolean?) {
        var targets = arrayOf(this); val nextTargets = mutableListOf<Token>()
        var depth = 1
        while (!(maxDepth != null && depth > maxDepth)) {
            for (t in targets) for (c in t.children) {
                val result = meth(c)
                if (result == false)
                    return
                if (result == true)
                    continue
                nextTargets.add(c)
            }
            if (nextTargets.isEmpty())
                break
            targets = nextTargets.toTypedArray()
            nextTargets.clear()
            depth++
        }
    }

    @JsName("walk")
    fun jsWalk(meth: (Token) -> Boolean) =
        walk(null, meth)

    /**
     * Walks children breadth-first running the given predicate
     *
     * Returns the first child for which the predicate returns true
     *
     * Or null if no children matched the predicate
     */
    @JsName("queryOrNullAtDepth")
    fun queryOrNull(depth: Int? = null, meth: (Token) -> Boolean): Token? {
        var result: Token? = null
        walk(depth) {
            if (meth(it)) {
                result = it
                false
            } else null
        }
        return result
    }

    @JsName("queryOrNull")
    fun jsQueryOrNull(meth: (Token) -> Boolean) =
        queryOrNull(null, meth)

    /**
     * Walks children breadth-first running the given predicate
     *
     * Returns any children for which the predicate returns true
     *
     * Does not search children of tokens which matched the predicate
     */
    @JsName("queryAnyAtDepth")
    fun queryAny(depth: Int? = null, meth: (Token) -> Boolean): Array<Token> {
        val result = mutableListOf<Token>()
        walk(depth) {
            if (meth(it)) {
                result.add(it)
                true
            } else null
        }
        return result.toTypedArray()
    }

    @JsName("queryAny")
    fun jsQueryAny(meth: (Token) -> Boolean) =
        queryAny(null, meth)

    /**
     * Walks children breadth-first running the given predicate
     *
     * Returns the first child for which the predicate returns true
     *
     * Throwing Token#QueryFailedError if no tokens match the predicate
     */
    @JsName("queryAtDepth")
    fun query(depth: Int? = null, meth: (Token) -> Boolean) =
        queryOrNull(depth, meth)
            ?: throw QueryFailedError(this)

    @JsName("query")
    fun jsQuery(meth: (Token) -> Boolean) =
        query(null, meth)

    /**
     * Walks children breadth-first running the given predicate
     *
     * Returns all children for which the predicate returns true
     *
     * Throwing Token#QueryFailedError if no tokens match the predicate
     */
    @JsName("queryManyAtDepth")
    fun queryMany(depth: Int? = null, meth: (Token) -> Boolean): Array<Token> {
        val result = queryAny(depth, meth)
        if (result.isEmpty())
            throw QueryFailedError(this)
        return result
    }

    @JsName("queryMany")
    fun jsQueryMany(meth: (Token) -> Boolean) =
        queryMany(null, meth)

    override fun toString(): String {
        var result = "($index;$value) $meta"
        if (children.isEmpty())
            return result
        result += "\n${
            children.joinToString("\n")
        }".replace("\n", "\n\t")
        return result
    }

}