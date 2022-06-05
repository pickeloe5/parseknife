package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.Token

/**
 * Runs a test on a given source at a given index
 */
abstract class Rule {

    class InferenceError(val received: Any): Error(
        "Expected rule literal (char, string, regex, etc.), received: ${received.javaClass.name}")

    companion object {
        /**
         * Infers Rules from more primitive types
         * E.g. Chars become CharacterRules, Regexes become RegexRules, etc.
         * When more than one argument is passed, they are AndRuled together
         */
        fun infer(vararg args: Any): Rule {
            if (args.size > 1)
                return AndRule(*args.map{ infer(it) }.toTypedArray())
            return when (val arg = args[0]) {
                is Rule -> arg
                is Int -> AnyRule(arg)
                is Char -> CharacterRule(arg)
                is String -> {
                    if (arg.length == 1)
                        CharacterRule(arg[0])
                    else
                        AndRule(*arg.map { CharacterRule(it) }.toTypedArray())
                }
                is Regex -> RegexRule(arg)
                else -> throw InferenceError(arg)
            }
        }

        /**
         * Creates a rule implementation that resolves to another rule's test
         * Useful for recursive rules that are sometimes tricky to type
         */
        inline fun refer(crossinline resolve: () -> Rule) = object: Rule() {
            lateinit var _root: Rule
            val root: Rule get() {
                if (!::_root.isInitialized)
                    _root = resolve()
                return _root
            }
            override fun test(cursor: Cursor) =
                root.makeToken(cursor)
        }

        /**
         * Wraps another rule, useful for adding additional metadata
         */
        fun wrap(root: Rule) = object: Rule() {
            override fun test(cursor: Cursor) =
                cursor.makeToken(root.test(cursor))
        }

        /**
         * Convenience function for quickly writing a dynamic Rule implementation
         */
        inline fun make(crossinline _test: (Cursor) -> Token) =
            object: Rule() {
                override fun test(cursor: Cursor) =
                    _test(cursor)
            }
    }

    /**
     * Metadata applied to tokens produced by this Rule
     */
    open val meta = mutableMapOf<String, Any>()

    /**
     * Builder pattern helper
     */
    fun withMeta(key: String, value: Any): Rule {
        meta[key] = value
        return this
    }

    /**
     * Runs this test, and returns the resulting Token
     */
    protected abstract fun test(cursor: Cursor): Token

    /**
     * Applies the metadata from this rule onto the Tokens it produces
     */
    fun makeToken(t: Token): Token {
        for ((k, v) in meta)
            t.meta[k] = v
        return t
    }

    /**
     * Convenience method for both testing and applying metadata
     */
    fun makeToken(c: Cursor) = makeToken(test(c))

}