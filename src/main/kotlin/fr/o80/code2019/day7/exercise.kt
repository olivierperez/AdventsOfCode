package fr.o80.code2019.day7

import java.util.Scanner
import kotlin.system.measureTimeMillis

fun main() {
    val time = measureTimeMillis {
        val day = Intcode()
        val partOne = day.partOne(day7Input)
        println("partOne: $partOne")
        val partTwo = day.partTwo("3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5")
        println("PartTwo: $partTwo")
    }

    println("${time}ms")
}

class Intcode {

    private val add: Operation = AddOperation()
    private val mul: Operation = MulOperation()
    private val read: ReadInputOperation = ReadInputOperation()
    private val output: OutputOperation = OutputOperation()
    private val jumpIfTrue: Operation = JumpIfTrue()
    private val jumpIfFalse: Operation = JumpIfFalse()
    private val lessThan: Operation = LessThan()
    private val equalTo: Operation = EqualTo()

    fun parseInput(s: String): MutableList<Int> =
        s.split(",")
            .map(String::toInt)
            .toMutableList()

    fun partOne(input: String): Int {
        return generateAllPhases()
            .map { phases ->
                amplify(
                    input = parseInput(input),
                    phases = phases
                )
            }
            .max()!!
    }

    fun partTwo(input: String): Int {
        return -1
    }

    private fun generateAllPhases(bump: Int = 0): List<List<Int>> {
        val result = mutableListOf<List<Int>>()

        val indexes = intArrayOf(0, 0, 0, 0, 0)
        val elements = intArrayOf(0, 1, 2, 3, 4).map { it + bump }.toIntArray()
        var i = 0
        val n = 5
        while (i < n) {
            if (indexes[i] < i) {
                swap(elements, if (i % 2 == 0) 0 else indexes[i], i)
                result += elements.copyOf().asList()
                indexes[i] = indexes[i] + 1
                i = 0
            } else {
                indexes[i] = 0
                i++
            }
        }
        return result
    }

    private fun swap(input: IntArray, a: Int, b: Int) {
        val tmp = input[a]
        input[a] = input[b]
        input[b] = tmp
    }

    private fun amplify(input: MutableList<Int>, phases: List<Int>): Int {
        var thrust = 0
        compute(
            input = input,
            intProvider = listOf(phases[0], thrust).iterator(),
            intReader = { thrust = it }
        )
        compute(
            input = input,
            intProvider = listOf(phases[1], thrust).iterator(),
            intReader = { thrust = it }
        )
        compute(
            input = input,
            intProvider = listOf(phases[2], thrust).iterator(),
            intReader = { thrust = it }
        )
        compute(
            input = input,
            intProvider = listOf(phases[3], thrust).iterator(),
            intReader = { thrust = it }
        )
        compute(
            input = input,
            intProvider = listOf(phases[4], thrust).iterator(),
            intReader = { thrust = it }
        )
        return thrust
    }

    private fun compute(input: MutableList<Int>, intProvider: Iterator<Int>, intReader: (Int) -> Unit): Int {
        read.intProvider = intProvider
        output.intReader = intReader
        applyUpdates(input)
        return input[0]
    }

    private fun applyUpdates(values: MutableList<Int>) {
        var index = 0
        while (values[index] != 99) {
            val mods = values[index] / 100
            val op = values[index] - mods * 100
            index += when (op) {
                1 -> add.update(values, mods, index)
                2 -> mul.update(values, mods, index)
                3 -> read.update(values, mods, index)
                4 -> output.update(values, mods, index)
                5 -> jumpIfTrue.update(values, mods, index)
                6 -> jumpIfFalse.update(values, mods, index)
                7 -> lessThan.update(values, mods, index)
                8 -> equalTo.update(values, mods, index)
                else -> throw IllegalArgumentException("Tu te crois où !? Je sais pas gérer de \"$op\"!")
            }
        }
    }

}

class ScannerIterator : Iterator<Int> {
    override fun hasNext(): Boolean {
        return true
    }

    override fun next(): Int {
        print("input: ")
        return Scanner(System.`in`).nextInt()
    }
}
