package fr.o80.day15

/**
 * @author Olivier Perez
 */
class MapReader(private val input: String) {

    var width: Int
        private set
    var height: Int
        private set

    init {
        val lines = input.lines()
        width = lines[0].length
        height = lines.size
    }

    fun entities(): List<Entity> {
        return input.lineSequence()
                .mapIndexed { y, line -> Pair(y, line) }
                .flatMap { (y, line) ->
                    line.asSequence()
                            .mapIndexed { x, c -> Pair(Point(x, y), c) }
                }
                .mapNotNull { (point, c) ->
                    when (c) {
                        'G' -> Goblin(point.x, point.y)
                        'E' -> Elf(point.x, point.y)
                        '#' -> Wall(point.x, point.y)
                        else -> null
                    }
                }
                .toList()
    }
}