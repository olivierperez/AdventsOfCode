package fr.o80.kotlin2018.week4

import fr.o80.kotlin2018.week4.MockTool.on
import fr.o80.kotlin2018.week4.MockTool.verify
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
    fun getNullableDouble(): Double?
    fun doInt(x: Int): Int
}

class Week4UnitTest {

    @Test
    @DisplayName("Mocking allows to set the returned value")
    fun shouldMockValue() {
        // Given
        val mockedValues = mock<Example>()
        on { mockedValues.getByte() } justReturn (Byte.MAX_VALUE)
        on { mockedValues.getChar() } justReturn (Char.MAX_VALUE)
        on { mockedValues.getInt() } justReturn (Int.MAX_VALUE)
        on { mockedValues.getLong() } justReturn (Long.MAX_VALUE)
        on { mockedValues.getFloat() } justReturn (Float.MAX_VALUE)
        on { mockedValues.getDouble() } justReturn (Double.MAX_VALUE)

        // When + Then
        assertEquals(Byte.MAX_VALUE, mockedValues.getByte())
        assertEquals(Char.MAX_VALUE, mockedValues.getChar())
        assertEquals(Int.MAX_VALUE, mockedValues.getInt())
        assertEquals(Long.MAX_VALUE, mockedValues.getLong())
        assertEquals(Float.MAX_VALUE, mockedValues.getFloat())
        assertEquals(Double.MAX_VALUE, mockedValues.getDouble())
    }

    @Test
    @DisplayName("Mocking allows to set the returned value as null")
    fun shouldMockNullableValue() {
        // Given
        val mockedValues = mock<Example>()
        on { mockedValues.getNullableDouble() } justReturn (null)

        // When + Then
        assertEquals(null, mockedValues.getDouble())
    }

    @Test
    @DisplayName("Mocking allows to replace functions bodies")
    fun shouldMockBody() {
        // Given
        var count = 1
        val mockedBodies = mock<Example>()
        on { mockedBodies.getInt() } justDo { count++ }

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

    @Test
    @DisplayName("Mocking allows to check multiple method calls")
    fun shouldCheckMultipleCalls() {
        // Given
        val mockedValues = mock<Example>()

        // When
        mockedValues.getInt()
        mockedValues.getInt()

        // Then
        verify(2) { mockedValues.getInt() }
    }

    @Test
    @DisplayName("Mocking allows to specify parameter values")
    fun shouldSetParameter() {
        // Given
        val mock = mock<Example>()
        on { mock.doInt(0) } justReturn (0)
        on { mock.doInt(1) } justReturn (42)
        on { mock.doInt(2) } justReturn (2)

        // When + Then
        assertEquals(0, mock.doInt(0))
        assertEquals(42, mock.doInt(1))
        assertEquals(2, mock.doInt(2))

    }
}
