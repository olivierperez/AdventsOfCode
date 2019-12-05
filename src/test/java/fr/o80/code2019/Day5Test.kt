package fr.o80.code2019

import fr.o80.code2019.day5.Intcode
import fr.o80.code2019.day5.Operation.Companion.getMod
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day5Test {

    @Nested
    inner class PartOne {
        @Test
        fun getMod_1() {
            assertEquals(0, getMod(3, 10))
            assertEquals(0, getMod(1, 10))
            assertEquals(1, getMod(2, 10))
            assertEquals(0, getMod(4, 10))
        }

        @Test
        fun partOne_1() {
            val day = Intcode()
            // 01001,4,3,0,33
            // op:1(+) => pos(4) + 3 == 99 + 3 => ecrire(pos(0))
            // 102,4,3,0,99
            // result == 102
            assertEquals(102, day.compute(day.parseInput("1001,4,3,0,99")))
            // 01002,4,3,0,33
            // op:2(*) => pos(4) * 3 == + * 3 => ecrire(pos(0))
            // 297,4,3,0,99
            // result == 297
            assertEquals(297, day.compute(day.parseInput("1002,4,3,0,99")))
        }
    }

    @Nested
    inner class PartTwo {
        @Test
        fun `should jump if true`() {
            val day = Intcode()
            assertEquals(1005, day.compute(day.parseInput("1005,2,7,1101,11,10,0,99")))
            assertEquals(22, day.compute(day.parseInput("1005,6,7,1101,11,11,0,99")))
        }

        @Test
        fun `should jump if false`() {
            val day = Intcode()
            assertEquals(1006, day.compute(day.parseInput("1006,6,7,1101,11,10,0,99")))
            assertEquals(22, day.compute(day.parseInput("1006,2,7,1101,11,11,0,99")))
        }

        @Test
        fun `should check less than`() {
            val day = Intcode()
            assertEquals(1, day.compute(day.parseInput("1107,6,7,0,99")))
            assertEquals(0, day.compute(day.parseInput("1107,6,6,0,99")))
            assertEquals(0, day.compute(day.parseInput("1107,6,5,0,99")))
        }

        @Test
        fun `should check equality`() {
            val day = Intcode()
            assertEquals(0, day.compute(day.parseInput("1108,6,7,0,99")))
            assertEquals(1, day.compute(day.parseInput("1108,6,6,0,99")))
            assertEquals(0, day.compute(day.parseInput("1108,6,5,0,99")))
        }
    }

}
