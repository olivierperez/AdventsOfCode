package fr.o80.code2019.model

import fr.o80.code2019.day8.Day8
import kotlin.system.measureTimeMillis

fun main() {
    val time = measureTimeMillis {
        val day = Day8(25, 6)
        val partOne = day.partOne(day.parseInput(modelInput))
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
        return -1
    }

}
