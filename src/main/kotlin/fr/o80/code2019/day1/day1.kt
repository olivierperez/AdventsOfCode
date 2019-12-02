package fr.o80.code2019.day1

import kotlin.math.ceil
import kotlin.math.round

fun main() {
    println(fuelRequired(12))
    println(fuelRequired(14))
    println(fuelRequired(1969))
    println(fuelRequired(100756))

    println("--------------")
    println(totalFuelRequired(14))
    println(totalFuelRequired(1969))
    println(totalFuelRequired(100756))

    println("--------------")

    val total = day1input.split("\n")
            .asSequence()
            .map { it.toInt() }
//            .partOne()
            .partTwo()
            .fold(0) { acc, i ->
                println("$acc + $i = ${acc + i}")
                acc + i
            }

    println("total: $total")
}

private fun Sequence<Int>.partOne(): Sequence<Int> = map(::fuelRequired)
private fun Sequence<Int>.partTwo(): Sequence<Int> = map(::totalFuelRequired)

fun fuelRequired(mass: Int): Int = (mass / 3f - 2).toInt()

fun totalFuelRequired(mass: Int): Int {
    val initFuel = fuelRequired(mass)
    return if (initFuel <= 0) {
        0
    } else {
        initFuel + totalFuelRequired(initFuel)
    }
}