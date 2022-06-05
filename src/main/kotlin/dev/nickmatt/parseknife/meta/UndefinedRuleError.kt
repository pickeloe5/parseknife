package dev.nickmatt.parseknife.meta

class UndefinedRuleError(name: String): Error(
    "Tried to reference undefined rule: $name")