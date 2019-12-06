package fr.o80.code2019

import fr.o80.code2019.day6.Day6
import fr.o80.code2019.day6.day6Input
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day6Test {

    @Nested
    inner class PartOne {
        @Test
        fun test_1() {
            val input = "COM)B\n" +
                "B)C\n" +
                "C)D\n" +
                "D)E\n" +
                "E)F\n" +
                "B)G\n" +
                "G)H\n" +
                "D)I\n" +
                "E)J\n" +
                "J)K\n" +
                "K)L"

            val day = Day6()
            assertEquals(42, day.goBilly(day.parseInput(input)))
        }

        @Test
        fun test_real() {
            val day = Day6()
            assertEquals(140608, day.goBilly(day.parseInput(day6Input)))
        }
    }

    @Nested
    inner class PartTwo {
        @Test
        fun testBilly() {
            val day = Day6()
            assertEquals(42, day.goBilly(mutableListOf()))
        }
    }

}
