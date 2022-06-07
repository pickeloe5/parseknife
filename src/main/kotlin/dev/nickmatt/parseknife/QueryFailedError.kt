package dev.nickmatt.parseknife

class QueryFailedError(token: Token):
        ParseKnifeError(token, "Could not find child matching query")