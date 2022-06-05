package dev.nickmatt.parseknife.meta.rule

import dev.nickmatt.parseknife.rule.r

val rule = r(

    r.many(

        r(ruleName, '=', expression, ';')
            .withMeta("ruleName", "rule")

    ).withMeta("ruleName", "language"),

    r.eof()
        .withMeta("ruleName", "endOfFile")

).withMeta("ruleName", "file")