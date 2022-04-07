package dev.nickmatt.parseknife.error

import dev.nickmatt.parseknife.Cursor

open class ParseKnifeError(
    val cursor: Cursor,
    _message: String
): Error(wrapMessage(_message, cursor)) {

    companion object {
        fun doUntil(atLeastOnce: Boolean = true, meth: () -> Unit) {
            if (atLeastOnce)
                meth()
            while (true) try {
                meth()
            } catch (e: ParseKnifeError) {
                return
            }
        }

        fun<T> doCatching(reject: (ParseKnifeError) -> Unit, meth: () -> T) =
            try {
                meth()
            } catch (e: ParseKnifeError) {
                reject(e)
                null
            }

        private fun wrapMessage(message: String, cursor: Cursor): String {
            val (line, column) = cursor.makeCoords()
            return "[L$line:C$column] $message"
        }
    }

    val index = cursor.index

}