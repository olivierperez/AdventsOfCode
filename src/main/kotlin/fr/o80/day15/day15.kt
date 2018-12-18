package fr.o80.day15

import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis { println("15.1 => ${exercise_15_1()}") }.also { println("Time: ${it}ms\n") }
    measureTimeMillis { println("15.2 => ${exercise_15_2()}") }.also { println("Time: ${it}ms\n") }
}

fun exercise_15_1(): Int {
    val resolver = Day15Resolver(day15demo)
    return resolver.steps() * resolver.totalLife()
}

fun exercise_15_2(): Int {
    return 0

}
