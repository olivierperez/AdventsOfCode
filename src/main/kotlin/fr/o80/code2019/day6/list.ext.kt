package fr.o80.code2019.day6

fun List<Instruction>.toPlanetsMap(): PlanetsMap {
    val planets = mutableMapOf<String, Planet>()
    for (instruction in this) {
        planets.addPlaner(instruction)
        planets.addOrbit(instruction)
    }
    return planets
}

private fun MutableMap<String, Planet>.addOrbit(instruction: Instruction) {
    computeIfPresent(instruction.orbit) { _, planet ->
        planet.apply { attractedBy = instruction.planet }
    }
    computeIfAbsent(instruction.orbit) { name ->
        Planet(name).apply { attractedBy = instruction.planet }
    }
}

private fun MutableMap<String, Planet>.addPlaner(instruction: Instruction) {
    computeIfPresent(instruction.planet) { name, planet ->
        planet.apply { addOrbit(instruction.orbit) }
    }
    computeIfAbsent(instruction.planet) { name ->
        Planet(name).apply { addOrbit(instruction.orbit) }
    }
}
