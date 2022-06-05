package dev.nickmatt.parseknife

/**
 * Represents a section of source
 *
 * Has structured, hierarchical children
 *
 * As well as metadata
 */
data class Token(
    val source: Source,
    val index: Int,
    val value: String
) {

    inner class QueryFailedError:
        ParseKnifeError(this@Token, "Could not find child matching query")

    inner class OrphanedTokenError:
        ParseKnifeError(this@Token, "Could not find parent for token")

    val meta = mutableMapOf<String, Any>()
    val children: MutableList<Token> = TokenChildren(this)

    var parentOrNull: Token? = null
    var parent: Token
        get() = parentOrNull ?: throw OrphanedTokenError()
        set(value) { parentOrNull = value }

    constructor(_source: Source, match: MatchResult):
            this(_source, match.range.first, match.value)

    constructor(_source: Source, group: MatchGroup):
            this(_source, group.range.first, group.value)

    constructor(_source: Source, _index: Int, length: Int):
            this(_source, _index, _source[_index until _index + length])

    /**
     * Builder pattern utility for adding children
     */
    fun withChildren(vararg tokens: Token): Token {
        children.addAll(tokens)
        return this
    }

    /**
     * Builder pattern utility for adding metadata
     */
    fun withMeta(k: String, v: String): Token {
        meta[k] = v
        return this
    }

    /**
     * Convenience method for deep, breadth-first traversal
     */
    private fun walk(maxDepth: Int? = null, meth: (Token) -> Boolean?) {
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

    /**
     * Walks children breadth-first running the given predicate
     *
     * Returns the first child for which the predicate returns true
     *
     * Or null if no children matched the predicate
     */
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

    /**
     * Walks children breadth-first running the given predicate
     *
     * Returns any children for which the predicate returns true
     *
     * Does not search children of tokens which matched the predicate
     */
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

    /**
     * Walks children breadth-first running the given predicate
     *
     * Returns the first child for which the predicate returns true
     *
     * Throwing Token#QueryFailedError if no tokens match the predicate
     */
    fun query(depth: Int? = null, meth: (Token) -> Boolean) =
        queryOrNull(depth, meth)
            ?: throw QueryFailedError()

    /**
     * Walks children breadth-first running the given predicate
     *
     * Returns all children for which the predicate returns true
     *
     * Throwing Token#QueryFailedError if no tokens match the predicate
     */
    fun queryMany(depth: Int? = null, meth: (Token) -> Boolean): Array<Token> {
        val result = queryAny(depth, meth)
        if (result.isEmpty())
            throw QueryFailedError()
        return result
    }

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