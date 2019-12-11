package fr.o80.code2019.day10

import kotlin.system.measureTimeMillis

fun main() {
    val time = measureTimeMillis {
        val day = Day10()
        val partOne = day.maxVisibleAsteroids(day.parseInput(day10Input))
        println("PartOne: $partOne")
    }

    println("${time}ms")
}

class Day10 {

    fun parseInput(input: String): Asteroids = Asteroids(input)

    fun maxVisibleAsteroids(asteroids: Asteroids, list: List<Asteroid> = asteroids.all): Int {
        return list
            .map { asteroid -> VisibilityComputer(asteroids.all, asteroid).count() }
            .max()!!
    }

}

class Asteroids(
    private val input: String
) {
    val all: List<Asteroid>

    init {
        val build = mutableListOf<Asteroid>()
        val lines = input.split('\n')
        lines.forEachIndexed { y, s ->
            s.forEachIndexed { x, c ->
                if (c == '#')
                    build.add(Asteroid(x, y))
            }
        }
        all = build
    }

}

data class Asteroid(val x: Int, val y: Int, var visible: Boolean = true)
