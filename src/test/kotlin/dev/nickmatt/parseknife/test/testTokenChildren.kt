package dev.nickmatt.parseknife.test

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.rule.r

fun testTokenChildren() {

    val cursor = Cursor("abc")
    val rule = r('a', 'b', 'c')
    val token = rule.makeToken(cursor)

    assert(token.children.size == 3) {"Expected matched token to have three children"}
    assert(token.children[0].value == "${cursor.source[0]}") {"Expected first token to match first character"}
    assert(token.children[1].value == "${cursor.source[1]}") {"Expected second token to match second character"}
    assert(token.children[2].value == "${cursor.source[2]}") {"Expected third token to match third character"}

    println("Successfully tested Token.children")

}