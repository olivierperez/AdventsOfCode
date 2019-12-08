package fr.o80.code2019.day7

import kotlin.system.measureTimeMillis

const val CONTINUE = false
const val STOP = true

fun main() {
    val time = measureTimeMillis {
        val day = Intcode()
        //val partOne = day.partOne(day7Input)
        //println("partOne: $partOne")

        // Exemple 1
        //val partTwo = day.partTwo("3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5")
        // Exemple 2
        //val partTwo = day.partTwo("3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10")
        val partTwo = day.partTwo(day7Input)

        println("PartTwo: $partTwo")
    }

    println("${time}ms")
}

class Intcode {

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
        return generateAllPhases(5)
            .map { phases ->
                amplify(
                    input = parseInput(input),
                    phases = phases
                )
            }
            .max()!!
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
        var thrust: Output = Thrust(0)
        val amplifierA = Amplifier(phases[0], input)
        val amplifierB = Amplifier(phases[1], input)
        val amplifierC = Amplifier(phases[2], input)
        val amplifierD = Amplifier(phases[3], input)
        val amplifierE = Amplifier(phases[4], input)

        do {
            // A
            thrust = amplifierA.amplify(thrust.thrust)

            // B
            thrust = amplifierB.amplify(thrust.thrust)

            // C
            thrust = amplifierC.amplify(thrust.thrust)

            // D
            thrust = amplifierD.amplify(thrust.thrust)

            // E
            thrust = amplifierE.amplify(thrust.thrust)
        } while (thrust is Thrust)

        return (thrust as Final).thrust
    }

}

sealed class Output(val thrust: Int) {
    override fun toString(): String {
        return "${this::class.simpleName}(thrust=$thrust)"
    }
}

class Thrust(thrust: Int) : Output(thrust)
class Final(thrust: Int) : Output(thrust)
