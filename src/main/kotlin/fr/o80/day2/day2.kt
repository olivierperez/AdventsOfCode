package fr.o80.day2

import kotlin.system.measureTimeMillis

/**
 * @author Olivier Perez
 */
fun main() {
    measureTimeMillis { println("2.1 => ${exercise_2_1()}") }.also { println("Time: ${it}ms") }
    measureTimeMillis { println("2.2 => ${exercise_2_2()}") }.also { println("Time: ${it}ms") }
}

fun exercise_2_2(): String {
    val list = day2input.split("\n")

    list.forEach {first ->
        list.forEach { second ->
            val compared: Pair<String, String> = first comparedTo second
            val (diff, same) = compared
            if (diff.length == 1) {
                return same
            }
        }
    }

    return "Not found!"
}

private infix fun String.comparedTo(second: String): Pair<String, String> {
    val diff = StringBuilder()
    val same = StringBuilder()

    this.forEachIndexed { index, c ->
        if (c == second[index]) {
            same.append(c)
        } else {
            diff.append(c)
        }
    }

    return Pair(diff.toString(), same.toString())
}

fun exercise_2_1(): Long {
    var twices = 0L
    var triples = 0L

    day2input.split("\n")
            .forEach {
                val count: Pair<Boolean, Boolean> = it.count2and3()
                if (count.first) twices++
                if (count.second) triples++
            }

    return twices * triples
}

private fun String.count2and3(): Pair<Boolean, Boolean> {
    var twice = false
    var triple = false

    this.forEach {
        val count = this.count { c -> c == it }
        if (count == 2) twice = true
        if (count == 3) triple = true
    }
    return Pair(twice, triple)
}
