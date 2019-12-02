package fr.o80.code2019.day2

import kotlin.system.measureTimeMillis

fun main() {
    val time = measureTimeMillis {
        val unitTest = Day1().compute(parseInput("1,9,10,3,2,3,11,0,99,30,40,50"))
        println("Unit test: $unitTest")
        check(3500 == unitTest) { "Unit test failed" }

        val partOne = Day1().compute(parseInput(day2input), 12, 2)
        println("Part one: $partOne")
        check(4930687 == partOne) { "Part one failed" }

        val partTwo = Day1().findBestNounVerb(parseInput(day2input))
        println("Part two: $partTwo")
        check(5335 == partTwo) { "Part two failed" }
    }

    println("${time}ms")
}

fun parseInput(s: String): MutableList<Int> =
        s.split(",")
                .map(String::toInt)
                .toMutableList()

class Day1 {

    private val add: Operation = AddOperation()
    private val mul: Operation = MulOperation()

    fun findBestNounVerb(input: MutableList<Int>): Int {

        for (n in 0..99) {
            for (v in 0..99) {
                val inputCloned = ArrayList(input)
                val computed = compute(inputCloned, n, v)

                if (computed == 19690720) {
                    return 100 * n + v
                }
            }
        }

        throw IllegalStateException("You failed!")

    }

    fun compute(input: MutableList<Int>, noun: Int? = null, verb: Int? = null): Int {
        input[1] = noun ?: input[1]
        input[2] = verb ?: input[2]
        applyUpdates(input)
        return input[0]
    }

    private fun applyUpdates(values: MutableList<Int>) {
        var index = 0
        while (values[index] != 99) {
            when (values[index]) {
                1 -> {
                    add.apply(values, index)
                    index += 4
                }
                2 -> {
                    mul.apply(values, index)
                    index += 4
                }
                else -> throw IllegalArgumentException("Tu te crois où !? Je sais pas gérer de \"${values[index]}\"!")
            }
        }
    }

}

interface Operation {
    fun apply(values: MutableList<Int>, index: Int)
}

class AddOperation : Operation {
    override fun apply(values: MutableList<Int>, index: Int) {
        val v1 = values[values[index + 1]]
        val v2 = values[values[index + 2]]
        val outputPosition = values[index + 3]

        values[outputPosition] = v1 + v2
    }
}

class MulOperation : Operation {
    override fun apply(values: MutableList<Int>, index: Int) {
        val v1 = values[values[index + 1]]
        val v2 = values[values[index + 2]]
        val outputPosition = values[index + 3]

        values[outputPosition] = v1 * v2
    }
}
