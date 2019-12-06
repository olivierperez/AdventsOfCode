package fr.o80.code2019.day6

import kotlin.system.measureTimeMillis

fun main() {
    val time = measureTimeMillis {
        val day = Day6()
        val partOne = day.goBilly(day.parseInput(day6Input))
        println("PartOne: $partOne")
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

    fun goBilly(input: List<Instruction>): Int {
        val planets: Map<String, Planet> = buildPlanets(input)
        return countAllOrbits(planets, "COM", 0)
    }

    private fun countAllOrbits(planets: Map<String, Planet>, name: String, previous: Int): Int =
        planets.getValue(name).orbits
            .fold(previous) { acc, orbitName ->
                acc + countAllOrbits(planets, orbitName, previous + 1)
            }

    private fun buildPlanets(input: List<Instruction>): Map<String, Planet> {
        val planets = mutableMapOf<String, Planet>()
        for (instruction in input) {
            planets.compute(instruction.planet) { name, found ->
                val planet = found ?: Planet(name)
                planet.addOrbit(instruction.orbit)

                planet
            }
            planets.computeIfAbsent(instruction.orbit) { name ->
                Planet(name)
            }
        }
        return planets
    }

}

class Instruction(val planet: String, val orbit: String)

data class Planet(val name: String) {

    val orbits = mutableListOf<String>()

    fun addOrbit(orbit: String) {
        orbits.add(orbit)
    }

}
