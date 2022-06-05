package dev.nickmatt.parseknife.meta

enum class TermKind(
    private val ruleName: String
) {
    END_OF_FILE("endOfFile"),
    INTEGER("integer"),
    CHARACTER("character"),
    STRING("string"),
    REGEX("regex"),
    GROUP("group"),
    RULE_NAME("ruleName");
    companion object {
        val ruleNames = values()
            .map { it.ruleName }.toTypedArray()
    }
}