package fr.o80.day11

import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis { println("11.1 => ${exercise_11_1(2187)}") }.also { println("Time: ${it}ms") }
    measureTimeMillis { println("11.2 => ${exercise_11_2()}") }.also { println("Time: ${it}ms") }
}

fun exercise_11_1(gridId: Int): String {
    val fuelMap: List<Int> = (Point(1, 1)..Point(300, 300))
            .asSequence()
            .map { point ->
                fuel(gridId, point.x, point.y)
            }
            .toList()
    val max = (Point(1, 1)..Point(300 - 3, 300 - 3))
            .asSequence()
            .map { point ->
                Pair(point, maxField(fuelMap, point))
            }
            .maxBy { it.second }!!

    return "${max.first.x},${max.first.y}"
}

fun fuel(gridId: Int, x: Int, y: Int): Int {
    val rackId = x + 10
    val powerLevel = y * rackId
    val a = powerLevel + gridId
    val b = a * rackId
    val hundreds = (b / 100) - ((b / 1000) * 10)
    return hundreds - 5
}

fun maxField(fuelMap: List<Int>, point: Point): Int {
    val (x, y) = point
    return fuelMap[x + (y - 1) * 300] +
            fuelMap[x + (y - 1) * 300 + 1] +
            fuelMap[x + (y - 1) * 300 + 2] +
            fuelMap[x + (y - 1) * 300 + 300] +
            fuelMap[x + (y - 1) * 300 + 301] +
            fuelMap[x + (y - 1) * 300 + 302] +
            fuelMap[x + (y - 1) * 300 + 600] +
            fuelMap[x + (y - 1) * 300 + 601] +
            fuelMap[x + (y - 1) * 300 + 602]
}

fun exercise_11_2(): Int {
    return 0
}

data class Point(val x: Int, val y: Int) {
    operator fun rangeTo(to: Point): PointIterator = PointIterator(this, to)
}

class PointIterator(
        private val start: Point,
        private val endInclusive: Point
) : Iterator<Point> {
    var current = start

    override fun hasNext(): Boolean = current.x < endInclusive.x || current.y < endInclusive.y

    override fun next(): Point {
        return if (current.x < endInclusive.x) {
            Point(current.x + 1, current.y)
        } else {
            Point(1, current.y + 1)
        }.also { current = it }
    }

}