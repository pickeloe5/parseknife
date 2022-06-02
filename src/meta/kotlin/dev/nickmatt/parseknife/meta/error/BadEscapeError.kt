package dev.nickmatt.parseknife.meta.error

class BadEscapeError(code: String):
        Error("Bad escape code (\\n, \\t, etc.): $code")