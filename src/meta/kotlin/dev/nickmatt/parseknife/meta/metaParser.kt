package dev.nickmatt.parseknife.meta

import dev.nickmatt.parseknife.Parser
import dev.nickmatt.parseknife.Token
import dev.nickmatt.parseknife.meta.transform.TransformTable

object metaParser: Parser<RuleMap>(rule) {

    override fun transform(token: Token) =
        TransformTable(token).makeRuleMap()

}