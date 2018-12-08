package fr.o80.day8

import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis { println("8.1 => ${exercise_8_1()}") }.also { println("Time: ${it}ms") }
    measureTimeMillis { println("8.2 => ${exercise_8_2()}") }.also { println("Time: ${it}ms") }
}

fun exercise_8_1(): Int {
    val values = day8input.split(' ').map(String::toInt)
    val (root, _) = readNode(values)
    return root.metaSum()
}

fun exercise_8_2(): Int {
    val values = day8input.split(' ').map(String::toInt)
    val (root, _) = readNode(values)
    return root.valueSum()
}

fun readNode(values: List<Int>): Pair<Node, Int> {
    val nbValues = values[0]
    val nbMeta = values[1]

    if (nbValues == 0) {
        val meta = values.subList(2, 2 + nbMeta)
        val children = listOf<Node>()
        return Pair(Node(nbValues, nbMeta, children, meta, values), 2 + nbMeta)
    } else {
        var childrenSize = 0
        val children = mutableListOf<Node>()

        for (i in 0 until nbValues) {
            val (child, size) = readNode(values.subList(2 + childrenSize, values.size))
            childrenSize += size
            children += child
        }

        val meta = values.subList(2 + childrenSize, 2 + childrenSize + nbMeta)
        return Pair(Node(nbValues, nbMeta, children, meta, values), 2 + childrenSize + nbMeta)
    }
}

data class Node(val nbValues: Int, val nbMeta: Int, val children: List<Node>, val meta: List<Int>, val input: List<Int>) {
    fun metaSum(): Int = meta.sum() + children.fold(0) { acc, child -> acc + child.metaSum() }
    fun valueSum(): Int {
        return if (children.isNullOrEmpty()) {
            meta.sum()
        } else {
            meta.fold(0) { acc, i ->
                if (children.size < i) {
                    acc + 0
                } else {
                    acc + children[i - 1].valueSum()
                }
            }
        }
    }

}
