package fr.o80.code2019.day5

import kotlin.system.measureTimeMillis

fun main() {
    val time = measureTimeMillis {
        val day = Intcode()
        val partOne = day.compute(day.parseInput(day5Input))
        println("PartOne: $partOne")
    }

    println("${time}ms")
}

class Intcode {

    private val add: Operation = AddOperation()
    private val mul: Operation = MulOperation()
    private val read: Operation = ReadInputOperation()
    private val output: Operation = OutputOperation()
    private val jumpIfTrue: Operation = JumpIfTrue()
    private val jumpIfFalse: Operation = JumpIfFalse()
    private val lessThan: Operation = LessThan()
    private val equalTo: Operation = EqualTo()

    fun parseInput(s: String): MutableList<Int> =
        s.split(",")
            .map(String::toInt)
            .toMutableList()

    fun compute(input: MutableList<Int>): Int {
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
