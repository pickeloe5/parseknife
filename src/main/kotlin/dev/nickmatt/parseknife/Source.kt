package dev.nickmatt.parseknife

data class Source(
    val text: String
) {

    operator fun get(range: IntRange) =
        text.substring(range)

    fun makeToken(match: MatchResult) =
        Token(this, match)

    fun makeToken(group: MatchGroup) =
        Token(this, group)

    fun makeCoords(index: Int): Pair<Int, Int> {
        var lineBreakIndex = 0
        var line = 1
        for (i in 0 until index)
            if (text[i] == '\n') {
                lineBreakIndex = i + 1
                line++
            }
        return Pair(line, index + 1 - lineBreakIndex)
    }

}