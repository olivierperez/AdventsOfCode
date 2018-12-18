package fr.o80.day15

class Board(input: String) {

    val maxPoint: Point
    val walls: Map<Point, Entity>
    private val entities: List<Entity>
    private val players: List<Entity>
    private val pathResolver = PathResolver(this)

    init {
        MapReader(input).let { mapReader ->
            maxPoint = mapReader.maxPoint
            entities = mapReader.entities()
        }
        players = entities.filter { it !is Wall }
        walls = entities.filter { it is Wall }
            .map { Point(it.x, it.y) to it }
            .toMap()
    }

    fun entities() = players.sortedWith(Comparator { a, b ->
        when {
            a.y < b.y -> -1
            b.y < a.y -> 1
            else -> a.x - b.x
        }
    })

    fun enemiesOf(entity: Entity): List<Entity> = players.filter { it isEnemyOf entity }

    fun neightborEnemies(entity: Entity): List<Entity> {
        return players.asSequence()
            .filter { player -> player isEnemyOf entity }
            .filter { player ->
                (player.y == entity.y && (player.x - entity.x == 1 || player.x - entity.x == -1)) || (player.x == entity.x && (player.y - entity.y == 1 || player.y - entity.y == -1))
            }
            .toList()
    }

    fun isFree(x: Int, y: Int): Boolean =
        !walls.containsKey(Point(x, y)) && players.none { it.x == x && it.y == y }

    fun nextStep(from: Entity, to: Entity): Pair<Point, Int> {
        return pathResolver.nextStep(from = to.point(), to = from.point(), max = maxPoint)
    }
}

data class Point(val x: Int, val y: Int) {
    operator fun rangeTo(max: Point): PointIterator = PointIterator(this, max)
}
