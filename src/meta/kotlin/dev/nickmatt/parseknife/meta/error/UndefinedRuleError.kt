package dev.nickmatt.parseknife.meta.error

class UndefinedRuleError(name: String):
        Error("Tried to reference a rule by a name not in the table: $name")