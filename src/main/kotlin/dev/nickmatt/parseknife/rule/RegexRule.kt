package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.ParseKnifeError
import dev.nickmatt.parseknife.Token
import kotlin.js.Json
import kotlin.js.RegExp

/**
 * Passes when a given regular expression matches at a cursor
 *
 * Converts captured groups into child tokens by default
 *
 * Those groups' names are stored in their tokens' metadata under "groupName"
 */
@ExperimentalJsExport
open class RegexRule(
    _expression: String
): Rule() {

    companion object {

        fun make(_expression: RegExp) =
            RegexRule((js("_expression.source").unsafeCast<String>()))
        /**
         * Used for whitespace-insensitive ThenRules
         */
        val WHITESPACE = RegexRule("\\s*")
    }

    private val expression = RegExp(_expression, "y")

    override fun test(cursor: Cursor): Token {
        val (numberedGroups, namedGroups) = makeMatch(cursor)
            ?: throw ParseKnifeError(cursor, this)

        val token = Token(cursor.source, cursor.index, numberedGroups[0])

        if (numberedGroups.size > 1) token.withMeta("numberedGroups",
            numberedGroups.copyOfRange(1, numberedGroups.size))

        if (namedGroups != null)
            token.withMeta("namedGroups", namedGroups)

        return token
    }

    override fun toString() = "/${
        expression.toString()
            .replace("/", "\\/")
    }/"

    private fun makeMatch(c: Cursor): Pair<Array<String>, Json?>? {
        expression.lastIndex = c.index
        val jsMatch = expression.exec(c.source.text) ?: return null
        val numberedGroups = js("Array.from(jsMatch)")
            .unsafeCast<Array<String>>()
        val namedGroups = js("jsMatch.groups || null")
            .unsafeCast<Json?>()
        return Pair(numberedGroups, namedGroups)
    }
}