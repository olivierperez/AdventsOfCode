package fr.o80.week4

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

interface Example {
    fun getInt(): Int
}

class Week4UnitTest {

    @Test
    fun shouldMockIt() {
        val a = mock<Example>()
        setReturnValue({ a.getInt() }, 2)
        assertEquals(2, a.getInt())

        /*
        var i = 1
        val b =mock<Example>()
        setBody({b.getInt()}, { i++ })
        assertEquals(1, b.getInt())
        assertEquals(2, b.getInt())
        assertEquals(3, b.getInt())
        */
    }
}