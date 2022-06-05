package dev.nickmatt.parseknife

import dev.nickmatt.parseknife.rule.Rule

/**
 * Helps to run a given rule on different sources such as primitive strings
 */
abstract class Parser<T>(
    private val root: Rule
) {

    companion object {
        inline fun<T> make(_root: Rule, crossinline _transform: (Token) -> T) =
                object: Parser<T>(_root) {
            override fun transform(token: Token) =
                _transform(token)
        }

        fun make(_root: Rule) = object: Parser<Token>(_root) {
            override fun transform(token: Token) =
                token
        }
    }

    abstract fun transform(token: Token): T

    operator fun invoke(cursor: Cursor) =
        transform(root.makeToken(cursor))

    operator fun invoke(source: Source) =
        invoke(Cursor(source))

    operator fun invoke(source: String) =
        invoke(Source(source))

}