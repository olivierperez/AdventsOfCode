package fr.o80.code2019.day8

import kotlin.system.measureTimeMillis

fun main() {
    val time = measureTimeMillis {
        val day = Day8(25, 6)
        val partOne = day.partOne(day.parseInput(day8Input))
        println("PartOne: $partOne")
    }

    println("${time}ms")
}

class Day8(val columns: Int, val rows: Int) {

    fun parseInput(input: String): List<Layer> =
        input.chunked(columns * rows)
            .map(::Layer)
            .toList()

    fun partOne(layers: List<Layer>): Int {
        return layers
            .minBy { it.count('0') }!!
            .checksum()
    }

}

class Layer(val input: String) {
    fun count(c: Char): Int {
        return input.count { it == c }
    }

    fun checksum(): Int {
        return count('1') * count('2')
    }
}