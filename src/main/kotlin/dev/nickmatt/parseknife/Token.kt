package dev.nickmatt.parseknife

data class Token(
    val source: Source,
    val index: Int,
    val value: String
) {

    inner class QueryFailedError:
        ParseKnifeError(this@Token, "Could not find child matching query")

    val meta = mutableMapOf<String, Any>()
    val children = mutableListOf<Token>()
    val length = value.length

    constructor(_source: Source, _index: Int, length: Int): this(_source, _index,
        _source.text.substring(_index until _index + length))

    fun withMeta(moreMeta: Map<String, Any>): Token {
        for ((key, value) in moreMeta)
            meta[key] = value
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

    fun query(depth: Int? = null, meth: (Token) -> Boolean): Token {
        var result: Token? = null
        walk(depth) {
            if (meth(it)) {
                result = it
                false
            } else null
        }
        return result
            ?: throw QueryFailedError()
    }

    fun queryAll(depth: Int? = null, meth: (Token) -> Boolean): Array<Token> {
        val result = mutableListOf<Token>()
        walk(depth) {
            if (meth(it)) {
                result.add(it)
                true
            } else null
        }
        if (result.isEmpty())
            throw QueryFailedError()
        return result.toTypedArray()
    }

}