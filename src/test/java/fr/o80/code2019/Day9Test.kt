package fr.o80.code2019

import fr.o80.code2019.day9.Intcode
import fr.o80.code2019.day9.day9Input
import jdk.nashorn.internal.ir.annotations.Ignore
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.math.BigInteger

@DisplayName("Day 7 tests")
internal class Day9Test {

    @Nested
    @DisplayName("Day 7 Part 1")
    inner class PartOne {
        @Test
        fun `example from AOC one`() {
            val input = "1102,34915192,34915192,7,4,7,99,0"

            val day = Intcode()
            val partOne = day.compute(day.parseInput(input))

            assertEquals(BigInteger("1219070632396864"), partOne)
        }

        @Test
        fun `example from AOC two`() {
            val input = "0"

            val day = Intcode()
            val partOne = day.compute(day.parseInput(input))

            assertEquals(-1, partOne)
        }

        @Test
        fun `from my puzzle input`() {
            val day = Intcode()
            val partOne = day.compute(day.parseInput(day9Input))

            assertEquals(-1, partOne)
        }
    }

    @Nested
    @Ignore
    @DisplayName("Day 7 Part 2")
    inner class PartTwo {
        @Test
        fun `example from AOC one`() {
            val input = "3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5"

            val day = Intcode()
            //val partTwo = day.partTwo(input)

            //assertEquals(139629729, partTwo)
        }

        @Test
        fun `example from AOC two`() {
            val input = "3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10"

            val day = Intcode()
//            val partTwo = day.partTwo(input)

//            assertEquals(18216, partTwo)
        }

        @Test
        fun `from my puzzle input`() {
            val day = Intcode()
//            val partTwo = day.partTwo(day9Input)

//            assertEquals(76211147, partTwo)
        }
    }

}
