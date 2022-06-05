package dev.nickmatt.parseknife.test

import dev.nickmatt.parseknife.Parser
import dev.nickmatt.parseknife.Source
import dev.nickmatt.parseknife.meta.*
import dev.nickmatt.parseknife.meta.transform.TransformTable
import dev.nickmatt.parseknife.rule.*

private val source = Source("""
whitespace = /\s+/;

character = /'(?<content>\\.|[^'])'/;
regex = /\/(?<content>(\\.|[^\/])*)\//;
string = /"(?<content>(\\.|[^'])*)"/;

endOfFile = "${'$'}eof";
integer = /[0-9]+/;
ruleName = /[a-zA-Z_]+/;
group = '(' or ')';

decorator = /[+?*!^]*/;
termValue = integer
    | character
    | string
    | regex
    | group
    | ruleName;
term = (termValue decorator)^;

and = (term (whitespace term)^*)^;
or = and ('|' and)*;

rule = ruleName '=' or ';';
language = rule+;
""")

private fun recycle(ruleMap: RuleMap) =
    Parser.make(r(ruleMap["language"], r.eof())) {
        TransformTable.makeRuleMap(it)
    }(source)

fun testMetaParser() {
    val cycle1 = metaParser(source)
    assert(cycle1["language"] is ManyRule)
    assert(cycle1["rule"] is ThenRule)
    assert(cycle1["group"] is ThenRule)
    assert(cycle1["integer"] is RegexRule)

    val cycle2 = recycle(cycle1)
    assert(cycle2["language"] is ManyRule)
    assert(cycle2["rule"] is ThenRule)
    assert(cycle2["group"] is ThenRule)
    assert(cycle2["integer"] is RegexRule)

    val cycle3 = recycle(cycle2)
    assert(cycle3["language"] is ManyRule)
    assert(cycle3["rule"] is ThenRule)
    assert(cycle3["group"] is ThenRule)
    assert(cycle3["integer"] is RegexRule)

    println("Successfully tested meta.parse")
}