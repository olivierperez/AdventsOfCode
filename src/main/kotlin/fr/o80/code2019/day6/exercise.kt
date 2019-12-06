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
                Planet(name).apply { attractedBy = instruction.planet }
            }
            planets.computeIfPresent(instruction.orbit) { _, planet ->
                planet.apply { attractedBy = instruction.planet }
            }
        }
        return planets
    }

    fun computePathToSanta(input: List<Instruction>): Int {
        val planets: Map<String, Planet> = buildPlanets(input)

        val pathYOUtoCOM: List<String> = planets.pathToCOM("YOU")
        val pathSANtoCOM: List<String> = planets.pathToCOM("SAN")

        val pivot: String = common(pathYOUtoCOM, pathSANtoCOM)

        return pathYOUtoCOM.indexOf(pivot) + pathSANtoCOM.indexOf(pivot)
    }

    private fun common(path1: List<String>, path2: List<String>): String {
        for (p1 in path1) {
            for (p2 in path2) {
                if (p1 == p2)
                    return p1
            }
        }
        throw IllegalArgumentException("Tes 2 chemins n'ont pas de match !")
    }

}

private fun Map<String, Planet>.pathToCOM(origin: String): List<String> {
    val path = mutableListOf<String>()
    var current = get(origin)
    while (current != null) {
        current = get(current.attractedBy)
        if (current != null) {
            path.add(current.name)
        }
    }
    return path
}

class Instruction(val planet: String, val orbit: String)

data class Planet(val name: String) {

    var attractedBy: String? = null

    val orbits = mutableListOf<String>()

    fun addOrbit(orbit: String) {
        orbits.add(orbit)
    }

}
