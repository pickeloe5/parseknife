package dev.nickmatt.parseknife.test

fun main() {
    testSourceLocation()
    testTokenMeta()
    testTokenChildren()
    testRuleHelper()

    testAnyRules()
    testCharacterRules()
    testRegexRules()

    testMaybeRules()
    testManyRules()
    testNotRules()

    testThenRules()
    testOrRules()

    testRecursiveRules()
    testMetaParser()
}