package dev.nickmatt.parseknife.test

import dev.nickmatt.parseknife.Token
import dev.nickmatt.parseknife.rule.CharacterRule
import dev.nickmatt.parseknife.rule.Rule

fun logToken(t: Token, depth: Int = 0) {
    val indent = "  "
    val prefix = indent.repeat(depth)
    println("${prefix}Token:")
    println("$prefix - Index: ${t.index}")
    println("$prefix - Value: \"${t.value}\"")
    if (t.meta.isNotEmpty()) {
        println("$prefix - Meta:")
        for ((key, value) in t.meta)
            println("$prefix$indent - $key: ($value)")
    }
    if (t.children.isNotEmpty()) {
        println("$prefix - Children:")
        for (child in t.children)
            logToken(child, depth + 1)
    }
}

fun main() {
    testCharacterRules()
    testThenRules()
    testOrRules()
    testManyRules()
    testRecursiveRules()
    testMaybeRules()
    testTokenMeta()
    testTokenChildren()
    testRuleHelper()
    testMetaParser()
}