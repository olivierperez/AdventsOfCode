package fr.o80.day10

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis { println("10.1 & 10.2 => ${exercise_10_1and2()}") }.also { println("Time: ${it}ms") }
}

fun exercise_10_1and2(): Int {
    val points = day10input.split('\n')
            .map { Regex("position=< *(-?\\d+), *(-?\\d+)> velocity=< *(-?\\d+), *(-?\\d+)>").find(it)!!.groupValues }
            .map {
                val position = Point(it[1].toInt(), it[2].toInt())
                val velocity = Velocity(it[3].toInt(), it[4].toInt())
                Pair(position, velocity)
            }
            .toMap()

    var lastSize = Long.MAX_VALUE
    var lastMap = points

    for (i in 1..100000) {
        val newMap = lastMap.mapKeys { (point, velocity) -> point.move(velocity) }
        val outline = newMap.keys.fold(Rectangle()) { r, (x, y) ->
            Rectangle(
                    left = Integer.min(r.left, x),
                    top = Integer.min(r.top, y),
                    right = Integer.max(r.right, x),
                    bottom = Integer.max(r.bottom, y)
            )
        }
        val newSize = outline.size()

        if (newSize > lastSize) {
            draw(outline, lastMap, "result-${i - 1}.png")
            return i -1
        }

        lastSize = newSize
        lastMap = newMap
    }

    return 0
}

fun draw(outline: Rectangle, points: Map<Point, Velocity>, output: String) {

    val width = (outline.right - outline.left)
    val height = (outline.bottom - outline.top)
    val image = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)

    val black = 0.shl(24).or(0.shl(16)).or(0.shl(8)).or(0)
    val white = 255.shl(24).or(255.shl(16)).or(255.shl(8)).or(255)

    var blacks = 0
    var whites = 0

    for (y in 0 until height) {
        for (x in 0 until width) {
            val xInMap = x + outline.left
            val yInMap = y + outline.top

            if (points[Point(xInMap, yInMap)] != null) {
                image.setRGB(x, y, black)
                blacks++
            } else {
                image.setRGB(x, y, white)
                whites++
            }
        }
    }

    ImageIO.write(image, "png", File(output))
}

data class Point(var x: Int, var y: Int) {
    fun move(velocity: Velocity) = Point(
            x + velocity.x,
            y + velocity.y
    )
}

data class Velocity(val x: Int, val y: Int)

data class Rectangle(
        val left: Int = Int.MAX_VALUE,
        val top: Int = Int.MAX_VALUE,
        val right: Int = Int.MIN_VALUE,
        val bottom: Int = Int.MIN_VALUE
) {
    fun contains(x: Int, y: Int): Boolean =
            x in left..right && y in top..bottom

    fun size(): Long = (right - left).toLong() * (bottom - top)
}