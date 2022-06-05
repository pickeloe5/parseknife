package dev.nickmatt.parseknife.meta

import dev.nickmatt.parseknife.Parser
import dev.nickmatt.parseknife.meta.rule.rule
import dev.nickmatt.parseknife.meta.transform.TransformTable

/**
 * Returns a map of names to rules from source text
 */
val metaParser =
    Parser.make(rule, TransformTable::makeRuleMap)