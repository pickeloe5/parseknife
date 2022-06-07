package dev.nickmatt.parseknife.rule

class RuleInferenceError(val received: Any): Error(
    "Expected rule literal (char, string, regex, etc.)")