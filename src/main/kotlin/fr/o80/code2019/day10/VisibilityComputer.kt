package fr.o80.code2019.day10

import kotlin.math.abs

class VisibilityComputer(private val all: List<Asteroid>, private val asteroid: Asteroid) {

    fun count(): Int {
        val visibleAsteroids = all.map { it.copy() }.toMutableList()
        //println(all)
        //println(visibleAsteroids)
        //println("----")
        visibleAsteroids.remove(asteroid)

        visibleAsteroids.forEach {
            if (it.visible) {
                visibleAsteroids
                    .filter { other -> other != it && other.isHiddenBy(it, asteroid) }
                    .forEach { hidden -> hidden.visible = false }
            }
        }

        //println(visibleAsteroids)
        //println(visibleAsteroids.filter { !it.visible })

        return visibleAsteroids.count { it.visible }
    }

}

fun Asteroid.isHiddenBy(visibleAsteroid: Asteroid, originAsteroid: Asteroid): Boolean {
    val xDistance = this.x - originAsteroid.x
    val yDistance = this.y - originAsteroid.y
    val xDistanceToVisible = visibleAsteroid.x - originAsteroid.x
    val yDistanceToVisible = visibleAsteroid.y - originAsteroid.y

    return when {
        visibleAsteroid.x == originAsteroid.x && this.x == originAsteroid.x ->
            yDistanceToVisible in 0..yDistance || yDistanceToVisible in 0 downTo yDistance

        visibleAsteroid.y == originAsteroid.y && this.y == originAsteroid.y ->
            xDistanceToVisible in 0..xDistance || xDistanceToVisible in 0 downTo xDistance

        else ->
            xDistance.toFloat() / xDistanceToVisible == yDistance.toFloat() / yDistanceToVisible &&
                abs(xDistanceToVisible) < abs(xDistance) &&
                (xDistance / abs(xDistance) == xDistanceToVisible / abs(xDistanceToVisible))
    }

}
