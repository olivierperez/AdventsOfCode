package fr.o80.day15

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.function.Executable
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class Day15ResolverUnitTest {
    @ParameterizedTest
    @MethodSource("source")
    fun endToEnd(input: String, steps: Int, life: Int, debug: Boolean) {
        // When
        val resolver = Day15Resolver(input, showLife = debug, drawSteps = debug, debugMoves = debug)
        if (debug) resolver.draw()
        resolver.resolve()
        if (debug) resolver.draw()

        // Then
        assertAll(Executable {
            assertEquals(steps, resolver.stepCount, "Step count doesn't fit")
        }, Executable {
            assertEquals(life, resolver.totalLife(), "Total dones't fit")
        })
    }

    companion object {
        @JvmStatic
        fun source() = Stream.of(
            Arguments.of(
                """
            |#######
            |#.G...#
            |#...EG#
            |#.#.#G#
            |#..G#E#
            |#.....#
            |#######
        """.trimMargin(), 47, 590, false
            ),
            Arguments.of(
                """
            |#######
            |#G..#E#
            |#E#E.E#
            |#G.##.#
            |#...#E#
            |#...E.#
            |#######
        """.trimMargin(), 37, 982, true
            ),
            Arguments.of(
                """
            |#######
            |#E..EG#
            |#.#G.E#
            |#E.##E#
            |#G..#.#
            |#..E#.#
            |#######
        """.trimMargin(), 46, 859, false
            )
        )
    }

}