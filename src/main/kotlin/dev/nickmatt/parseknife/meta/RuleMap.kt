package dev.nickmatt.parseknife.meta

import dev.nickmatt.parseknife.rule.Rule
import kotlin.js.json

/**
 * Extends MutableMap to get non-null with error handling
 */
class RuleMap(rules: Map<String, Rule>):
    LinkedHashMap<String, Rule>(rules) {

    fun jsGet(key: String) = get(key)

    override operator fun get(key: String) = super.get(key)
        ?: throw UndefinedRuleError(key)

    fun toJson() = json(*toList().toTypedArray())

}