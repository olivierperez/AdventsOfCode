package fr.o80.day15

class Board(entities: List<Entity>, private val maxPoint: Point) {

    private val walls = entities.filter { it is Wall }.map { Point(it.x, it.y) to it }.toMap()
    private val players = entities.filter { it !is Wall }
    private val pathResolver = PathResolver(this)

    fun entities() = players.sortedWith(Comparator { a, b ->
        when {
            a.y < b.y -> -1
            b.y < a.y -> 1
            else      -> a.x - b.x
        }
    })

    fun enemiesOf(entity: Entity): List<Entity> =
        players.filter { it isEnemyOf entity }

    fun isFree(x: Int, y: Int): Boolean =
        !walls.containsKey(Point(x, y)) && players.filter { it.x == x && it.y == y }.isEmpty()

    fun nextStep(from: Point, to: Point): Point {
        return pathResolver.nextStep(
                from = to,
                to = from,
                max = maxPoint)
    }
}

data class Point(val x: Int, val y: Int) {
    operator fun rangeTo(max: Point): PointIterator = PointIterator(this, max)
}
