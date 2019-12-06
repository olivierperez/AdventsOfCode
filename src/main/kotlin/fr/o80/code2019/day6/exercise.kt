package fr.o80.code2019.day6

import kotlin.system.measureTimeMillis

fun main() {
    val time = measureTimeMillis {
        val day = Day6()
        val partOne = day.goBilly(day.parseInput(day6Input))
        println("PartOne: $partOne")
        val partTwo = day.computePathToSanta(day.parseInput(day6Input))
        println("PartTwo: $partTwo")
    }

    println("${time}ms")
}

class Day6 {

    fun parseInput(input: String): List<Instruction> =
        input.split("\n")
            .map {
                val split = it.split(")")
                Instruction(split[0], split[1])
            }

    fun goBilly(input: List<Instruction>): Int =
        input.toPlanetsMap()
            .countAllOrbits("COM", 0)

    fun computePathToSanta(input: List<Instruction>): Int =
        input.toPlanetsMap()
            .countJumpBetween("YOU", "SAN")

}

class Instruction(val planet: String, val orbit: String)

class Planet(val name: String) {

    var attractedBy: String? = null

    val orbits = mutableListOf<String>()

    fun addOrbit(orbit: String) {
        orbits.add(orbit)
    }

}
