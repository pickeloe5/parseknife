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
        source[index + i]

    operator fun plusAssign(i: Int) {
        index += i
    }

    inline fun branch(meth: (MutableList<Token>) -> Unit): Token {
        val children = mutableListOf<Token>()
        val start = index
        val end: Int
        try {
            meth(children)
            end = index
        } finally {
            index = start
        }

        return makeToken(end - start)
            .withChildren(children)
    }

    fun makeToken(length: Int = 1) =
        source.makeToken(index until index + length)

    fun makeCoords(i: Int = index) =
        source.makeCoords(i)

    fun consume(rule: Rule): Token {
        val token = rule.makeToken(this)
        index += token.value.length
        return token
    }

    fun consume(rules: List<Rule>) =
        rules.map { consume(it) }

}