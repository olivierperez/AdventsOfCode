package fr.o80.day9

import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis { println("9.1 => ${exercise_9_1(447, 71510)}") }.also { println("Time: ${it}ms") }
    measureTimeMillis { println("9.2 => ${exercise_9_2(447, 100 * 71510)}") }.also { println("Time: ${it}ms") }
}

fun exercise_9_1(players: Int, maxValue: Int): Int {
    val circle = ArrayList<Int>().apply {
        add(0)
        add(1)
    }
    var lastPosition = 1
    val scores = mutableMapOf<Int, Int>().apply {
        (0 until players).forEach {
            put(it, 0)
        }
    }

    for (x in 2..maxValue) {
        var newPosition = lastPosition + 2
        newPosition = if (newPosition <= circle.size) newPosition else newPosition % circle.size

        lastPosition = if (x % 23 == 0) {
            var removedAt = newPosition - 9
            if (removedAt < 0) {
                removedAt += circle.size
            }

            val removed = circle.removeAt(removedAt)
            //println("removed: $removed")

            val player = x % players
            //println("player = $player")
            val score = scores[player]!!
            scores[player] = score + x + removed

            removedAt
        } else {
            circle.add(newPosition, x)
            newPosition
        }
        //println(circle)
    }

    return scores.values.max()!!
}

fun exercise_9_2(players: Int, maxValue: Int): Int {
    val circle = ArrayList<Int>().apply {
        add(0)
        add(1)
    }
    var lastPosition = 1
    val scores = mutableMapOf<Int, Int>().apply {
        (0 until players).forEach {
            put(it, 0)
        }
    }

    for (x in 2..maxValue) {
        if (x % 500_000 == 0) {
            println(x)
        }
        var newPosition = lastPosition + 2
        newPosition = if (newPosition <= circle.size) newPosition else newPosition % circle.size

        lastPosition = if (x % 23 == 0) {
            var removedAt = newPosition - 9
            if (removedAt < 0) {
                removedAt += circle.size
            }

            val removed = circle.removeAt(removedAt)
            //println("removed: $removed")

            val player = x % players
            //println("player = $player")
            val score = scores[player]!!
            scores[player] = score + x + removed

            removedAt
        } else {
            circle.add(newPosition, x)
            newPosition
        }
        //println(circle)
    }

    return scores.values.max()!!
}
