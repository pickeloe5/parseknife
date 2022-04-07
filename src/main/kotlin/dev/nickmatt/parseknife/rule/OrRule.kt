package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.error.ParseKnifeError

class OrRule(
    private vararg val children: Rule
): Rule {

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