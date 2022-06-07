package dev.nickmatt.parseknife

@OptIn(ExperimentalJsExport::class)
class OrphanedTokenError(token: Token):
        ParseKnifeError(token, "Could not find parent for token")