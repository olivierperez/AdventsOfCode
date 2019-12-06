package fr.o80.code2019.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class DayXTest {

    @Nested
    inner class PartOne {
        @Test
        fun testBilly() {
            val day = DayX()
            assertEquals(42, day.goBilly(mutableListOf()))
        }
    }

    @Nested
    inner class PartTwo {
        @Test
        fun testBilly() {
            val day = DayX()
            assertEquals(42, day.goBilly(mutableListOf()))
        }
    }

}