package fr.o80.code2019.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class DayXTest {

    @Nested
    @DisplayName("Day _ Part 1")
    inner class PartOne {
        @Test
        fun `example from AOC`() {
            // Given
            val input = ""

            // When
            val day = DayX()
            val goBilly = day.goBilly(day.parseInput(input))

            // Then
            assertEquals(42, goBilly)
        }
    }

    @Nested
    @DisplayName("Day _ Part 2")
    inner class PartTwo {
        @Test
        fun `example from AOC`() {
            // Given
            val input = ""

            // When
            val day = DayX()
            val goBilly = day.goBilly(day.parseInput(input))

            // Then
            assertEquals(42, goBilly)
        }
    }

}
