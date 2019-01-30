package fr.o80.code2018.day6

import java.lang.Integer.max
import java.lang.Integer.min
import java.lang.Math.abs
import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis { println("6.1 => ${exercise_6_1()}") }.also { println("Time: ${it}ms") }
    measureTimeMillis { println("6.2 => ${exercise_6_2()}") }.also { println("Time: ${it}ms") }
}

val day6demo = """1, 1
1, 6
8, 3
3, 4
5, 5
8, 9"""

fun exercise_6_2(): Int {
    val input = day6input
    val max = 10000

    val points = input
            .split('\n')
            .map {
                val coords = it.split(", ")
                Point(coords[0].toInt(), coords[1].toInt())
            }

    val outline = points.fold(Rectangle()) { r, point ->
        Rectangle(
                left = min(r.left, point.x),
                top = min(r.top, point.y),
                right = max(r.right, point.x),
                bottom = max(r.bottom, point.y)
        )
    }

    var interrestingPoints = 0

    for (x in outline.left - 1..outline.right + 1) {
        for (y in outline.top - 1..outline.bottom + 1) {
            val distanceWithPoints = points.fold(0) { acc, point ->
                acc + (point - Point(x, y))
            }

            if (distanceWithPoints < max) {
                interrestingPoints ++
            }
        }
    }

    return interrestingPoints
}

fun exercise_6_1(): Int {
    val points = day6input
            .split('\n')
            .map {
                val coords = it.split(", ")
                Point(coords[0].toInt(), coords[1].toInt())
            }

    val outline = points.fold(Rectangle()) { r, point ->
        Rectangle(
                left = min(r.left, point.x),
                top = min(r.top, point.y),
                right = max(r.right, point.x),
                bottom = max(r.bottom, point.y)
        )
    }

    val bestChoices = mutableMapOf<Point, Int>()
    val disqualified = mutableSetOf<Point>()

    for (x in outline.left - 1..outline.right + 1) {
        for (y in outline.top - 1..outline.bottom + 1) {
            val distances = points.map { point -> Pair(point, point - Point(x, y)) }
            val minDistance = distances.map { pair -> pair.second }.min()
            val closest = distances.filter { pair -> pair.second == minDistance }.map { it.first }

            if (closest.size == 1) {
                if (outline.contains(x, y)) {
                    val value = bestChoices.getOrDefault(closest[0], 0)
                    bestChoices[closest[0]] = value + 1
                } else {
                    closest.forEach { point -> disqualified.add(point) }
                }
            }
        }
    }

    val biggerZone = bestChoices.filterNot { it.key in disqualified }.maxBy { (_, value) -> value }?.value

    return biggerZone ?: 0
}

data class Point(val x: Int, val y: Int) {
    inline operator fun minus(other: Point): Int =
            abs(x - other.x) + abs(y - other.y)
}

data class Rectangle(
        val left: Int = Int.MAX_VALUE,
        val top: Int = Int.MAX_VALUE,
        val right: Int = Int.MIN_VALUE,
        val bottom: Int = Int.MIN_VALUE
) {
    fun contains(x: Int, y: Int): Boolean =
            x in left..right && y in top..bottom
}
