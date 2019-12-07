package fr.o80.code2019

import fr.o80.code2019.day7.Intcode
import fr.o80.code2019.day7.day7Input
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Day 7 tests")
internal class Day7Test {

    @Nested
    @DisplayName("Day 7 Part 1")
    inner class PartOne {
        @Test
        fun `example from AOC one`() {
            val input = "3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0"

            val day = Intcode()
            val partOne = day.partOne(input)

            assertEquals(65210, partOne)
        }

        @Test
        fun `example from AOC two`() {
            val input = "3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0"

            val day = Intcode()
            val partOne = day.partOne(input)

            assertEquals(54312, partOne)
        }

        @Test
        fun `example from AOC three`() {
            val input = "3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0"

            val day = Intcode()
            val partOne = day.partOne(input)

            assertEquals(43210, partOne)
        }

        @Test
        fun `from my puzzle input`() {
            val day = Intcode()
            val partOne = day.partOne(day7Input)

            assertEquals(30940, partOne)
        }
    }

    @Nested
    @DisplayName("Day 7 Part 2")
    inner class PartTwo {
        @Test
        fun `example from AOC`() {
            val input = "3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5"

            val day = Intcode()
            val partTwo = day.partTwo(input)

            assertEquals(139629729, partTwo)
        }

        @Test
        fun `from my puzzle input`() {
            val day = Intcode()
        }
    }

}
