package dev.nickmatt.parseknife.meta.error

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.error.UnexpectedTokenError

class BadTermKind(_cursor: Cursor):
    UnexpectedTokenError(_cursor, "Expected term to be wildcard, char, group, or reference.")