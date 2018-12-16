package fr.o80.day15

/**
 * @author Olivier Perez
 */
class MapReader {
    fun read(input: String): List<Entity> {
        return input.lines()
                .mapIndexed { y, line -> Pair(y, line) }
                .flatMap { (y, line) ->
                    line.mapIndexed { x, c -> Pair(Point(x, y), c) }
                }
                .mapNotNull { (point, c) ->
                    when (c) {
                        'G' -> Goblin(point.x, point.y)
                        'E' -> Elf(point.x, point.y)
                        '#' -> Wall(point.x, point.y)
                        else -> null
                    }
                }
    }
}