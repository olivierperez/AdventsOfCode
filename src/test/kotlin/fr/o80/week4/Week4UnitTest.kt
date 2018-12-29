package fr.o80.week4

import fr.o80.week4.MockTool.justDo
import fr.o80.week4.MockTool.justReturn
import fr.o80.week4.MockTool.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

interface Example {
    fun getByte(): Byte
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
        // Given
        val mockedValues = mock<Example>()
        justReturn(Byte.MAX_VALUE) on { mockedValues.getByte() }
        justReturn(Char.MAX_VALUE) on { mockedValues.getChar() }
        justReturn(Int.MAX_VALUE) on { mockedValues.getInt() }
        justReturn(Long.MAX_VALUE) on { mockedValues.getLong() }
        justReturn(Float.MAX_VALUE) on { mockedValues.getFloat() }
        justReturn(Double.MAX_VALUE) on { mockedValues.getDouble() }

        // When + Then
        assertEquals(Byte.MAX_VALUE, mockedValues.getByte())
        assertEquals(Char.MAX_VALUE, mockedValues.getChar())
        assertEquals(Int.MAX_VALUE, mockedValues.getInt())
        assertEquals(Long.MAX_VALUE, mockedValues.getLong())
        assertEquals(Float.MAX_VALUE, mockedValues.getFloat())
        assertEquals(Double.MAX_VALUE, mockedValues.getDouble())
    }

    @Test
    @DisplayName("Mocking allows to replace functions bodies")
    fun shouldMockBody() {
        // Given
        var count = 1
        val mockedBodies = mock<Example>()
        justDo { count++ } on { mockedBodies.getInt() }

        // When + Then
        assertEquals(1, mockedBodies.getInt())
        assertEquals(2, mockedBodies.getInt())
        assertEquals(3, mockedBodies.getInt())
    }

    @Test
    @DisplayName("Mocking allows to check method calls")
    fun shouldCheckCalls() {
        // Given
        val mockedValues = mock<Example>()

        // When
        mockedValues.getInt()

        // Then
        verify { mockedValues.getInt() }
    }

}
