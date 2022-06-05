package dev.nickmatt.parseknife.meta

import dev.nickmatt.parseknife.Parser
import dev.nickmatt.parseknife.meta.rule.rule
import dev.nickmatt.parseknife.meta.transform.TransformTable

val metaParser =
    Parser.make(rule, TransformTable::makeRuleMap)