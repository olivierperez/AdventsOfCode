package fr.o80.code2018.day11

import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis { println("11.1 => ${exercise_11_1(2187)}") }.also { println("Time: ${it}ms") }
    measureTimeMillis { println("11.2 => ${exercise_11_2(2187)}") }.also { println("Time: ${it}ms") }
}

fun exercise_11_1(gridId: Int): String {
    val fuelMap: List<Int> = (Point(1, 1)..Point(300, 300))
            .asSequence()
            .map { point ->
                fuel(gridId, point.x, point.y)
            }
            .toList()

    val max = find(fuelMap, 3)
    return "${max.first.x},${max.first.y}"
}

fun exercise_11_2(gridId: Int): String {
    val fuelMap: List<Int> = (Point(1, 1)..Point(300, 300))
            .asSequence()
            .map { point ->
                fuel(gridId, point.x, point.y)
            }
            .toList()
    val max = (1..300)
            .asSequence()
            .map { size ->
                val (point, fuel) = find(fuelMap, size)
                Triple(point, fuel, size)
            }
            .maxBy { it.second }!!
    return "${max.first.x},${max.first.y},${max.third}"
}

private fun find(fuelMap: List<Int>, selectionSize: Int): Pair<Point, Int> {
    return (Point(1, 1)..Point(300 - selectionSize + 1, 300 - selectionSize + 1))
            .asSequence()
            .map { point ->
                val maxFuel = maxField(fuelMap, point, selectionSize)
                Pair(point, maxFuel)
            }
            .maxBy { it.second }
            ?: Pair(Point(-1, -1), -1)
}

fun fuel(gridId: Int, x: Int, y: Int): Int {
    val rackId = x + 10
    val powerLevel = y * rackId
    val a = powerLevel + gridId
    val b = a * rackId
    val hundreds = (b / 100) - ((b / 1000) * 10)
    return hundreds - 5
}

fun maxField(fuelMap: List<Int>, point: Point, selectionSize: Int): Int {
    val (x, y) = point
    var fuel = 0
    for (i in 0 until 0 + selectionSize) {
        for (j in 0 until 0 + selectionSize) {
            fuel += fuelMap[x - 1 + (y - 1) * 300 + i + (300 * j)]
        }
    }
    return fuel
}

data class Point(val x: Int, val y: Int) {
    operator fun rangeTo(to: Point): PointIterator = PointIterator(this, to)
}

class PointIterator(
        start: Point,
        private val endInclusive: Point
                   ) : Iterator<Point> {
    private var current = start

    override fun hasNext(): Boolean = current.x < endInclusive.x || current.y < endInclusive.y

    override fun next(): Point {
        return current.also {
            current = if (current.x < endInclusive.x) {
                Point(current.x + 1, current.y)
            } else {
                Point(1, current.y + 1)
            }
        }
    }

}