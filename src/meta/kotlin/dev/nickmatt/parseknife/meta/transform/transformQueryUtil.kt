package dev.nickmatt.parseknife.meta.transform

import dev.nickmatt.parseknife.Token

internal fun Token.query(name: String) =
    query { it.meta["ruleName"] == name }

internal fun Token.queryRegexGroup(name: String) =
    query { it.meta["groupName"] == name }

internal fun Token.queryMany(name: String) =
    queryMany { it.meta["ruleName"] == name }