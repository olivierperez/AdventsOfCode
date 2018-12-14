package fr.o80.day14

import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis { println("14.1 (a) => ${exercise_14_1("37", 9, 10)}") }.also { println("Time: ${it}ms\n") }
    measureTimeMillis { println("14.1 (b) => ${exercise_14_1("37", 5, 10)}") }.also { println("Time: ${it}ms\n") }
    measureTimeMillis { println("14.1 (c) => ${exercise_14_1("37", 18, 10)}") }.also { println("Time: ${it}ms\n") }
    measureTimeMillis { println("14.1 (d) => ${exercise_14_1("37", 2018, 10)}") }.also { println("Time: ${it}ms\n") }
    measureTimeMillis { println("14.1 => ${exercise_14_1("37", 556061, 10)}") }.also { println("Time: ${it}ms\n") }
    measureTimeMillis { println("14.2 => ${exercise_14_2("37", 556061, 10)}") }.also { println("Time: ${it}ms\n") }
}

fun exercise_14_1(input: String, after: Int, next: Int): String {
    val scores = input.map { (it - 48).toInt() }.toMutableList()
    val initialSize = input.length
    var recipesToCombine = Pair(0, 1)
    var i = initialSize
    val result = StringBuilder(next)

    while (i < after + next) {

        recipesToCombine
                .makeNewRecipes(scores)
                .forEachIndexed { index, recipe ->
                    scores.add(recipe)
                    i++
                    if (i > after && i <= after + next) {
                        result.append(recipe.toString(10))
                    }
                }

        val newSize = scores.size

        val nextFirst = (recipesToCombine.first + 1 + scores[recipesToCombine.first]) % newSize
        val nextSecond = (recipesToCombine.second + 1 + scores[recipesToCombine.second]) % newSize

        recipesToCombine = Pair(nextFirst, nextSecond)
    }

    return result.toString()
}

private fun Pair<Int, Int>.makeNewRecipes(scores: List<Int>): IntArray =
        (scores[first] + scores[second]).let { score ->
            if (score < 10) {
                intArrayOf(score)
            } else {
                val one = score / 10
                val two = score - (one * 10)
                intArrayOf(one, two)
            }
        }

fun exercise_14_2(input: String, after: Int, next: Int): String {
    return ""
}