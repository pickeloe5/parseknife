package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.Source
import dev.nickmatt.parseknife.Token
import kotlin.js.RegExp
import kotlin.js.json

/**
 * Runs a test on a given source at a given index
 */
@ExperimentalJsExport
abstract class Rule {

    companion object {
        /**
         * Infers Rules from more primitive types
         *
         * E.g. Chars become CharacterRules, RegExps become RegexRules, etc.
         *
         * When more than one argument is passed, they are AndRuled together
         */
        fun infer(vararg args: Any): Rule {
            if (args.size > 1)
                return AndRule(*args.map{ infer(it) }.toTypedArray())
            return when (val arg = args[0]) {
                is Rule -> arg
                is Int -> AnyRule(arg)
                is Char -> CharacterRule.make(arg)
                is String -> {
                    if (arg.length == 1)
                        CharacterRule(arg)
                    else
                        AndRule(*arg.map { CharacterRule.make(it) }.toTypedArray())
                }
                is RegExp -> RegexRule.make(arg)
                else -> throw RuleInferenceError(arg)
            }
        }

        /**
         * Creates a rule implementation that resolves to another rule's test
         *
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
            override fun toString() =
                root.toString()
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
    @JsName("meta")
    open val meta = json()

    /**
     * Builder pattern helper for [meta]
     *
     * The metadata for this rule is applied to the tokens it produces
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
     * Convenience method for both testing and applying metadata
     */
    fun makeToken(c: Cursor): Token {
        val t = test(c)
        t.meta.add(meta)
        return t
    }

    @JsName("match")
    fun match(source: String, index: Int? = null) =
        makeToken(Cursor.make(source, index))

    fun matchEntire(source: String): Token? {
        val cursor = Cursor.make(source)
        val result = cursor.consume(this)
        return if (cursor.index != source.length)
            null
        else result
    }

}