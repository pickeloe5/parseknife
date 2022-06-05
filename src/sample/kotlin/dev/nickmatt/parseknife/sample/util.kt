package dev.nickmatt.parseknife.sample

import dev.nickmatt.parseknife.Token

private fun byRule(name: String) = {
        it: Token -> it.meta["ruleName"] == name }

fun Token.query(ruleName: String, depth: Int? = null) =
    query(depth, byRule(ruleName))

fun Token.queryAny(ruleName: String, depth: Int? = null) =
    queryAny(depth, byRule(ruleName))