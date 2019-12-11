package fr.o80.code2019.day10

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2

class Laser(
    private val station: Asteroid,
    private val asteroids: Asteroids
) {
    fun destroyUnit(nth: Int): Asteroid {
        val asteroidsGroupedByAngle = asteroids.groupedByAngle()
        val angles = asteroidsGroupedByAngle.keys.sorted()

        var i = 1
        var angleId = 0
        while (i < nth) {
            val group = asteroidsGroupedByAngle.getValue(angles[angleId])

            if (group.isNotEmpty()) {
                group.removeAt(0)
                i++
            }

            angleId = (angleId + 1) % angles.size
        }

        return asteroidsGroupedByAngle.getValue(angles[angleId])[0]
    }

    private fun Asteroids.groupedByAngle(): Map<Angle, MutableList<Asteroid>> {
        computeAnglesAndDistance()

        return all
            .groupBy { it.angle }
            .mapValues { (_, group) ->
                group
                    .sortedBy { it.distance }
                    .toMutableList()
            }
    }

    private fun Asteroids.computeAnglesAndDistance() {
        all.forEach {
            it.angle = angleWith(it)
            it.distance = abs(it.x - station.x) + abs(it.y - station.y)
        }
    }

    fun angleWith(other: Asteroid): Double {
        val atan2 = atan2((station.y - other.y).toDouble(), (station.x - other.x).toDouble())
        return (atan2 - PI / 2 + 2 * PI) % (2 * PI)
    }
}