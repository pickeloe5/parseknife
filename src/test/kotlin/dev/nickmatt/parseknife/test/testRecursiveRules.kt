@file:OptIn(ExperimentalJsExport::class)

package dev.nickmatt.parseknife.test

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.ParseKnifeError
import dev.nickmatt.parseknife.Token
import dev.nickmatt.parseknife.rule.Rule
import dev.nickmatt.parseknife.rule.RuleHelper

private val r = RuleHelper.instance

private val rule: Rule = r.ref {
    r.many(r.or("b", gRule)) }
private val gRule = r("(", rule, ")")
fun testRecursiveRules() {
//    println(rule)
    val cursor = Cursor.make("(b(b))")

    assert(rule.makeToken(cursor).value == cursor.source.text)
        {"Expected full source to pass rule's test"}
    cursor.index = 1
    assert(rule.makeToken(cursor).value == cursor.source.text.substring(1..4))
        {"Expected indices 1 through 4 to pass rule's test"}

    try {
        cursor.index = 4
        rule.makeToken(cursor)
        assert(false) {"Expected test to fail at index 4"}
    } catch (e: Error) {
        if (e is AssertionError)
            throw e
        if (e !is ParseKnifeError)
            return assert(false) {"Test should have failed with a ParseKnifeError"}
        assert(e.index == 4) {"Test at index 4 should have failed at index 4"}
    }

    println("Successfully tested RecursiveRules")
}