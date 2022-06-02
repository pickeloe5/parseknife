package dev.nickmatt.parseknife.test

import dev.nickmatt.parseknife.error.RuleInferenceError
import dev.nickmatt.parseknife.rule.AnyRule
import dev.nickmatt.parseknife.rule.CharacterRule
import dev.nickmatt.parseknife.rule.ThenRule
import dev.nickmatt.parseknife.rule.r
import java.lang.AssertionError

fun testRuleHelper() {

    assert(r(1) is AnyRule) {"Expected Int to be inferred as AnyRule"}
    assert(r('c') is CharacterRule) {"Expected Char to be inferred as CharacterRule"}
    assert(r('c', 'c') is ThenRule) {"Expected Chars to be inferred as ThenRule"}

    val received = 54f
    try {
        r(received)
        assert(false) {"Expected failure to infer Float as any rule"}
    } catch (e: Error) {
        if (e is AssertionError)
            throw e
        if (e !is RuleInferenceError)
            return assert(false) {"Expected inferring an Float to throw a RuleInferenceError"}
        assert(e.received == received) {"Expected bad Float to be stored on RuleInferenceError"}
    }

}