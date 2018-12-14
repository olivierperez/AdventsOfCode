package fr.o80.day12

import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis { println("12.1 => ${exercise_12_1(20)}") }.also { println("Time: ${it}ms") }
    measureTimeMillis { println("12.2 => ${exercise_12_1(50_000_000_000)}") }.also { println("Time: ${it}ms") }
}

fun exercise_12_1(generation: Long): Long {
    lateinit var initial: String
    val generators: Set<String> = day12input
            .split('\n')
            .mapIndexed { index, line ->
                when (index) {
                    0 -> {
                        initial = Regex("initial state: (.+)").find(line)!!.groupValues[1]
                        null
                    }
                    1 -> null
                    else -> {
                        val find = Regex("(.+) => (.)").find(line)!!
                        if (find.groupValues[2][0] == '#') find.groupValues[1] else null
                    }
                }
            }
            .filterNotNull()
            .toSet()

    val store = mutableMapOf<String, Plants>()
    var last = Plants(initial, 0)

    for (i in 1..generation) {
        if (i % 100_000_000 == 0L) {
            println("$i => ${i.toFloat() * 100 / generation}%")
        }

        if (store.containsKey(last.initial)) {
            val stored = store[last.initial]!!
            val newGeneration = Plants(stored.initial, i, stored.minLeft + 1) // Hack that works for me :-)
            store[last.initial] = newGeneration
            last = newGeneration
        } else {
            val newGeneration = last.step(generators)
            store[last.initial] = newGeneration
            last = newGeneration
        }
    }

    return last.sumAllPlantNumber()
}

class Plants(val initial: String, val step: Long, val minLeft: Long = 0) {

    private val plants = "....$initial...."

    fun sumAllPlantNumber(): Long {
        return plants
                .mapIndexed { index, c ->
                    if (c == '#') {
                        index + minLeft - 4
                    } else {
                        0
                    }
                }
                .filter { it != 0L }
                .sum()
    }

    fun step(generators: Set<String>): Plants {
        val next = StringBuilder()
        for (c in 0 until plants.length) {
            val neighborhood = plants
                    .substring(max(0, c - 2), min(plants.length, c + 3))
            if (generators.contains(neighborhood)) {
                next.append('#')
            } else {
                next.append('.')
            }
        }

        return Plants(
                next.trim('.').toString(),
                step + 1,
                minLeft + next.indexOf('#') - 4
        )
    }

    override fun toString(): String {
        return "$plants => $minLeft"
    }
}
