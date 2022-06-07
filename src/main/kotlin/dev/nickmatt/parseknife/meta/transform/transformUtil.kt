package dev.nickmatt.parseknife.meta.transform

import dev.nickmatt.parseknife.QueryFailedError
import dev.nickmatt.parseknife.Token
import kotlin.js.Json

internal fun Token.query(name: String) =
    query { it.meta["ruleName"] == name }

internal fun Token.queryRegexGroup(name: String) =
    meta["namedGroups"].unsafeCast<Json>()[name] as String?
        ?: throw QueryFailedError(this)

internal fun Token.queryMany(name: String) =
    queryMany { it.meta["ruleName"] == name }

