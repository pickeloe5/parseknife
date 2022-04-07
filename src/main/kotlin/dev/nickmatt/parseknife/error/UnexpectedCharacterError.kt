package dev.nickmatt.parseknife.error

import dev.nickmatt.parseknife.Cursor

class UnexpectedCharacterError(
    c: Cursor,
    val received: Char?,
    vararg val expected: Char
): ParseKnifeError(c, makeMessage(received, expected)) {
    companion object {

        private fun makeMessage(received: Char?, expected: CharArray): String {
            val receivedFormatted = if (received == null) "EOF" else "'$received'"
            val expectedFormatted = "'${expected.joinToString("' | '")}'"
            return "Received: ${receivedFormatted}, expected: $expectedFormatted"
        }

    }
}