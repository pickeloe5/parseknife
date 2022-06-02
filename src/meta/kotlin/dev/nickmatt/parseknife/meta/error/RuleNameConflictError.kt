package dev.nickmatt.parseknife.meta.error

class RuleNameConflictError(name: String):
        Error("Attempted to add a rule by a name already in the table: $name")