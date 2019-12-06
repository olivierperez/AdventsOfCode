package fr.o80.code2019.day6

typealias PlanetsMap = Map<String, Planet>

fun PlanetsMap.countAllOrbits(name: String, previous: Int): Int =
    getValue(name).orbits
        .fold(previous) { acc, orbitName ->
            acc + countAllOrbits(orbitName, previous + 1)
        }

fun PlanetsMap.countJumpBetween(from: String, to: String): Int {
    val pathYOUtoCOM: List<String> = pathToCOM(from)
    val pathSANtoCOM: List<String> = pathToCOM(to)

    val pivot: String = pathYOUtoCOM.first { it in pathSANtoCOM }

    return pathYOUtoCOM.indexOf(pivot) + pathSANtoCOM.indexOf(pivot)
}

fun PlanetsMap.pathToCOM(origin: String): List<String> {
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

