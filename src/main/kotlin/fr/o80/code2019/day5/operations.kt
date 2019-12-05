package fr.o80.code2019.day5

import fr.o80.code2019.day5.Operation.Companion.moddedValue
import java.util.Scanner

const val MOD_POSITION = 0

interface Operation {
    fun update(values: MutableList<Int>, mods: Int, index: Int): Int

    companion object {
        fun moddedValue(mods: Int, values: List<Int>, index: Int, n: Int): Int {
            return if (getMod(n, mods) == MOD_POSITION) values[values[index + n]] else values[index + n]
        }

        fun getMod(modNumber: Int, mods: Int): Int {
            val modsString = mods.toString()
            return when {
                modsString.length < modNumber -> 0
                else -> modsString.substring(modsString.length - modNumber)[0].toInt() - 48
            }
        }
    }
}

// 1
class AddOperation : Operation {
    override fun update(values: MutableList<Int>, mods: Int, index: Int): Int {
        val value1 = moddedValue(mods, values, index, 1)
        val value2 = moddedValue(mods, values, index, 2)
        val outputPosition = values[index + 3]

        values[outputPosition] = value1 + value2

        return 4
    }
}

// 2
class MulOperation : Operation {
    override fun update(values: MutableList<Int>, mods: Int, index: Int): Int {
        val value1 = moddedValue(mods, values, index, 1)
        val value2 = moddedValue(mods, values, index, 2)
        val outputPosition = values[index + 3]

        values[outputPosition] = value1 * value2

        return 4
    }
}

// 3
class ReadInputOperation : Operation {
    override fun update(values: MutableList<Int>, mods: Int, index: Int): Int {
        print("input: ")
        val input = Scanner(System.`in`).use { it.nextInt() }

        val outputPosition = values[index + 1]
        values[outputPosition] = input

        return 2
    }
}

//4
class OutputOperation : Operation {
    override fun update(values: MutableList<Int>, mods: Int, index: Int): Int {
        val value = moddedValue(mods, values, index, 1)
        println("output: $value")

        return 2
    }
}

// 5
class JumpIfTrue : Operation {
    override fun update(values: MutableList<Int>, mods: Int, index: Int): Int {
        val condition = moddedValue(mods, values, index, 1)
        val destination = moddedValue(mods, values, index, 2)

        return if (condition != 0) destination - index else 3
    }
}

// 6
class JumpIfFalse : Operation {
    override fun update(values: MutableList<Int>, mods: Int, index: Int): Int {
        val condition = moddedValue(mods, values, index, 1)
        val destination = moddedValue(mods, values, index, 2)

        return if (condition == 0) destination - index else 3
    }
}

// 7
class LessThan : Operation {
    override fun update(values: MutableList<Int>, mods: Int, index: Int): Int {
        val value1 = moddedValue(mods, values, index, 1)
        val value2 = moddedValue(mods, values, index, 2)
        val outputPosition = values[index + 3]

        values[outputPosition] = if (value1 < value2) 1 else 0

        return 4
    }
}

// 8
class EqualTo : Operation {
    override fun update(values: MutableList<Int>, mods: Int, index: Int): Int {
        val value1 = moddedValue(mods, values, index, 1)
        val value2 = moddedValue(mods, values, index, 2)
        val outputPosition = values[index + 3]

        values[outputPosition] = if (value1 == value2) 1 else 0

        return 4
    }
}
