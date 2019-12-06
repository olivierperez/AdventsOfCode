package fr.o80.code2019.model

import kotlin.system.measureTimeMillis

fun main() {
    val time = measureTimeMillis {
        val day = DayX()
        val partOne = day.goBilly(day.parseInput(modelInput))
        println("PartOne: $partOne")
    }

    println("${time}ms")
}

class DayX {

    fun parseInput(input: String): MutableList<Int> =
        input.split(",")
            .map(String::toInt)
            .toMutableList()

    fun goBilly(input: MutableList<Int>): Int {
        return 42
    }

}
