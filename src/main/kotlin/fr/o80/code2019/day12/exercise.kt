package fr.o80.code2019.day12

import kotlin.system.measureTimeMillis

fun main() {
    val time = measureTimeMillis {
        val day = Day12()
        val partOne = day.computeFullEnergy(day12Input, 1000)
        println("PartOne: $partOne")
    }

    println("${time}ms")
}

class Day12 {
    private val gravity = ApplyGravity()
    private val velocity = ApplyVelocity()

    fun computeFullEnergy(moons: List<Moon>, steps: Int): Int {
        for (i in 1..steps) {
            for (moon in moons) {
                gravity(moon, moons)
            }
            for (moon in moons) {
                velocity(moon)
            }
            //println(moons)
            //println("----------------------------------")
        }
        return moons.sumBy { it.fullEnergy() }
    }

}
