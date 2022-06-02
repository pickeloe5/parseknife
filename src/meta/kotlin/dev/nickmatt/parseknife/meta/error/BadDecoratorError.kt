package dev.nickmatt.parseknife.meta.error

class BadDecoratorError(decorator: String):
        Error("Bad decorator (?, +, etc.): $decorator")