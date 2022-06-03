package dev.nickmatt.parseknife.rule

object r {

    operator fun invoke(vararg args: Any) =
        Rule.infer(*args)

    inline fun ref(crossinline resolve: () -> Rule) =
        Rule.refer(resolve)

    inline fun wrap(r: Rule) =
        Rule.wrap(r)

    fun any(length: Int? = null) =
        AnyRule(length)

    fun char(c: Char) =
        CharacterRule(c)

    fun then(vararg r: Any) =
        ThenRule(*r)

    fun or(vararg r: Any) =
        OrRule(*r)

    fun many(r: Any) =
        ManyRule(r)

    fun maybe(r: Any) =
        MaybeRule(r)

    fun not(r: Any) =
        NotRule(r)

    fun eof() =
        EofRule()

}