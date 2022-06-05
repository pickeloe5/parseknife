package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.ParseKnifeError
import dev.nickmatt.parseknife.Token
import java.util.regex.Pattern

/**
 * Passes when a given regular expression matches at a cursor
 *
 * Converts captured groups into child tokens by default
 * Those groups' names are stored in their tokens' metadata under "groupName"
 */
open class RegexRule(
    private val expression: Regex
): Rule() {

    companion object {

        /**
         * Used for whitespace-insensitive ThenRules
         */
        val WHITESPACE = RegexRule(Regex("\\s*"))
    }

    /**
     * Returns a lookup map for finding names of groups
     * Normally, this name is not accessible for MatchGroups
     * You can, however, lookup groups for names; so we reverse that
     */
    private fun makeGroupNames(match: MatchResult) =
        makeGroupNames().mapNotNull { name ->
            match.groups[name]
                ?.let { Pair(it, name) }
        }.toMap()

    /**
     * Returns a list of captured groups paired with nullable String names
     * Useful for converting these groups to tokens, no need to refer to a map
     */
    private fun makeGroups(
        match: MatchResult,
        names: Map<MatchGroup, String> = makeGroupNames(match)
    ) = match.groups.toList()
        .subList(1, match.groups.size)
        .filterNotNull()
        .map { Pair(it, names[it]) }

    /**
     * Returns a linear list of tokens from captured groups
     */
    private fun makeChildren(
        token: Token,
        groups: List<Pair<MatchGroup, String?>>
    ) = groups.map { (group, name) ->
        val child = Token(token.source, group)
        if (name != null)
            child.withMeta("groupName", name)
        else child
    }

    /**
     * Adopts a linear list of tokens as a structured hierarchy of children
     */
    private fun adoptChildren(
        token: Token,
        children: List<Token>
    ): Token {
        var parent = token
        for (child in children) {
            while (child.index - parent.index >= parent.value.length)
                parent = parent.parent
            parent.children.add(child)
            parent = child
        }
        return token
    }

    override fun test(cursor: Cursor): Token {
        val match = makeMatch(cursor)
            ?: throw ParseKnifeError(cursor, this)
        val token = Token(cursor.source, match)

        if (match.groups.isEmpty())
            return token

        // Spicy!
        return adoptChildren(token,
            makeChildren(token,
                makeGroups(match)))
    }

    override fun toString() = "/${
        expression.toString()
            .replace("/", "\\/")
    }/"

    /**
     * Matching at an index is experimental currently
     */
    @OptIn(ExperimentalStdlibApi::class)
    private fun makeMatch(c: Cursor) =
        expression.matchAt(c.source.text, c.index)

    /**
     * Kotlin's regular expressions don't make it easy for us
     * to access groups and their names in the way we'd like
     */
    private fun makeGroupNames(): Array<String> {

        val field = Pattern::class.java.getDeclaredField("namedGroups")
        field.isAccessible = true

        return (field.get(expression.toPattern()) as Map<*, *>?)
            ?.keys?.map(Any?::toString)?.toTypedArray()
            ?: arrayOf()
    }
}