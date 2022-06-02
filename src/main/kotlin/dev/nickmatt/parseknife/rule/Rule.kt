package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.Token
import dev.nickmatt.parseknife.error.RuleInferenceError

abstract class Rule {

    companion object {
        fun infer(vararg args: Any): Rule {
            if (args.size > 1)
                return ThenRule(*args.map{ infer(it) }.toTypedArray())
            val arg = args[0]
            if (arg is Rule)
                return arg
            if (arg is Int)
                return AnyRule(arg)
            if (arg is Char)
                return CharacterRule(arg)
            throw RuleInferenceError(arg)
        }

        inline fun refer(crossinline resolve: () -> Rule) = object: Rule() {
            lateinit var _root: Rule
            val root: Rule get() {
                if (!::_root.isInitialized)
                    _root = resolve()
                return _root
            }
            override fun test(c: Cursor) =
                root.makeToken(c)
        }

        fun wrap(root: Rule) = object: Rule() {
            override fun test(c: Cursor): Token {
                val child = root.test(c)
                val token = c.makeToken(child.value.length)
                token.children.add(child)
                return makeToken(token)
            }
        }
    }

    open val meta = mutableMapOf<String, Any>()

    protected abstract fun test(c: Cursor): Token
    fun makeToken(c: Cursor) = makeToken(test(c))
    fun makeToken(t: Token) = t.withMeta(meta)

    fun withMeta(key: String, value: Any): Rule {
        meta[key] = value
        return this
    }
    fun withMeta(vararg pairs: Pair<String, Any>): Rule {
        pairs.forEach { (key, value) ->
            withMeta(key, value)
        }
        return this
    }

}