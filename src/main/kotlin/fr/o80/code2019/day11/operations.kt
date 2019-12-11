package fr.o80.code2019.day11

import java.math.BigInteger

const val MOD_POSITION = 0
const val MOD_DIRECT = 1
const val MOD_RELATIVE = 2

var relativeBase: Int = 0

interface Operation {
    fun update(values: Values, mods: Int, index: Int): Int

    companion object {
        fun moddedValue(mods: Int, values: Values, index: Int, n: Int): BigInteger {
            return when (val mod = getMod(n, mods)) {
                MOD_POSITION -> values[values[index + n].toInt()]
                MOD_DIRECT -> values[index + n]
                MOD_RELATIVE -> values[values[index + n].toInt() + relativeBase]
                else -> throw IllegalArgumentException("Mod not handled: $mod")
            }
        }

        fun moddedPosition(mods: Int, values: Values, index: Int, n: Int): Int {
            return when (val mod = getMod(n, mods)) {
                MOD_POSITION -> values[index + n].toInt()
                MOD_RELATIVE -> values[index + n].toInt() + relativeBase
                else -> throw IllegalArgumentException("Mod not handled: $mod")
            }
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
    override fun update(values: Values, mods: Int, index: Int): Int {
        val value1 = Operation.moddedValue(mods, values, index, 1)
        val value2 = Operation.moddedValue(mods, values, index, 2)
        val outputPosition = Operation.moddedPosition(mods, values, index, 3)

        values[outputPosition] = value1 + value2

        return 4
    }
}

// 2
class MulOperation : Operation {
    override fun update(values: Values, mods: Int, index: Int): Int {
        val value1 = Operation.moddedValue(mods, values, index, 1)
        val value2 = Operation.moddedValue(mods, values, index, 2)
        val outputPosition = Operation.moddedPosition(mods, values, index, 3)

        values[outputPosition] = value1 * value2

        return 4
    }
}

// 3
class ReadInputOperation(private val intProvider: () -> Int) : Operation {
    override fun update(values: Values, mods: Int, index: Int): Int {
        val input = intProvider().toBigInteger()

        val outputPosition = Operation.moddedPosition(mods, values, index, 1)
        values[outputPosition] = input

        return 2
    }
}

//4
class OutputOperation(private val output: (BigInteger) -> Unit) : Operation {
    override fun update(values: Values, mods: Int, index: Int): Int {
        val value = Operation.moddedValue(mods, values, index, 1)
        output(value)

        return 2
    }
}

// 5
class JumpIfTrue : Operation {
    override fun update(values: Values, mods: Int, index: Int): Int {
        val condition = Operation.moddedValue(mods, values, index, 1)
        val destination = Operation.moddedValue(mods, values, index, 2).toInt()

        return if (condition != BigInteger.ZERO) destination - index else 3
    }
}

// 6
class JumpIfFalse : Operation {
    override fun update(values: Values, mods: Int, index: Int): Int {
        val condition = Operation.moddedValue(mods, values, index, 1)
        val destination = Operation.moddedValue(mods, values, index, 2).toInt()

        return if (condition == BigInteger.ZERO) destination - index else 3
    }
}

// 7
class LessThan : Operation {
    override fun update(values: Values, mods: Int, index: Int): Int {
        val value1 = Operation.moddedValue(mods, values, index, 1)
        val value2 = Operation.moddedValue(mods, values, index, 2)
        val outputPosition = Operation.moddedPosition(mods, values, index, 3)

        values[outputPosition] = if (value1 < value2) BigInteger.ONE else BigInteger.ZERO

        return 4
    }
}

// 8
class EqualTo : Operation {
    override fun update(values: Values, mods: Int, index: Int): Int {
        val value1 = Operation.moddedValue(mods, values, index, 1)
        val value2 = Operation.moddedValue(mods, values, index, 2)
        val outputPosition = Operation.moddedPosition(mods, values, index, 3)

        values[outputPosition] = if (value1 == value2) BigInteger.ONE else BigInteger.ZERO

        return 4
    }
}

// 9
class AdjustRelativeBase : Operation {
    override fun update(values: Values, mods: Int, index: Int): Int {
        val value1 = Operation.moddedValue(mods, values, index, 1)
        relativeBase += value1.toInt()
        return 2
    }

}
