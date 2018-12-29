package fr.o80.week4

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

interface Example {
    fun getChar(): Char
    fun getInt(): Int
    fun getLong(): Long
    fun getFloat(): Float
    fun getDouble(): Double
}

class Week4UnitTest {

    @Test
    fun shouldMockIt() {
        val a = mock<Example>()
        setReturnValue({ a.getChar() }, '2')
        setReturnValue({ a.getInt() }, 2)
        setReturnValue({ a.getLong() }, 2)
        setReturnValue({ a.getFloat() }, 2)
        setReturnValue({ a.getDouble() }, 2)
        assertEquals(2, a.getInt())

        var i = 1
        val b = mock<Example>()
        setBody({ b.getInt() }, { i++ })
        assertEquals(1, b.getInt())
        assertEquals(2, b.getInt())
        assertEquals(3, b.getInt())
    }
}