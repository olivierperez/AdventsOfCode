package fr.o80.code2019.day13

import fr.o80.code2019.day13.Block.BLOCK
import fr.o80.code2019.day13.ReadingMod.LEFT
import fr.o80.code2019.day13.ReadingMod.TILE
import fr.o80.code2019.day13.ReadingMod.TOP
import java.math.BigInteger

data class Point(val x: BigInteger, val y: BigInteger)

class Game(input: String) {

    private val map = mutableMapOf<Point, Block>()

    private val parsed = parseInput(input)

    private val intcode = Intcode(this::provideInt, this::handleOutput)

    private var readingMod: ReadingMod = LEFT
    private var left: BigInteger = BigInteger.ZERO
    private var top: BigInteger = BigInteger.ZERO

    fun parseInput(s: String): MutableList<BigInteger> =
        s.split(",")
            .map(String::toBigInteger)
            .toMutableList()

    private fun provideInt(): Int {
        throw IllegalArgumentException("Pourquoi t'as besoin de ça ?")
    }

    private fun handleOutput(value: BigInteger) {
        //println("Output: $value")
        when (readingMod) {
            LEFT -> {
                left = value
                readingMod = TOP
            }
            TOP -> {
                top = value
                readingMod = TILE
            }
            TILE -> {
                map[Point(left, top)] = Block.from(value.toInt())
                readingMod = LEFT
            }
        }
    }

    fun countTileBlock(): Int {
        intcode.compute(parsed)
        return map.values.count { it == BLOCK }
    }

}

enum class ReadingMod {
    LEFT,
    TOP,
    TILE
}

enum class Block {
    EMPTY,
    WALL,
    BLOCK,
    PADDLE,
    BALL;

    companion object {
        fun from(value: Int): Block =
            when (value) {
                0 -> EMPTY
                1 -> WALL
                2 -> BLOCK
                3 -> PADDLE
                4 -> BALL
                else -> throw IllegalArgumentException("Can't handle blocks ($value)")
            }
    }
}
