package fr.o80.day7

import java.util.*
import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis { println("7.1 => ${exercise_7_1()}") }.also { println("Time: ${it}ms") }
    measureTimeMillis { println("7.2 => ${exercise_7_2()}") }.also { println("Time: ${it}ms") }
}

private fun exercise_7_1(): String {
    val availableSteps = TreeSet<Char>()
    val graph: MutableMap<Char, MutableSet<Char>> = mutableMapOf()
    val result = StringBuilder()

    day7input.split('\n')
            .map {
                Regex("Step (.) must be finished before step (.) can begin.").find(it)!!.groupValues
            }
            .onEach { found ->
                val dependency = found[1][0]
                val step = found[2][0]

                availableSteps.add(step)
                availableSteps.add(dependency)

                val stepDependencies = graph.getOrPut(step) { mutableSetOf() }
                stepDependencies.add(dependency)
            }

    while (graph.isNotEmpty()) {

        // Find next step
        val nextStep = availableSteps.first { step ->
            graph[step]
                    ?.filter { availableSteps.contains(it) }
                    ?.isNullOrEmpty()
                    ?: true
        }

        // Clean graph
        availableSteps.remove(nextStep)
        graph.remove(nextStep)

        // Append result
        result.append(nextStep)
    }

    return result.toString()
}

private fun exercise_7_2(): Int {
    val availableSteps = TreeSet<Step>()
    val graph: MutableMap<Char, MutableSet<Char>> = mutableMapOf()
    val workers = 5
    val additionalTime = 60
    val input = day7input

    // Build dependency graph

    input.split('\n')
            .map {
                Regex("Step (.) must be finished before step (.) can begin.").find(it)!!.groupValues
            }
            .forEach { found ->
                val dependency = found[1][0]
                val step = found[2][0]

                availableSteps.add(Step(step, additionalTime))
                availableSteps.add(Step(dependency, additionalTime))

                val stepDependencies = graph.getOrPut(step) { mutableSetOf() }
                stepDependencies.add(dependency)
            }

    // Execute algorithm

    var time = 0
    while (graph.isNotEmpty()) {

        // Find next step
        val nextSteps = availableSteps
                .filter { step ->
                    availableSteps.none { availableStep ->
                        graph[step.letter]?.contains(availableStep.letter) ?: false
                    }
                }
                .sorted()
                .take(workers)

        // Clean graph
        nextSteps.forEach { step ->

            step.hasStarted = true
            step.remaining--

            if (step.remaining <= 0) {
                availableSteps.removeIf { it.letter == step.letter }
                graph.remove(step.letter)
            }

        }

        // Append result
        time++
    }

    return time
}

data class Step(val letter: Char, val additionalTime: Int, var remaining: Int = letter.toInt() - 64 + additionalTime, var hasStarted: Boolean = false) : Comparable<Step> {
    override fun compareTo(other: Step): Int {
        return when {
            hasStarted == other.hasStarted -> letter - other.letter
            hasStarted -> -1
            else -> 1
        }
    }
}
