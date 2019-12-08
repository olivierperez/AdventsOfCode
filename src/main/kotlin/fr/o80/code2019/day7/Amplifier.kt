package fr.o80.code2019.day7

class Amplifier(
    private val phase: Int,
    input: List<Int>
) {

    private val working = mutableListOf<Int>().apply { addAll(input) }
    private var index = 0
    private var firstRun = true
    private var lastOutput = 0

    private val add: Operation = AddOperation()
    private val mul: Operation = MulOperation()
    private val read: ReadInputOperation = ReadInputOperation()
    private val output: OutputOperation = OutputOperation()
    private val jumpIfTrue: Operation = JumpIfTrue()
    private val jumpIfFalse: Operation = JumpIfFalse()
    private val lessThan: Operation = LessThan()
    private val equalTo: Operation = EqualTo()

    fun amplify(thrust: Int): Output {
        // println("pahse: $phase")
        // println("thrust: $thrust")
        // println("index: $index")
        // println("working: $working")
        return computeTwo(phase, thrust, working)
    }

    private fun computeTwo(phase: Int, thrust: Int, input: MutableList<Int>): Output {
        var out = 0

        read.intProvider = if (firstRun) listOf(phase, thrust).iterator() else listOf(thrust).iterator()
        output.intReader = { out = it }
        firstRun = false

        return if (applyUpdates(input) == CONTINUE) {
            lastOutput = out
            Thrust(out)
        } else {
            Final(lastOutput)
        }
    }

    private fun applyUpdates(values: MutableList<Int>): Boolean {
        do {
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
                99 -> return STOP
                else -> throw IllegalArgumentException("Tu te crois où !? Je sais pas gérer de \"$op\"!")
            }
        } while (op != 99 && op != 4)
        return CONTINUE
    }
}