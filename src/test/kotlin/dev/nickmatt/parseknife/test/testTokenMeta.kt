package dev.nickmatt.parseknife.test

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.rule.ManyRule

fun testTokenMeta() {

    val cursor = Cursor.make("a")
    val rule = ManyRule('a')

    val kind = "many-a"
    rule.meta["kind"] = kind

    val token = rule.makeToken(cursor)
    assert(token.value == cursor.source.text) {"Expected full source to pass rule's test"}
    assert(token.meta["kind"] == kind) {"Expected 'kind' of token to be stored in meta"}

    println("Successfully tested Token.meta")

}