package fr.o80.code2019.day11

import java.math.BigInteger

class Intcode(intProvider: () -> Int, output: (BigInteger) -> Unit) {

    private val add: Operation = AddOperation()
    private val mul: Operation = MulOperation()
    private val read: Operation = ReadInputOperation(intProvider)
    private val output: Operation = OutputOperation(output)
    private val jumpIfTrue: Operation = JumpIfTrue()
    private val jumpIfFalse: Operation = JumpIfFalse()
    private val lessThan: Operation = LessThan()
    private val equalTo: Operation = EqualTo()
    private val relativeBase: Operation = AdjustRelativeBase()

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