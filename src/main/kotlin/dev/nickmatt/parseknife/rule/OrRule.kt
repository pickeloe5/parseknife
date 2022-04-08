package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.error.ParseKnifeError

class OrRule(
    vararg _children: Any
): Rule {

    private val children = _children.map { Rule.infer(it) }

    private fun findSignificantError(vararg pkes: ParseKnifeError): ParseKnifeError {
        var result = pkes[0]
        for (i in 1 until pkes.size)
            if (pkes[i].index > result.index)
                result = pkes[i]
        return result
    }

    override fun test(c: Cursor): Int {
        val errors = mutableListOf<ParseKnifeError>()
        for (r in children)
            return ParseKnifeError.doCatching(errors::add) {
                r.test(c)
            } ?: continue
        throw findSignificantError(*errors.toTypedArray())
    }

}