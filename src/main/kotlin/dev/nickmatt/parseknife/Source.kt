package dev.nickmatt.parseknife

data class Source(
    val text: String
) {

    val length get() =
        text.length

    operator fun get(i: Int) =
        text.getOrNull(i)

    operator fun get(r: IntRange) =
        text.substring(r)

    fun makeToken(r: IntRange) =
        Token(r.first, this[r])

    fun makeCoords(index: Int): Pair<Int, Int> {
        var lineBreakIndex = 0
        var line = 1
        for (i in 0 until index)
            if (text[i] == '\n') {
                lineBreakIndex = i
                line++
            }
        return Pair(line, index + 1 - lineBreakIndex)
    }

}