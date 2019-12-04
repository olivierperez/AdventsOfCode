package fr.o80.code2019.model

import kotlin.system.measureTimeMillis

fun main() {
    val time = measureTimeMillis {
        val partOne = DayX().goBilly(parseInput(modelInput))
        println("PartOne: $partOne")
    }

    println("${time}ms")
}

fun parseInput(s: String): MutableList<Int> =
    s.split(",")
        .map(String::toInt)
        .toMutableList()

class DayX {

    fun goBilly(input: MutableList<Int>): Int = 42

}
