package dev.nickmatt.parseknife.test

import dev.nickmatt.parseknife.rule.*

private val r = RuleHelper.instance

fun testRuleHelper() {

    assert(r(1) is AnyRule) {"Expected Int to be inferred as AnyRule"}
    assert(r("c") is CharacterRule) {"Expected 1-Char String to be inferred as CharacterRule"}
    assert(r("c", "c") is AndRule) {"Expected Chars to be inferred as AndRule"}

    val received = true
    try {
        r(received)
        assert(false) {"Expected failure to infer Boolean as any rule"}
    } catch (e: Error) {
        if (e is AssertionError)
            throw e
        if (e !is RuleInferenceError)
            return assert(false) {"Expected inferring an Float to throw a RuleInferenceError"}
        assert(e.received == received) {"Expected bad Float to be stored on RuleInferenceError"}
    }

    println("Successfully tested rule helper")
}