package fr.o80.code2019.day13

import kotlin.system.measureTimeMillis

fun main() {
    val time = measureTimeMillis {
        val day = Game(day13Input)
        val partOne = day.countTileBlock()
        println("Part One: $partOne")

        val partTwo = day.play()
        println("Part Two: $partTwo")
    }

    println("${time}ms")
}

