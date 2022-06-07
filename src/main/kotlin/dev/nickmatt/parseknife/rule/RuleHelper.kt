package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor
import dev.nickmatt.parseknife.Token
import kotlin.js.RegExp

@ExperimentalJsExport
class RuleHelper {

    companion object {
        val instance = RuleHelper()
    }

    operator fun invoke(vararg args: dynamic) =
        Rule.infer(*args)

    @JsName("invoke")
    fun jsInvoke(args: Array<dynamic>) =
        Rule.infer(*args)

    @JsName("ref")
    inline fun ref(crossinline resolve: () -> Rule) =
        Rule.refer(resolve)

    @JsName("make")
    inline fun make(crossinline test: (Cursor) -> Token) =
        Rule.make(test)

    @JsName("wrap")
    fun wrap(r: Rule) =
        Rule.wrap(r)

    @JsName("any")
    fun any(length: Int? = null) =
        AnyRule(length)

    @JsName("char")
    fun char(c: String) =
        CharacterRule(c)

    fun and(vararg r: Any) =
        AndRule(*r)

    @JsName("and")
    fun jsAnd(r: Array<Any>) =
        AndRule(*r)

    fun or(vararg r: Any) =
        OrRule(*r)

    @JsName("or")
    fun jsOr(r: Array<Any>) =
        OrRule(*r)

    @JsName("many")
    fun many(r: Any) =
        ManyRule(r)

    @JsName("maybe")
    fun maybe(r: Any) =
        MaybeRule(r)

    @JsName("not")
    fun not(r: Any) =
        NotRule(r)

    @JsName("eof")
    fun eof() =
        EofRule.instance

    @JsName("regexWrap")
    fun regex(
        expression: RegExp
    ) = RegexRule.make(expression)

    @JsName("regex")
    fun regex(
        expression: String
    ) = RegexRule(expression)

}