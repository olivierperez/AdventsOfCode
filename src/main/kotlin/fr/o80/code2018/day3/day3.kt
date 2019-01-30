package fr.o80.code2018.day3

import kotlin.system.measureTimeMillis

/**
 * @author Olivier Perez
 */
fun main() {
    measureTimeMillis { println("3.1 => ${exercise_3_1()}") }.also { println("Time: ${it}ms") }
    measureTimeMillis { println("3.2 => ${exercise_3_2()}") }.also { println("Time: ${it}ms") }
}

fun exercise_3_1(): Int {
    val counts = mutableMapOf<String, Int>()
    day3input.split('\n')
            .map(String::toClaims)
            .forEach { it.update(counts) }
    return counts.filterValues { it >= 2 }
            .size
}

fun exercise_3_2(): String {
    val counts = mutableMapOf<String, Int>()
    val claims = day3input.split('\n').map(String::toClaims)
    return claims
            .onEach { it.update(counts) }
            .first { it doesNotOverlap counts }
            .id
}

private fun String.toClaims(): Claims {
    val founds = Regex("#([0-9]+) @ ([0-9]+),([0-9]+): ([0-9]+)x([0-9]+)")
            .find(this)!!
            .groupValues
    return Claims(founds[1], founds[2].toInt(), founds[3].toInt(), founds[4].toInt(), founds[5].toInt())
}

class Claims(val id: String, val left: Int, val top: Int, val width: Int, val height: Int) {
    fun update(counts: MutableMap<String, Int>) {
        for (x in left until width + left) {
            for (y in top until height + top) {
                counts["$x;$y"] = (counts["$x;$y"] ?: 0) + 1
            }
        }
    }

    infix fun doesNotOverlap(counts: MutableMap<String, Int>): Boolean {
        for (x in left until width + left) {
            for (y in top until height + top) {
                if (counts["$x;$y"] != 1) {
                    return false
                }
            }
        }
        return true
    }
}
