package dev.nickmatt.parseknife

/**
 * Convenience class for reading from a source
 */
@ExperimentalJsExport
data class Source(
    val text: String
) {

    operator fun get(index: Int, length: Int) =
        text.substring(index, index + length)

    /**
     * Converts "index" to "line" and "column"
     */
    fun makeCoords(index: Int): SourceLocation {
        var lineBreakIndex = 0
        var line = 1
        for (i in 0 until index)
            if (text[i] == '\n') {
                lineBreakIndex = i + 1
                line++
            }
        return SourceLocation(line, index + 1 - lineBreakIndex)
    }

}