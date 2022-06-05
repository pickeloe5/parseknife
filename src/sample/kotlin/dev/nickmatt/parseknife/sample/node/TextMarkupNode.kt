package dev.nickmatt.parseknife.sample.node

import dev.nickmatt.parseknife.Token

class TextMarkupNode(
    private val content: String
): MarkupNode {

    companion object {

        fun make(token: Token) =
            TextMarkupNode(token.value)
    }

    override fun toString() =
        content

}