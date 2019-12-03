package fr.o80.code2019.model

import fr.o80.code2019.day3.Day3
import fr.o80.code2019.day3.Part
import fr.o80.code2019.day3.Point
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day3TestPartTwo {

    @Nested
    inner class PartTwo {
        /*
    ...........
    .+-----+...
    .|.....|...
    .|..+--X-+.
    .|..|..|.|.
    .|.-X--+.|.
    .|..|....|.
    .|.......|.
    .o-------+.
    ...........
        */

        @Test
        fun partTwo_simple() {
            // Given
            val day3 = Day3()
            val input = "R8,U5,L5,D3\n" +
                    "U7,R6,D4,L4"

            // When
            val parsedInput = day3.parseInput(input)
            val result = day3.partTwo(parsedInput)

            // Then
            assertEquals(30, result)
        }

        @Test
        fun partTwo_1() {
            // Given
            val day3 = Day3()
            val input = "R75,D30,R83,U83,L12,D49,R71,U7,L72\n" +
                    "U62,R66,U55,R34,D71,R55,D58,R83"

            // When
            val parsedInput = day3.parseInput(input)
            val result = day3.partTwo(parsedInput)

            // Then
            assertEquals(610, result)
        }

        @Test
        fun partTwo_2() {
            // Given
            val day3 = Day3()
            val input = "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51\n" +
                    "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"

            // When
            val parsedInput = day3.parseInput(input)
            val result = day3.partTwo(parsedInput)

            // Then
            assertEquals(410, result)
        }
    }

    @Nested
    inner class CheckWirePart {

        @Test
        fun testPart_1() {
            // Given
            val part1 = Part(Point(0, 0), Point(0, 5))
            val part2 = Part(Point(0, 0), Point(0, 5))
            val point1 = Point(0, 0)
            val point2 = Point(0, 5)
            val point3 = Point(0, 3)

            // Then
            assertTrue(point1 in part1)
            assertTrue(point2 in part1)
            assertTrue(point3 in part1)
            assertTrue(point1 in part2)
            assertTrue(point2 in part2)
            assertTrue(point3 in part2)
        }

        @Test
        fun testPart_2() {
            // Given
            val part = Part(Point(0, 0), Point(0, 5))
            val point1 = Point(0, -1)
            val point2 = Point(1, 5)
            val point3 = Point(0, 6)

            // Then
            assertFalse(point1 in part)
            assertFalse(point2 in part)
            assertFalse(point3 in part)
        }

        @Test
        fun testPart_3() {
            // Given
            val part = Part(a = Point(x = 146, y = 53), b = Point(x = 146, y = 4))
            val point = Point(x = 146, y = 46)

            // Then
            assertTrue(point in part)
        }
    }
}
