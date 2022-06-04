package dev.nickmatt.parseknife.test

import dev.nickmatt.parseknife.Source

fun testSourceLocation() {
    val source = Source("abc\n123")

    val (row1, column1) = source.makeCoords(0)
    assert(row1 == 1)
    assert(column1 == 1)

    val (row2, column2) = source.makeCoords(3)
    assert(row2 == 1)
    assert(column2 == 4)

    val (row3, column3) = source.makeCoords(4)
    assert(row3 == 2)
    assert(column3 == 1)

    val (row4, column4) = source.makeCoords(7)
    assert(row4 == 2)
    assert(column4 == 4)
}