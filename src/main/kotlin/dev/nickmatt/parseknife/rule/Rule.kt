package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.error.RuleInferenceError

interface Rule {

    companion object {
        fun infer(vararg args: Any): Rule {
            if (args.size > 1)
                return ThenRule(*args.map{ infer(it) }.toTypedArray())
            val arg = args[0]
            if (arg is Rule)
                return arg
            if (arg is Char)
                return CharacterRule(arg)
            throw RuleInferenceError(arg)
        }

        inline fun refer(crossinline resolve: () -> Rule) = object: Rule {
            lateinit var _root: Rule
            val root: Rule get() {
                if (!::_root.isInitialized)
                    _root = resolve()
                return _root
            }
            override fun test(c: Cursor) =
                root.test(c)
        }
    }

    fun test(c: Cursor): Int

}