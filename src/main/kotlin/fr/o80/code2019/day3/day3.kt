package fr.o80.code2019.day3

import kotlin.math.abs
import kotlin.system.measureTimeMillis

fun main() {
    val time = measureTimeMillis {
        val day3 = Day3()
        val partOne = day3.goBilly(day3.parseInput(day3Input))
        println("PartOne: $partOne")
    }

    println("${time}ms")
}


class Day3 {

    fun goBilly(input: List<List<Move>>): Int {

        val wire1 = toWire(input[0])
        val wire2 = toWire(input[1])
        println("wire1: $wire1")
        println("wire2: $wire2")

        val intersects = computeIntersects(wire1, wire2)
        println("intersects: $intersects")

        return intersects.map { it.norm() }
                .min() ?: -1
    }

    private fun computeIntersects(wire1: List<Part>, wire2: List<Part>): List<Point> {
        val intersects = mutableListOf<Point>()

        wire1.forEachIndexed { i1, part1 ->
            wire2.forEachIndexed { i2, part2 ->
                val intersect: Point? = part1 crossWith part2
                intersect?.let {
                    intersects += it
                }
            }
        }

        return intersects
    }

    private fun toWire(moves: List<Move>): List<Part> {
        var from = Point(0, 0)
        val parts = mutableListOf<Part>()

        for (move in moves) {
            val to = move.computeNewPointFrom(from)
            parts += Part(from, to)
            from = to
        }

        return parts
    }

    fun parseInput(s: String): List<List<Move>> =
            s.split("\n")
                    .map { parseWire(it) }
                    .toList()

    private fun parseWire(wireInstruction: String): List<Move> =
            wireInstruction.split(",")
                    .map { Move(it[0], it.substring(1).toInt()) }
                    .toList()
}

data class Part(val a: Point, val b: Point) {
    infix fun crossWith(other: Part): Point? {
        if (a.x == b.x) {
            if ((other.a.x < a.x && a.x < other.b.x) || other.a.x > a.x && a.x > other.b.x)
                if ((a.y < other.a.y && other.a.y < b.y) || (a.y > other.a.y && other.a.y > b.y))
                    return Point(a.x, other.b.y)

        } else if (a.y == b.y) {
            if ((other.a.y < a.y && a.y < other.b.y) || (other.a.y > a.y && a.y > other.b.y))
                if ((a.x < other.a.x && other.a.x < b.x) || (a.x > other.a.x && other.a.x > b.x))
                    return Point(other.a.x, a.y)

        } else {
            throw IllegalArgumentException("Oops:\n$this\n$other")
        }

        return null
    }
}

data class Move(val direction: Char, val distance: Int) {
    fun computeNewPointFrom(from: Point): Point {
        return when (direction) {
            'U' -> Point(from.x, from.y + distance)
            'D' -> Point(from.x, from.y - distance)
            'R' -> Point(from.x + distance, from.y)
            'L' -> Point(from.x - distance, from.y)
            else -> throw IllegalArgumentException("Je peux pas traiter ton truc! \"$direction\"")
        }
    }
}

data class Point(val x: Int, val y: Int) {
    fun norm(): Int = abs(x) + abs(y)
}
