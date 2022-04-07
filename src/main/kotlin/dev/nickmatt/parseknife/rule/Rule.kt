package dev.nickmatt.parseknife.rule

import dev.nickmatt.parseknife.Cursor

interface Rule {

    fun test(c: Cursor): Int

}