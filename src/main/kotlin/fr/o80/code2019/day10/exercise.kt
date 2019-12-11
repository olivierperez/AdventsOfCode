package fr.o80.code2019.day10

import kotlin.math.PI
import kotlin.math.atan2
import kotlin.system.measureTimeMillis

typealias Angle = Double

fun main() {
    val time = measureTimeMillis {
        val day = Day10()
        val asteroids = day.parseInput(day10Input)

        val partOne = day.maxVisibleAsteroids(asteroids)
        println("PartOne: $partOne")

        val partTwo = day.destroyUntil(asteroids, 200)
        println("PartTwo: $partTwo")
    }

    println("${time}ms")
}

class Day10 {

    fun parseInput(input: String): Asteroids = Asteroids(input)

    fun maxVisibleAsteroids(asteroids: Asteroids, list: List<Asteroid> = asteroids.all): Int {
        return findBestAsteroid(list, asteroids).visibleAsteroids
    }

    fun destroyUntil(asteroids: Asteroids, nth: Int): Int {
        val station = findBestAsteroid(asteroids.all, asteroids)
        val laser = Laser(station, asteroids)
        val nextAsteroid = laser.destroyUnit(nth)
        return nextAsteroid.x * 100 + nextAsteroid.y
    }

    private fun findBestAsteroid(list: List<Asteroid>, asteroids: Asteroids): Asteroid {
        return list
            .onEach { asteroid ->
                val count = VisibilityComputer(asteroids.all, asteroid).count()
                asteroid.visibleAsteroids = count
            }
            .maxBy { asteroid -> asteroid.visibleAsteroids }!!
    }

}

class Asteroids(
    input: String
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

data class Asteroid(
    val x: Int,
    val y: Int,
    var visible: Boolean = true,
    var visibleAsteroids: Int = Int.MIN_VALUE,
    var angle: Double = Double.MIN_VALUE,
    var distance: Int = Int.MIN_VALUE
)
