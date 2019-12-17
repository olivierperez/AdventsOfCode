package fr.o80.code2019.day13

import kotlin.system.measureTimeMillis

fun main() {
    val time = measureTimeMillis {
        val day = Game(day13Input)
        val partOne = day.countTileBlock()
        println("partOne: $partOne")
    }

    println("${time}ms")
}

