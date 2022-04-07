package dev.nickmatt.parseknife

import dev.nickmatt.parseknife.rule.Rule

data class Cursor(
    val source: String,
    var index: Int = 0
) {

    val length get() = source.length

    operator fun get(i: Int) =
        source.getOrNull(index + i)

    operator fun plusAssign(i: Int) {
        index += i
    }

    fun consume(r: Rule) {
        this += r.test(this)
    }

    inline fun branch(meth: () -> Unit): Int {
        val start = index
        meth()
        val end = index

        index = start
        return end - start
    }

    fun makeCoords(): Pair<Int, Int> {
        var lineBreakIndex = 0
        var line = 1
        for (i in 0 until index)
            if (source[i] == '\n') {
                lineBreakIndex = i
                line++
            }
        return Pair(line, index + 1 - lineBreakIndex)
    }

}