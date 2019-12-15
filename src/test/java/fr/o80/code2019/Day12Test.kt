package fr.o80.code2019

import fr.o80.code2019.day12.ApplyGravity
import fr.o80.code2019.day12.ApplyVelocity
import fr.o80.code2019.day12.Day12
import fr.o80.code2019.day12.day12Input
import fr.o80.code2019.day12.Moon
import fr.o80.code2019.day12.Vec3
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day12Test {

    @Nested
    @DisplayName("Day 12 Part 1")
    inner class PartOne {
        @Test
        fun `Apply gravity`() {
            // Given
            val gravity = ApplyGravity()
            val moons = listOf(
                Moon.at(3, 99, 99),
                Moon.at(5, 99, 99)
            )

            // When
            gravity(moons[0], moons)
            gravity(moons[1], moons)

            // Then
            assertEquals(1, moons[0].velocity.x)
            assertEquals(-1, moons[1].velocity.x)
            assertEquals(0, moons[0].velocity.y)
            assertEquals(0, moons[1].velocity.y)
            assertEquals(0, moons[0].velocity.z)
            assertEquals(0, moons[1].velocity.z)
        }

        @Test
        fun `Apply velocity`() {
            // Given
            val velocity = ApplyVelocity()
            val moon = Moon(
                Vec3(3, 10, -6),
                Vec3(1, 1, 1)
            )

            // When
            velocity(moon)

            // Then
            assertEquals(4, moon.position.x)
            assertEquals(11, moon.position.y)
            assertEquals(-5, moon.position.z)
        }

        @Test
        fun `Update once`() {
            // Given
            val day = Day12()
            val moons = listOf(
                Moon.at(3, 99, 99),
                Moon.at(5, 99, 99)
            )

            // When
            day.computeFullEnergy(moons, 1)

            // Then
            assertEquals(Vec3(4, 99, 99), moons[0].position)
            assertEquals(Vec3(4, 99, 99), moons[1].position)
        }

        @Test
        fun `Update twice`() {
            // Given
            val day = Day12()
            val moons = listOf(
                Moon.at(3, 99, 99),
                Moon.at(5, 99, 99)
            )

            // When
            day.computeFullEnergy(moons, 2)

            // Then
            assertEquals(Vec3(5, 99, 99), moons[0].position)
            assertEquals(Vec3(3, 99, 99), moons[1].position)
        }

        @Test
        fun `Compute energy`() {
            // Given
            val moon = Moon(
                Vec3(3, 10, -6),
                Vec3(1, 1, 1)
            )

            // When
            val energy = moon.fullEnergy()

            // Then
            assertEquals(57, energy)
        }

        @Test
        fun `From AoC`() {
            // Given
            val day = Day12()
            val moons = listOf(
                Moon(Vec3(x = -1, y = 0, z = 2), Vec3(0, 0, 0)),
                Moon(Vec3(x = 2, y = -10, z = -7), Vec3(0, 0, 0)),
                Moon(Vec3(x = 4, y = -8, z = 8), Vec3(0, 0, 0)),
                Moon(Vec3(x = 3, y = 5, z = -1), Vec3(0, 0, 0))
            )

            // When+1
            day.computeFullEnergy(moons, 1)

            // Then
            assertEquals(Vec3(x = 3, y = -1, z = -1), moons[0].velocity)
            assertEquals(Vec3(x = 1, y = 3, z = 3), moons[1].velocity)
            assertEquals(Vec3(x = -3, y = 1, z = -3), moons[2].velocity)
            assertEquals(Vec3(x = -1, y = -3, z = 1), moons[3].velocity)
            // -----------
            assertEquals(Vec3(x = 2, y = -1, z = 1), moons[0].position)
            assertEquals(Vec3(x = 3, y = -7, z = -4), moons[1].position)
            assertEquals(Vec3(x = 1, y = -7, z = 5), moons[2].position)
            assertEquals(Vec3(x = 2, y = 2, z = 0), moons[3].position)

            // When +1
            day.computeFullEnergy(moons, 1)

            // Then
            assertEquals(Vec3(x = 3, y = -2, z = -2), moons[0].velocity)
            assertEquals(Vec3(x = -2, y = 5, z = 6), moons[1].velocity)
            assertEquals(Vec3(x = 0, y = 3, z = -6), moons[2].velocity)
            assertEquals(Vec3(x = -1, y = -6, z = 2), moons[3].velocity)
            // -----------
            assertEquals(Vec3(x = 5, y = -3, z = -1), moons[0].position)
            assertEquals(Vec3(x = 1, y = -2, z = 2), moons[1].position)
            assertEquals(Vec3(x = 1, y = -4, z = -1), moons[2].position)
            assertEquals(Vec3(x = 1, y = -4, z = 2), moons[3].position)

            // When +8
            val energy = day.computeFullEnergy(moons, 8)

            // Then
            assertEquals(Vec3(x = -3, y = -2, z = 1), moons[0].velocity)
            assertEquals(Vec3(x = -1, y = 1, z = 3), moons[1].velocity)
            assertEquals(Vec3(x = 3, y = 2, z = -3), moons[2].velocity)
            assertEquals(Vec3(x = 1, y = -1, z = -1), moons[3].velocity)
            // -----------
            assertEquals(Vec3(x = 2, y = 1, z = -3), moons[0].position)
            assertEquals(Vec3(x = 1, y = -8, z = 0), moons[1].position)
            assertEquals(Vec3(x = 3, y = -6, z = 1), moons[2].position)
            assertEquals(Vec3(x = 2, y = 0, z = 4), moons[3].position)

            assertEquals(179, energy)
        }

        @Test
        fun `From my puzzle`() {
            // Given
            val day = Day12()

            // When
            val energy = day.computeFullEnergy(day12Input, 1000)

            // Then
            assertEquals(7471, energy)
        }
    }

}
