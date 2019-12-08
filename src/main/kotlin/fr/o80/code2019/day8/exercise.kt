package fr.o80.code2019.day8

import kotlin.system.measureTimeMillis

fun main() {
    val time = measureTimeMillis {
        val day = Day8(25, 6)
        val partOne = day.partOne(day.parseInput(day8Input))
        println("PartOne: $partOne")
        val partTwo = day.partTwo(day.parseInput(day8Input))
        println("PartTwo: $partTwo")
    }

    println("${time}ms")
}

class Day8(val columns: Int, val rows: Int) {

    fun parseInput(input: String): List<Layer> =
        input.chunked(columns * rows)
            .map { Layer(columns, rows, it) }
            .toList()

    fun partOne(layers: List<Layer>): Int {
        return layers
            .minBy { it.count('0') }!!
            .checksum()
    }

    fun partTwo(layers: List<Layer>): Int {
        val image = Image(columns, rows, layers)

        //println("-".repeat(columns))
        //layers.forEach { println(it) }
        //println("-".repeat(columns))

        image.compute()
        println(image)
        return -1
    }

}

class Layer(val columns: Int, val rows: Int, var input: String) {
    fun count(c: Char): Int {
        return input.count { it == c }
    }

    fun checksum(): Int {
        return count('1') * count('2')
    }

    operator fun get(x: Int, y: Int): Char {
        return input[x + y * columns]
    }

    operator fun get(i: Int): Char {
        return input[i]
    }

    fun write(char: Char) {
        input += char
    }

    override fun toString(): String {
        val sb = StringBuilder()
        for (y in 0 until rows) {
            for (x in 0 until columns) {
                val char = when(this[x, y]) {
                    '0' -> ' '
                    '1' -> 'X'
                    else -> ' '
                }
                sb.append(char)
            }
            sb.append('\n')
        }
        return sb.substring(0, sb.length-1).toString()
    }
}

class Image(val columns: Int, val rows: Int, val layers: List<Layer>) {

    var output: Layer? = null

    fun compute() {
        output = Layer(columns, rows, "")
        for (i in 0 until columns * rows) {
            ff@ for (layer in layers) {
                when (val char = layer[i]) {
                    '0', '1' -> {
                        output?.write(char)
                        break@ff
                    }
                    '2' -> {
                        // no-op
                    }
                    else -> throw IllegalArgumentException("Je ne sais pas dessiner des \"$char\"")
                }
            }
        }
    }

    override fun toString(): String {
        return output?.toString()?: "Image non dessinée !"
    }

}
