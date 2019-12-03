package fr.o80.code2019.model

import fr.o80.code2019.day3.Day3
import fr.o80.code2019.day3.Part
import fr.o80.code2019.day3.Point
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day3Test {
/*
...+...
...|...
+--X-+.
...+...

1,2; 6;2
4,1; 4,4
*/
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
    /*
...........
.+-----+...
.|.....|...
.|..+--X-+.
.|..|..|.|.
.3456--+.|.
.2..|....|.
.1.......|.
.o-------+.
...........
    */

    @Test
    fun testBilly_0() {
        // Given
        val day3 = Day3()
        val input = "R8,U5,L5,D3\n" +
                "U7,R6,D4,L4"

        // When
        val parsedInput = day3.parseInput(input)
        val result = day3.goBilly(parsedInput)

        // Then
        assertEquals(6, result)
    }
    @Test
    fun testBilly_1() {
        // Given
        val day3 = Day3()
        val input = "R75,D30,R83,U83,L12,D49,R71,U7,L72\n" +
                "U62,R66,U55,R34,D71,R55,D58,R83"

        // When
        val parsedInput = day3.parseInput(input)
        val result = day3.goBilly(parsedInput)

        // Then
        assertEquals(159, result)
    }

    @Test
    fun testBilly_2() {
        // Given
        val day3 = Day3()
        val input = "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51\n" +
                "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"

        // When
        val parsedInput = day3.parseInput(input)
        val result = day3.goBilly(parsedInput)

        // Then
        assertEquals(135, result)
    }

    @Test
    fun testPart_0() {
        // Given
        val part1 = Part(Point(1,2), Point(6,2))
        val part2 = Part(Point(4,1), Point(4,4))

        // When
        val inter = (part1 crossWith part2)!!

        // Then
        assertEquals(4, inter.x)
        assertEquals(2, inter.y)
    }

    @Test
    fun testPart_1() {
        // Given
        val part1 = Part(Point(1,2), Point(6,2))
        val part2 = Part(Point(4,1), Point(4,4))

        // When
        val inter = (part2 crossWith part1)!!

        // Then
        assertEquals(4, inter.x)
        assertEquals(2, inter.y)
    }

    @Test
    fun testPart_2() {
        // Given
        val part1 = Part(Point(6,2), Point(1,2))
        val part2 = Part(Point(4,1), Point(4,4))

        // When
        val inter = (part1 crossWith part2)!!

        // Then
        assertEquals(4, inter.x)
        assertEquals(2, inter.y)
    }

    @Test
    fun testPart_3() {
        // Given
        val part1 = Part(Point(6,2), Point(1,2))
        val part2 = Part(Point(4,1), Point(4,4))

        // When
        val inter = (part2 crossWith part1)!!

        // Then
        assertEquals(4, inter.x)
        assertEquals(2, inter.y)
    }

/*
...+...
...|...
+--X-+.
...+...

1,2; 6;2
4,1; 4,4
*/

}
