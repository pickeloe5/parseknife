package dev.nickmatt.parseknife.meta.transform

import dev.nickmatt.parseknife.Token
import dev.nickmatt.parseknife.meta.RuleMap
import dev.nickmatt.parseknife.meta.UndefinedRuleError
import dev.nickmatt.parseknife.rule.Rule
import dev.nickmatt.parseknife.rule.r

/**
 * Statefully transforms a language token into a map of its named Rules
 * Uses state to decouple rules from the order in which they're defined
 * Useful for referencing rules before they're defined (e.g. "a=b+;b='b';")
 */
class TransformTable private constructor(
    language: Token
) {

    companion object {
        fun makeRuleMap(language: Token) =
            RuleMap(TransformTable(language).rules)
    }

    /**
     * For wrapping rules that are only references to other rules
     * This is an uncommon use case, but a hidden killer
     * Token metadata would be changed in unpredictable ways
     * Because the resolved reference would itself be modified
     * Its metadata overwritten with that of the referencing rule
     */
    var makingReference = false

    private val ruleTokens = mutableMapOf<String, Token>()
    private val rules = mutableMapOf<String, Rule>()
    private val resolveHistory = mutableListOf<String>()

    init {

        for (t in language.queryMany("rule"))
            ruleTokens[t.query("ruleName").value] = t

        ruleTokens.keys.forEach { addAttempt(it) }
    }

    private fun addAttempt(name: String) {

        if (name in rules || name in resolveHistory)
            return
        resolveHistory.add(name)

        try {
            var value = ruleTokens[name]?.let {
                transformValue(it)
            } ?: throw UndefinedRuleError(name)

            if (makingReference)
                value = r.wrap(value)

            rules[name] = value
                .withMeta("ruleName", name)

        } finally {
            resolveHistory.removeLast() }
    }

    fun resolveReference(name: String): Rule {

        if (name !in rules && name !in resolveHistory)
            addAttempt(name)

        return rules[name] ?: r.ref {rules[name]!!}
    }

}