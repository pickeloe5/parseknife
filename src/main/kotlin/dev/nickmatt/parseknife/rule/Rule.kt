package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.Token

abstract class Rule {

    class InferenceError(val received: Any): Error(
        "Expected rule literal (char, string, regex, etc.), received: ${received.javaClass.name}")

    companion object {
        fun infer(vararg args: Any): Rule {
            if (args.size > 1)
                return ThenRule(*args.map{ infer(it) }.toTypedArray())
            return when (val arg = args[0]) {
                is Rule -> arg
                is Int -> AnyRule(arg)
                is Char -> CharacterRule(arg)
                is String -> {
                    if (arg.length == 1)
                        CharacterRule(arg[0])
                    else
                        ThenRule(*arg.map { CharacterRule(it) }.toTypedArray())
                }
                is Regex -> RegexRule(arg)
                else -> throw InferenceError(arg)
            }
        }

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

        fun wrap(root: Rule) = object: Rule() {
            override fun test(cursor: Cursor) =
                cursor.makeToken(root.test(cursor))
        }

        inline fun make(crossinline _test: (Cursor) -> Token) =
            object: Rule() {
                override fun test(cursor: Cursor) =
                    _test(cursor)
            }
    }

    open val meta = mutableMapOf<String, Any>()

    protected abstract fun test(cursor: Cursor): Token
    fun makeToken(c: Cursor) = makeToken(test(c))
    fun makeToken(t: Token): Token {
        for ((k, v) in meta)
            t.meta[k] = v
        return t
    }

    fun withMeta(key: String, value: Any): Rule {
        meta[key] = value
        return this
    }

}