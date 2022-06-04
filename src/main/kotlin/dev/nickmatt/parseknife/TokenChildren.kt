package dev.nickmatt.parseknife

class TokenChildren(
    private val parent: Token
): ArrayList<Token>() {

    override fun add(element: Token): Boolean {
        element.parent = parent
        return super.add(element)
    }

    override fun add(index: Int, element: Token) {
        element.parent = parent
        super.add(index, element)
    }

    override fun addAll(elements: Collection<Token>): Boolean {
        for (e in elements)
            e.parent = parent
        return super.addAll(elements)
    }

    override fun addAll(index: Int, elements: Collection<Token>): Boolean {
        for (e in elements)
            e.parent = parent
        return super.addAll(index, elements)
    }

}