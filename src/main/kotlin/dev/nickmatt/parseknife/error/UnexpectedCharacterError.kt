package dev.nickmatt.parseknife.error

import dev.nickmatt.parseknife.Cursor

class UnexpectedCharacterError(
    c: Cursor,
    val received: Char? = c[0],
    vararg val expected: Char
): ParseKnifeError(c, makeMessage(received, expected)) {
    companion object {

        private fun makeMessage(received: Char?, expected: CharArray): String {
            var message = "Received: ${if (received == null) "NULL" else "'$received'"}"
            if (expected.isNotEmpty())
                message += ", expected: '${expected.joinToString("' | '")}'"
            return message
        }

    }
}