package fr.o80.code2019.day13

import fr.o80.code2019.day13.Block.BALL
import fr.o80.code2019.day13.Block.BLOCK
import fr.o80.code2019.day13.Block.PADDLE
import fr.o80.code2019.day13.ReadingMod.LEFT
import fr.o80.code2019.day13.ReadingMod.TILE
import fr.o80.code2019.day13.ReadingMod.TOP
import java.math.BigInteger

private const val JOYSTICK_LEFT = -1
private const val JOYSTICK_RIGHT = 1
private const val JOYSTICK_CENTER = 0

data class Point(val x: BigInteger, val y: BigInteger)

class Game(input: String) {

    private lateinit var paddle: Point
    private lateinit var ball: Point
    private lateinit var score: BigInteger

    private val map = mutableMapOf<Point, Block>()

    private val parsed = parseInput(input)

    private val intcode = Intcode(this::provideInt, this::handleOutput)

    private var readingMod: ReadingMod = LEFT
    private var left: BigInteger = BigInteger.ZERO
    private var top: BigInteger = BigInteger.ZERO

    fun countTileBlock(): Int {
        intcode.compute(parsed)
        return map.values.count { it == BLOCK }
    }

    fun play(): BigInteger {
        parsed[0] = BigInteger.valueOf(2)
        intcode.compute(parsed)
        return score
    }

    fun parseInput(s: String): MutableList<BigInteger> =
        s.split(",")
            .map(String::toBigInteger)
            .toMutableList()

    private fun provideInt(): Int {
        return when {
            paddle.x > ball.x -> JOYSTICK_LEFT
            paddle.x < ball.x -> JOYSTICK_RIGHT
            else -> JOYSTICK_CENTER
        }
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
                if (left == BigInteger.valueOf(-1) && top == BigInteger.ZERO) {
                    score = value
                } else {
                    val block = Block.from(value.toInt())
                    val position = Point(left, top)
                    map[position] = block

                    if (block == BALL) {
                        ball = position
                    } else if (block == PADDLE) {
                        paddle = position
                    }
                }

                readingMod = LEFT
            }
        }
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
