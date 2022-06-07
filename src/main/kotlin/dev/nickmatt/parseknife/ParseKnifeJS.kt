package dev.nickmatt.parseknife

import dev.nickmatt.parseknife.meta.metaParser
import dev.nickmatt.parseknife.rule.RuleHelper

@ExperimentalJsExport
class ParseKnifeJS {

    companion object {
        @JsName("instance")
        var instance: ParseKnifeJS? = null
    }

    val r = RuleHelper()

    @JsName("makeRule")
    fun makeRule(source: String) =
        metaParser.expression(source)

    @JsName("makeRules")
    fun makeRules(source: String) =
        metaParser(source).toJson()

}