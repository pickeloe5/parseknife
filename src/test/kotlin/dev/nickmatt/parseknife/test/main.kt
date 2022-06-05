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

    testAndRules()
    testOrRules()

    testRecursiveRules()
    testMetaParser()
}