package fr.o80.day14

import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis { println("14.1 (a) => ${exercise_14_1(9, 10)}") }.also { println("Time: ${it}ms\n") }
    measureTimeMillis { println("14.1 (b) => ${exercise_14_1(5, 10)}") }.also { println("Time: ${it}ms\n") }
    measureTimeMillis { println("14.1 (c) => ${exercise_14_1(18, 10)}") }.also { println("Time: ${it}ms\n") }
    measureTimeMillis { println("14.1 (d) => ${exercise_14_1(2018, 10)}") }.also { println("Time: ${it}ms\n") }
    measureTimeMillis { println("14.1 => ${exercise_14_1(556061, 10)}") }.also { println("Time: ${it}ms\n") }

    measureTimeMillis { println("14.2 => ${exercise_14_2("556061")}") }.also { println("Time: ${it}ms\n") }
}

fun exercise_14_1(after: Int, next: Int): String {
    val scores = mutableListOf(3, 7)
    val initialSize = scores.size
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

fun exercise_14_2(search: String): Int {
    val scores = mutableListOf(3, 7)
    var recipesToCombine = Pair(0, 1)
    var i = scores.size
    val blockSize = 1000000

    while (true) {

        recipesToCombine
                .makeNewRecipes(scores)
                .forEach { recipe ->
                    scores.add(recipe)
                    i++
                    if (i % blockSize == 0) {
                        val size = scores.size
                        val lastRecipes = scores.subList(size - blockSize, size).toRealString()
                        val indexOf = lastRecipes.indexOf(search)
                        if (indexOf >= 0) {
                            return blockSize * ((i / blockSize) - 1) + indexOf
                        }
                    }
                }

        val newSize = scores.size

        val nextFirst = (recipesToCombine.first + 1 + scores[recipesToCombine.first]) % newSize
        val nextSecond = (recipesToCombine.second + 1 + scores[recipesToCombine.second]) % newSize

        recipesToCombine = Pair(nextFirst, nextSecond)
    }

}

private fun <E> List<E>.toRealString(): String =
        fold(StringBuilder(size)) { sb, elem ->
            sb.append(elem)
        }.toString()
