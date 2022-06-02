package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.Token
import dev.nickmatt.parseknife.error.ParseKnifeError
import dev.nickmatt.parseknife.error.UnexpectedCharacterError

class NotRule(
    _root: Any
): Rule() {
    private val root: Rule = infer(_root)
    override fun test(c: Cursor): Token {
        val received: Char?
        try {
            received = root.makeToken(c).value.getOrNull(0)
        } catch (e: ParseKnifeError) {
            return c.makeToken(1)
        }
        throw UnexpectedCharacterError(c, received)
    }

    override fun toString() =
        "$root!"
}