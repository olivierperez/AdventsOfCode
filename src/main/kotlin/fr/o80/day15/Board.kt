package fr.o80.day15

class Board(input: String) {

    val maxPoint: Point
    val walls: Map<Point, Entity>
    private val entities: MutableList<Entity>
    private val players: MutableList<Entity>
    private val pathResolver = PathResolver(this)

    init {
        MapReader(input).let { mapReader ->
            maxPoint = mapReader.maxPoint
            entities = mapReader.entities().toMutableList()
        }
        players = entities.filter { it !is Wall }.toMutableList()
        walls = entities.filter { it is Wall }
                .map { Point(it.x, it.y) to it }
                .toMap()
    }

    fun players() = players.sortedWith(Comparator { a, b ->
        when {
            a.y < b.y -> -1
            b.y < a.y -> 1
            else      -> a.x - b.x
        }
    })

    fun enemiesOf(entity: Entity): List<Entity> = players.filter { it isEnemyOf entity }

    fun <R : Comparable<R>> neighborEnemies(entity: Entity, sortedBy: (Entity) -> R): List<Entity> {
        return players.asSequence()
                .filter { player -> player isEnemyOf entity }
                .filter { player ->
                    (player.y == entity.y && (player.x - entity.x == 1 || player.x - entity.x == -1)) || (player.x == entity.x && (player.y - entity.y == 1 || player.y - entity.y == -1))
                }
                .sortedBy(sortedBy)
                .toList()
    }

    fun isFree(x: Int, y: Int): Boolean =
        !walls.containsKey(Point(x, y)) && players.none { it.x == x && it.y == y }

    fun nextPossibleSteps(from: Entity, to: Entity): List<Pair<Point, Int>> {
        return pathResolver.nextStep(from = to.point(), to = from.point(), max = maxPoint)
                .map { node -> Pair(Point(node.x, node.y), node.dist) }
    }

    fun removeDead() {
        entities.removeIf { it.life <= 0 }
        players.removeIf { it.life <= 0 }
    }

    fun totalLife(): Int = players.sumBy { it.life }
}

data class Point(val x: Int, val y: Int) {
    operator fun rangeTo(max: Point): PointIterator = PointIterator(this, max)
}
