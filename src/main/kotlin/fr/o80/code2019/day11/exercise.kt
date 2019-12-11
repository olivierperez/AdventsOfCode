package fr.o80.code2019.day11

import kotlin.system.measureTimeMillis

fun main() {
    val time = measureTimeMillis {
        val day = Robot(day11Input)
        val partOne = day.compute()
        println("partOne: $partOne")

        day.draw()
    }

    println("${time}ms")
}

