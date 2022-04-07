package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.error.ParseKnifeError

class MaybeRule(
    private val root: Rule
): Rule {

    override fun test(c: Cursor) = try {
        root.test(c)
    } catch (e: ParseKnifeError) {
        0
    }

}