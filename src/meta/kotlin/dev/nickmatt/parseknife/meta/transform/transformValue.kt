package dev.nickmatt.parseknife.meta.transform

import dev.nickmatt.parseknife.Token
import dev.nickmatt.parseknife.rule.Rule
import dev.nickmatt.parseknife.rule.r

internal fun TransformTable.transformValue(parent: Token): Rule {
    val value = parent.query("or")

    val ands = transformExpression(value, "and") { and ->
        val terms = transformExpression(
            and, "term", ::transformTerm)

        if (terms.size != 1) {
            makingReference = false
            r.then(*terms)
        } else terms[0]
    }

    return if (ands.size != 1) {
        makingReference = false
        r.or(*ands)
    } else ands[0]
}

private fun transformExpression(
    t: Token, termKey: String, makeTerm: (Token) -> Rule
): Array<Rule> {
    val terms = t.queryMany(termKey)
    if (terms.size == 1)
        return arrayOf(makeTerm(terms[0]))
    return terms.map(makeTerm).toTypedArray()
}