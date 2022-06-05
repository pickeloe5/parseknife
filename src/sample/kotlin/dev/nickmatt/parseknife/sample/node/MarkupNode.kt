package dev.nickmatt.parseknife.sample.node

import dev.nickmatt.parseknife.ParseKnifeError
import dev.nickmatt.parseknife.Token

interface MarkupNode {
    companion object {
        fun make(token: Token): MarkupNode {
            for (child in token.children) {
                if (child.meta["ruleName"] == "text")
                    return TextMarkupNode.make(child)
                else if (child.meta["ruleName"] == "element")
                    return ElementMarkupNode.make(child)
            }
            throw ParseKnifeError(token, "Expected node to be text or element")
        }
    }
}