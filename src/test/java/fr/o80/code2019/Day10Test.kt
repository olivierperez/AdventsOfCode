package fr.o80.code2019

import fr.o80.code2019.day10.Asteroid
import fr.o80.code2019.day10.Asteroids
import fr.o80.code2019.day10.Day10
import fr.o80.code2019.day10.Laser
import fr.o80.code2019.day10.day10Ex5Input
import fr.o80.code2019.day10.day10Input
import fr.o80.code2019.day10.isHiddenBy
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.math.PI

internal class Day10Test {

    @Nested
    @DisplayName("Day 10 Part 1")
    inner class PartOne {
        @Test
        fun `Check asteroid is hidden`() {
            // Given
            val origin = Asteroid(3, 4)
            val visible = Asteroid(2, 2)
            val hidden = Asteroid(1, 0)

            // When
            val hiddenIsHidden = hidden.isHiddenBy(visible, origin)

            // Then
            assertTrue(hiddenIsHidden)
        }

        @Test
        fun `Check asteroid is visible`() {
            // Given
            val origin = Asteroid(4, 6)
            val visible = Asteroid(1, 0)
            val otherVisible = listOf(
                Asteroid(2, 2),
                Asteroid(1, 1),
                Asteroid(3, 4),
                Asteroid(4, 50),
                Asteroid(50, 6)
            )

            // When

            // Then
            otherVisible.forEach { other ->
                assertFalse(other.isHiddenBy(visible, origin))
            }
        }

        @Test
        fun `Quick checks`() {
            assertTrue(Asteroid(9, 0).isHiddenBy(Asteroid(4, 5), Asteroid(3, 6)))

            assertTrue(Asteroid(0, -2).isHiddenBy(Asteroid(2, 2), Asteroid(3, 4)))
            assertTrue(Asteroid(0, -2).isHiddenBy(Asteroid(1, 0), Asteroid(3, 4)))

            assertFalse(Asteroid(1, 8).isHiddenBy(Asteroid(8, 8), Asteroid(5, 8)))

            assertTrue(Asteroid(3, 0).isHiddenBy(Asteroid(3, 2), Asteroid(3, 4)))

            assertFalse(Asteroid(x = 1, y = 0).isHiddenBy(Asteroid(3, 2), Asteroid(3, 4)))
            assertFalse(Asteroid(x = 4, y = 0).isHiddenBy(Asteroid(3, 2), Asteroid(3, 4)))
            assertFalse(Asteroid(x = 0, y = 2).isHiddenBy(Asteroid(3, 2), Asteroid(3, 4)))
            assertFalse(Asteroid(x = 1, y = 2).isHiddenBy(Asteroid(3, 2), Asteroid(3, 4)))
            assertFalse(Asteroid(x = 2, y = 2).isHiddenBy(Asteroid(3, 2), Asteroid(3, 4)))
            assertFalse(Asteroid(x = 4, y = 2).isHiddenBy(Asteroid(3, 2), Asteroid(3, 4)))

            assertTrue(Asteroid(1, 0).isHiddenBy(Asteroid(2, 2), Asteroid(3, 4)))
            assertFalse(Asteroid(2, 2).isHiddenBy(Asteroid(1, 0), Asteroid(3, 4)))

            assertFalse(Asteroid(1, 0).isHiddenBy(Asteroid(4, 0), Asteroid(3, 4)))

            assertFalse(Asteroid(4, 4).isHiddenBy(Asteroid(4, 3), Asteroid(3, 4)))

            assertFalse(Asteroid(2, 2).isHiddenBy(Asteroid(1, 0), Asteroid(4, 6)))
        }

        @Test
        fun `Example 01`() {
            // Given
            val input = """.#..#
                                 |.....
                                 |#####
                                 |....#
                                 |...##""".trimMargin()

            // When
            val day = Day10()
            val max = day.maxVisibleAsteroids(day.parseInput(input))

            // Then
            assertEquals(8, max)
        }

        @Test
        fun `Example 02`() {
            // Given
            val input = """......#.#.
                                 |#..#.#....
                                 |..#######.
                                 |.#.#.###..
                                 |.#..#.....
                                 |..#....#.#
                                 |#..#....#.
                                 |.##.#..###
                                 |##...#..#.
                                 |.#....####""".trimMargin()

            // When
            val day = Day10()
            val max = day.maxVisibleAsteroids(day.parseInput(input))

            // Then
            assertEquals(33, max)
        }

        @Test
        fun `Example 03`() {
            // Given
            val input = """#.#...#.#.
.###....#.
.#....#...
##.#.#.#.#
....#.#.#.
.##..###.#
..#...##..
..##....##
......#...
.####.###.""".trimMargin()

            // When
            val day = Day10()
            val max = day.maxVisibleAsteroids(day.parseInput(input))

            // Then
            assertEquals(35, max)
        }

        @Test
        fun `Example 04`() {
            // Given
            val input = """.#..#..###
####.###.#
....###.#.
..###.##.#
##.##.#.#.
....###..#
..#.#..#.#
#..#.#.###
.##...##.#
.....#.#..""".trimMargin()

            // When
            val day = Day10()
            val max = day.maxVisibleAsteroids(day.parseInput(input), listOf(Asteroid(6, 3)))

            // Then
            assertEquals(41, max)
        }

        @Test
        fun `Example 05`() {
            // Given

            // When
            val day = Day10()
            val max = day.maxVisibleAsteroids(day.parseInput(day10Ex5Input))

            // Then
            assertEquals(210, max)
        }

        @Test
        fun `From my puzzle`() {
            // Given

            // When
            val day = Day10()
            val max = day.maxVisibleAsteroids(day.parseInput(day10Input))

            // Then
            assertEquals(263, max)
        }
    }

    @Nested
    @DisplayName("Day 10 Part 2")
    inner class PartTwo {
        @Test
        fun `Check angles`() {
            val laser = Laser(Asteroid(0, 0), Asteroids(""))

            assertEquals(0.0, laser.angleWith(Asteroid(0, -15)), 0.00001) // 0
            assertEquals(PI / 2, laser.angleWith(Asteroid(15, 0)), 0.00001) // PI / 2
            assertEquals(PI, laser.angleWith(Asteroid(0, 15)), 0.00001) // PI
            assertEquals(3 * PI / 2, laser.angleWith(Asteroid(-15, 0)), 0.00001) // 3 PI / 2
        }
    }

}
