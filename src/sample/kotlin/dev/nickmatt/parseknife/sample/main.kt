package dev.nickmatt.parseknife.sample

import dev.nickmatt.parseknife.Parser
import dev.nickmatt.parseknife.meta.metaParser
import dev.nickmatt.parseknife.sample.node.MarkupNode

private val source = """
text = ("\\<" | '<'!)+;

name = /[a-z]+/;
openTag = '<' name '>';
closeTag = '<' '/' name '>';
element = openTag node* closeTag;

node = element | text;
page = node+;
"""

fun main(args: Array<String>) {

    val rules = metaParser(source)
    val parseSample = Parser.make(rules["page"]) { page ->

        page.queryAny("node")
            .map(MarkupNode::make)
    }

    val nodes = parseSample(args[0])

    println(nodes.joinToString(""))
}