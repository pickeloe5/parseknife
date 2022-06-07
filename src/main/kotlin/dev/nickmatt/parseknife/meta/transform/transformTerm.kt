package dev.nickmatt.parseknife.meta.transform

import dev.nickmatt.parseknife.ParseKnifeError
import dev.nickmatt.parseknife.Token
import dev.nickmatt.parseknife.meta.TermKind
import dev.nickmatt.parseknife.rule.Rule

internal fun TransformTable?.transformTerm(parent: Token): Rule {

    val value = parent.query("termValue").children.find {
        it.meta["ruleName"] in TermKind.ruleNames
    } ?: throw ParseKnifeError(parent, "Expected term value (e.g. int, char, string, etc.)")

    this?.let {
        if (!makingReference && value.meta["ruleName"] == "ruleName")
            makingReference = true
    }

    var result = transformTermValue(value)

    result = transformTermDecorator(result,
        parent.query("decorator"))

    return result
}