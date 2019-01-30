package fr.o80.code2018.day1

import kotlin.system.measureTimeMillis

/**
 * @author Olivier Perez
 */
fun main() {
    measureTimeMillis { println("1.1 => ${exercise_1_1()}") }.also { println("Time: ${it}ms") }
    measureTimeMillis { println("1.2 => ${exercise_1_2()}") }.also { println("Time: ${it}ms") }
}

fun exercise_1_2() : Long {

    val remember = mutableListOf<Long>()
    val input = day1input.split('\n').map(String::toLong)
    val size = input.size
    var i = 0
    var frequency = 0L

    while (true) {
        frequency += input[i % size]

        if (frequency in remember) {
            return frequency
        } else {
            remember.add(frequency)
        }
        i++
    }
}

fun exercise_1_1() : Long {
    val sum = day1input.split('\n')
            .fold(0L) { acc, line ->
                val int = line.toLong()
                acc + int
            }
    return sum
}