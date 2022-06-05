package dev.nickmatt.parseknife.sample.node

import dev.nickmatt.parseknife.Token
import dev.nickmatt.parseknife.sample.query
import dev.nickmatt.parseknife.sample.queryAny

class ElementMarkupNode(
    private val tag: String,
    private val children: Array<MarkupNode>
): MarkupNode {

    companion object {
        fun make(token: Token) = ElementMarkupNode(
            token
                .query("openTag")
                .query("name")
                .value,
            token.queryAny("node")
                .map(MarkupNode::make)
                .toTypedArray()
    )}

    override fun toString() =
        "<$tag>${children.joinToString("")}</$tag>"
}