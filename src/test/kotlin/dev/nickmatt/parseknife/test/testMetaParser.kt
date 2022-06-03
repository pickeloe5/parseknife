package dev.nickmatt.parseknife.test

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.Source
import dev.nickmatt.parseknife.meta.rule
import dev.nickmatt.parseknife.meta.transform
import dev.nickmatt.parseknife.rule.*

private val alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_".toCharArray()
private val digits = "0123456789".toCharArray()

private val source = Source("""whitespace = (' ' | '\t' | '\n')+;

ruleName = ('${alphabet.joinToString("' | '")}')+;

content = (('\\' 1)^ | '\''!);
character = ('\'' content '\'')^;

integer = ('${digits.joinToString("' | '")}')+;
decorator = ('+' | '*' | '?' | '!' | '^')+?;

group = '(' or ')';
termValue = integer | character | group | ruleName;
term = (termValue decorator)^;
and = (term (whitespace term)^*)^;
or = (and ('|' and)*)^;

rule = ruleName '=' or ';';
language = rule+;
""")

fun testMetaParser() {
    val cursor1 = Cursor(source)
    val precycle1 = rule.makeToken(cursor1)
    val cycle1 = transform(precycle1)
    val recycle1 = r(cycle1["language"]!!, r.eof())
    assert(cycle1["language"] is ManyRule)
    assert(cycle1["rule"] is ThenRule)
    assert(cycle1["group"] is ThenRule)
    assert(cycle1["integer"] is ManyRule)

    val cursor2 = Cursor(source)
    val precycle2 = recycle1.makeToken(cursor2)
    val cycle2 = transform(precycle2)
    val recycle2 = r(cycle2["language"]!!, r.eof())
    assert(cycle2["language"] is ManyRule)
    assert(cycle2["rule"] is ThenRule)
    assert(cycle2["group"] is ThenRule)
    assert(cycle2["integer"] is ManyRule)

    val cursor3 = Cursor(source)
    val precycle3 = recycle2.makeToken(cursor3)
    val cycle3 = transform(precycle3)
    assert(cycle3["language"] is ManyRule)
    assert(cycle3["rule"] is ThenRule)
    assert(cycle3["group"] is ThenRule)
    assert(cycle3["integer"] is ManyRule)

    println("Successfully tested meta.parse")
}