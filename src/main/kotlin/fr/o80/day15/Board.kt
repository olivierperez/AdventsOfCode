package fr.o80.day15

class Board(private val entities: List<Entity>, private val map: String) {

    fun entities() = entities.sortedWith(Comparator { a, b ->
        when {
            a.y < b.y -> -1
            b.y < a.y -> 1
            else -> a.x - b.x
        }
    })

    fun enemiesOf(entity: Entity): List<Entity> =
            entities.filter { it isEnemyOf entity }

    fun isFree(x: Int, y: Int): Boolean =
            map.lines()[y][x] == '.'

    fun shortestPath(from: Point, to: Point): Point {
        return Point(-1, -1)
    }
}

data class Point(val x: Int, val y: Int)
