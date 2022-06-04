package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.ParseKnifeError
import dev.nickmatt.parseknife.Token
import java.util.regex.Pattern

open class RegexRule(
    private val expression: Regex
): Rule() {

    companion object {
        val WHITESPACE = RegexRule(Regex("\\s*"))
    }

    private fun makeGroupNameMap(token: Token, match: MatchResult): Map<MatchGroup, String> {
        val names = makeGroupNames()
        val result = mutableMapOf<MatchGroup, String>()
        for (name in names) {
            val group = match.groups[name] ?: throw ParseKnifeError(token,
                "Could not find capture group for name: '$name'")
            result[group] = name
        }
        return result
    }

    private fun makeGroups(token: Token, match: MatchResult): List<Pair<MatchGroup, String?>> {
        val names = makeGroupNameMap(token, match)

        val groups = match.groups
            .filterNotNull()
        return groups.subList(1, groups.size)
            .map { Pair(it, names[it]) }
    }

    private fun makeFlatChildren(token: Token, match: MatchResult) =
        makeGroups(token, match).map { (group, name) ->

            val child = Token(token.source, group)

            if (name != null)
                child.withMeta("groupName", name)

            else child
        }

    override fun test(cursor: Cursor): Token {
        val match = makeMatch(cursor)
            ?: throw ParseKnifeError(cursor, this)
        val token = Token(cursor.source, match)

        if (match.groups.isEmpty())
            return token

        val children = makeFlatChildren(token, match)
        var parent = token
        for (child in children) {
            while (child.index - parent.index >= parent.length)
                parent = parent.parent
            parent.children.add(child)
            parent = child
        }
        return token
    }

    override fun toString() = "/${
        expression.toString()
            .replace("/", "\\/")
    }/"

    @OptIn(ExperimentalStdlibApi::class)
    private fun makeMatch(c: Cursor) =
        expression.matchAt(c.source.text, c.index)

    private fun makeGroupNames(): Array<String> {

        val field = Pattern::class.java.getDeclaredField("namedGroups")
        field.isAccessible = true

        return (field.get(expression.toPattern()) as Map<*, *>?)
            ?.keys?.map(Any?::toString)?.toTypedArray()
            ?: arrayOf()
    }
}