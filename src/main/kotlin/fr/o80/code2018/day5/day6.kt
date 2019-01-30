package fr.o80.code2018.day5

import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis { println("5.1 => ${exercise_5_1()}") }.also { println("Time: ${it}ms") }
    measureTimeMillis { println("5.2 => ${exercise_5_2()}") }.also { println("Time: ${it}ms") }
}

fun exercise_5_1(): Int {
    return react(day5input).length
}

fun exercise_5_2(): Int {
    return day5input.toLowerCase()
            .asSequence()
            .distinct()
            .map { day5input.replace(it.toString(), "", ignoreCase = true) }
            .map { react(it) }
            .minBy { it.length }
            ?.length
            ?: -1
}

fun react(polymer: String): String {
    val result = StringBuilder()
    var i = 0
    while (i < polymer.length - 1) {
        val diff = polymer[i] - polymer[i + 1]

        if (diff != 32 && diff != -32) {
            result.append(polymer[i])
            i += 1
        } else {
            i += 2
        }
    }

    if (i == polymer.length - 1) {
        result.append(polymer[i])
    }

    return if (result.length == polymer.length) {
        polymer
    } else {
        react(result.toString())
    }
}
