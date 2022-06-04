package dev.nickmatt.parseknife.meta

import dev.nickmatt.parseknife.rule.Rule

class RuleMap(rules: Map<String, Rule>):
    LinkedHashMap<String, Rule>(rules) {

    override operator fun get(key: String) = super.get(key)
        ?: throw UndefinedRuleError(key)

}