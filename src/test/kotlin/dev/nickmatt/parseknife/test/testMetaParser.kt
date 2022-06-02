package dev.nickmatt.parseknife.test

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.Source
import dev.nickmatt.parseknife.Token
import dev.nickmatt.parseknife.error.UnexpectedCharacterError
import dev.nickmatt.parseknife.meta.parse
import dev.nickmatt.parseknife.meta.transform
import dev.nickmatt.parseknife.rule.*

val alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_".toCharArray()

private val source = """whitespace = (' ' | '\t' | '\n')+;

ruleName = ('${alphabet.joinToString("' | '")}')+;

content = (('\\' ${'$'})^ | '\''!);
char = ('\'' content '\'')^;

wildcard = '$';
decorator = ('+' | '*' | '?' | '!' | '^')+;

group = '(' or ')';
termValue = wildcard | char | group | ruleName;
term = (termValue decorator?)^;
and = (term (whitespace term)^*)^;
or = (and ('|' and)*)^;

rule = ruleName '=' or ';';
language = rule+;
"""

private val eof = object: Rule() {
    init {
        meta["ruleName"] = "eof"
    }
    override fun test(c: Cursor): Token {
        if (c.index != c.source.text.length)
            throw UnexpectedCharacterError(c)
        return makeToken(c.makeToken(0))
    }
}

fun testMetaParser() {
    val cycle1 = parse(source)
    val recycle1 = r(cycle1["language"]!!, eof)
    assert(cycle1["language"] is ManyRule)
    assert(cycle1["rule"] is ThenRule)
    assert(cycle1["group"] is ThenRule)
    assert(cycle1["wildcard"] is CharacterRule)

    val cursor2 = Cursor(Source(source))
    val cycle2 = transform(cursor2, recycle1.makeToken(cursor2))
    val recycle2 = r(cycle2["language"]!!, eof)
    assert(cycle2["language"] is ManyRule)
    assert(cycle2["rule"] is ThenRule)
    assert(cycle2["group"] is ThenRule)
    assert(cycle2["wildcard"] is CharacterRule)

    val cursor3 = Cursor(Source(source))
    val cycle3 = transform(cursor3, recycle2.makeToken(cursor3))
    assert(cycle3["language"] is ManyRule)
    assert(cycle3["rule"] is ThenRule)
    assert(cycle3["group"] is ThenRule)
    assert(cycle3["wildcard"] is CharacterRule)

    println("Successfully tested meta parser")
}