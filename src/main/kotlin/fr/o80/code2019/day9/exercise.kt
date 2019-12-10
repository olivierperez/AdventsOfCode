package fr.o80.code2019.day9

import java.math.BigInteger
import kotlin.system.measureTimeMillis

fun main() {
    val time = measureTimeMillis {
        val day = Intcode()
        //val partOne = day.compute(day.parseInput("109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99"))
        //val partOne = day.compute(day.parseInput("1102,34915192,34915192,7,4,7,99,0"))
        //val partOne = day.compute(day.parseInput("104,1125899906842624,99"))
        val partOne = day.compute(day.parseInput(day9Input))
        println("partOne: $partOne")
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
    private val relativeBase: Operation = AdjustRelativeBase()

    fun parseInput(s: String): MutableList<BigInteger> =
        s.split(",")
            .map(String::toBigInteger)
            .toMutableList()

    fun compute(input: MutableList<BigInteger>): BigInteger {
        applyUpdates(Values(input))
        return input[0]
    }

    private fun applyUpdates(values: Values) {
        var index = 0
        while (values[index] != BigInteger.valueOf(99)) {
            val mods = values[index].toInt() / 100
            val op = values[index].toInt() - mods * 100
            //println("op: $op($mods)")
            index += when (op) {
                1 -> add.update(values, mods, index)
                2 -> mul.update(values, mods, index)
                3 -> read.update(values, mods, index)
                4 -> output.update(values, mods, index)
                5 -> jumpIfTrue.update(values, mods, index)
                6 -> jumpIfFalse.update(values, mods, index)
                7 -> lessThan.update(values, mods, index)
                8 -> equalTo.update(values, mods, index)
                9 -> relativeBase.update(values, mods, index)
                else -> throw IllegalArgumentException("Tu te crois où !? Je sais pas gérer de \"$op\"!")
            }
        }
    }

}
