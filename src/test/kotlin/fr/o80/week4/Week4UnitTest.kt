package fr.o80.week4

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
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
    @DisplayName("Mocking allows to set the returned value")
    fun shouldMockValue() {
        val mockedValues = mock<Example>()
        setReturnValue({ mockedValues.getChar() }, '2')
        setReturnValue({ mockedValues.getInt() }, 2)
        setReturnValue({ mockedValues.getLong() }, 2)
        setReturnValue({ mockedValues.getFloat() }, 2)
        setReturnValue({ mockedValues.getDouble() }, 2)
        assertEquals(2, mockedValues.getInt())
    }

    @Test
    @DisplayName("Mocking allows to replace functions bodies")
    fun shouldMockBody() {
        var count = 1
        val mockedBodies = mock<Example>()
        setBody({ mockedBodies.getInt() }, { count++ })
        assertEquals(1, mockedBodies.getInt())
        assertEquals(2, mockedBodies.getInt())
        assertEquals(3, mockedBodies.getInt())
    }

    @Test
    @DisplayName("Mocking allows to check method calls")
    fun shouldCheckCalls() {
        val mockedValues = mock<Example>()
        mockedValues.getInt()
        verify { mockedValues.getInt() }
    }

}