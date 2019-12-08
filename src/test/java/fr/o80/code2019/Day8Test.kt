package fr.o80.code2019

import fr.o80.code2019.day8.Day8
import fr.o80.code2019.day8.day8Input
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day8Test {

    @Nested
    @DisplayName("Day 8 Part 1")
    inner class PartOne {
        @Test
        fun `example from AOC`() {
            val input = "123456789012"
            val day = Day8(3, 2)
            assertEquals(1, day.partOne(day.parseInput(input)))
        }

        @Test
        fun `from my puzzle input`() {
            val day = Day8(25, 6)
            assertEquals(1441, day.partOne(day.parseInput(day8Input)))
        }
    }

    @Nested
    inner class PartTwo {
        @Test
        fun `example from AOC`() {
            val day = Day8(0, 0)
            assertEquals(42, day.partOne(mutableListOf()))
        }
    }

}
