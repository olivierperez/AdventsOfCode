package fr.o80.code2019

import fr.o80.code2019.day6.Day6
import fr.o80.code2019.day6.day6Input
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Day 6 tests")
internal class Day6Test {

    @Nested
    @DisplayName("Day 6 Part 1")
    inner class PartOne {
        @Test
        fun `example from AOC`() {
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
        fun `from my puzzle input`() {
            val day = Day6()
            assertEquals(140608, day.goBilly(day.parseInput(day6Input)))
        }
    }

    @Nested
    @DisplayName("Day 6 Part 2")
    inner class PartTwo {
        @Test
        fun `example from AOC`() {
            val input_1 = "COM)B\n" +
                "B)C\n" +
                "C)D\n" +
                "D)E\n" +
                "E)F\n" +
                "B)G\n" +
                "G)H\n" +
                "D)I\n" +
                "E)J\n" +
                "J)K\n" +
                "K)L\n" +
                "K)YOU\n" +
                "I)SAN"
            val day = Day6()
            assertEquals(4, day.computePathToSanta(day.parseInput(input_1)))
        }

        @Test
        fun `from my puzzle input`() {
            val input_1 = day6Input
            val day = Day6()
            assertEquals(337, day.computePathToSanta(day.parseInput(input_1)))
        }
    }

}
