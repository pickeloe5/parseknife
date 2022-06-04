package dev.nickmatt.parseknife

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
    val length = value.length

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

    fun withChildren(vararg tokens: Token): Token {
        children.addAll(tokens)
        return this
    }

    fun withMeta(k: String, v: String): Token {
        meta[k] = v
        return this
    }

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

    fun query(depth: Int? = null, meth: (Token) -> Boolean) =
        queryOrNull(depth, meth)
            ?: throw QueryFailedError()

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