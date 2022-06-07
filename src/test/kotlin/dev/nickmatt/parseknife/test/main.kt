package dev.nickmatt.parseknife.test

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.Source
import dev.nickmatt.parseknife.Token
import dev.nickmatt.parseknife.rule.Rule

class AssertionError(_message: String?):
    Error(_message ?: "Assertion failed")

fun assert(condition: Boolean, makeMessage: (() -> String)? = null) {
    if (!condition)
        throw AssertionError(makeMessage?.invoke())
}

fun main() {
    testSourceLocation()
    testTokenMeta()
    testRuleHelper()

    testAnyRules()
    testCharacterRules()
    testRegexRules()

    testMaybeRules()
    testManyRules()
    testNotRules()

    testAndRules()
    testOrRules()

    testRecursiveRules()
    testMetaParser()
}