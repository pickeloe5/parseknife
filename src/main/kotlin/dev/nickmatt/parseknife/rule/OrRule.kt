package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.Token
import dev.nickmatt.parseknife.ParseKnifeError

open class OrRule(
    vararg _children: Any
): Rule() {

    private val children = _children.map { infer(it) }

    private fun findSignificantError(vararg pkes: ParseKnifeError): ParseKnifeError {
        var result = pkes[0]
        for (i in 1 until pkes.size)
            if (pkes[i].index > result.index)
                result = pkes[i]
        return result
    }

    override fun test(c: Cursor): Token {
        val errors = mutableListOf<ParseKnifeError>()
        for (r in children)
            return ParseKnifeError.doCatching(errors::add) {
                c.makeToken(r.makeToken(c))
            } ?: continue
        throw findSignificantError(*errors.toTypedArray())
    }

    override fun toString() =
        "(${children.joinToString(" | ")})"

}