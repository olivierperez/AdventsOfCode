package fr.o80.code2019.model

import fr.o80.code2019.day4.Day4Part1
import fr.o80.code2019.day4.Day4Part2
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day4Test {

    @Nested
    inner class PartOne {
        @Test
        fun testBilly() {
            val day = Day4Part1()
            val partOne = day.gogogo(264360, 746325)
            assertEquals(945, partOne)

            assertEquals(1, day.gogogo(111111))
            assertEquals(0, day.gogogo(223450))
            assertEquals(0, day.gogogo(123789))
        }
    }

    @Nested
    inner class PartTwo {
        @Test
        fun testBilly() {
            val day = Day4Part2()
            val partOne = day.gogogo(264360, 746325)
            assertEquals(617, partOne)

            assertEquals(1, day.gogogo(112233))
            assertEquals(0, day.gogogo(123444))
            assertEquals(1, day.gogogo(111122))
        }
    }

}
