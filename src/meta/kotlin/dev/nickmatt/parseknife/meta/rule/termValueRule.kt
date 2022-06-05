package dev.nickmatt.parseknife.meta.rule

import dev.nickmatt.parseknife.rule.r

internal val ruleName = r.regex("[a-zA-Z_]+")
    .withMeta("ruleName", "ruleName")

private val eofTerm = r('$', 'e', 'o', 'f')
    .withMeta("ruleName", "endOfFile")

private val integer = r.regex("[0-9]+")
    .withMeta("ruleName", "integer")

private val character = r.regex("'(?<content>\\\\.|[^'])'")
    .withMeta("ruleName", "character")

private val string = r.regex("\"(?<content>(\\\\.|[^'])*)\"")
    .withMeta("ruleName", "string")

private val regex = r.regex("/(?<content>(\\\\.|[^/])*)/")
    .withMeta("ruleName", "regex")

private val group = r('(', expression, ')')
    .withMeta("ruleName", "group")

internal val termValue = r.or(
    eofTerm,
    integer, character, string, regex,
    ruleName,
    group
).withMeta("ruleName", "termValue")