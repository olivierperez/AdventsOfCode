package fr.o80.code2018.day15

class Day15Resolver(
    input: String,
    private val drawSteps: Boolean = false,
    private val debugMoves: Boolean = false,
    private val showLife: Boolean = false
) {

    private val board = Board(input)

    var stepCount = 0
    private set

    fun resolve() {
        while (keepGoing()) {
            if (drawSteps) println("---------------------------")
            step()
            if (drawSteps) draw()
        }
    }

    private fun keepGoing(): Boolean {
        val entities = board.players()
        val elves = entities.filter { it is Elf }

        return entities.size != elves.size && elves.isNotEmpty()
    }

    private fun step() {
        stepCount++
        val players = board.players()

        players.forEach { entity ->
            if (entity.life > 0) {
                val fightingEnemies = board.neighborEnemies(entity) { it.life }
                if (fightingEnemies.isNotEmpty()) {
                    fight(fightingEnemies)
                }
                val nearEnemies = board.neighborEnemies(entity) { it.life }
                if (nearEnemies.isEmpty()) {
                    move(entity)
                }
            }
        }

        if (debugMoves)
            println("")

        board.removeDead()
    }

    private fun fight(fightingWith: List<Entity>) {
        val minLife = fightingWith[0].life
        val targets = fightingWith.filter { it.life == minLife }
            .sortedWith(Comparator { a, b ->
                when {
                    a.y > b.y -> 1
                    a.y < b.y -> -1
                    a.x > b.x -> 1
                    else -> -1
                }
            })
        targets.first().hit()
    }

    private fun move(entity: Entity) {
        val nextStep = board.enemiesOf(entity)
            .flatMap { enemy -> board.nextPossibleSteps(entity, enemy) }
            .sortedBy { (_, dist) -> dist }
        if (nextStep.isNotEmpty()) {
            val shortestSteps = nextStep.filter { x -> x.second == nextStep[0].second }
                .map { (point, _) -> point }
                .sortedWith(Comparator { a, b ->
                    when {
                        a.y > b.y -> 1
                        a.y < b.y -> -1
                        a.x > b.x -> 1
                        else -> -1
                    }
                })
            entity.moveTo(shortestSteps[0])
            if (debugMoves)
                println(entity.javaClass.simpleName + " moved to [${shortestSteps[0].x},${shortestSteps[0].y}]")
        }
    }

    fun draw() {
        if (showLife){
            showLife()
            println("")
        }
        println("Step $stepCount")
        val allEntities = board.players()
            .map { Point(it.x, it.y) to it }
            .toMap()
            .toMutableMap()
            .apply { putAll(board.walls) }

        for (y in 0..board.maxPoint.y) {
            for (x in 0..board.maxPoint.x) {
                val entity = allEntities[Point(x, y)]
                if (entity != null) {
                    print(entity.char())
                } else {
                    print('.')
                }
            }
            println()
        }
        println()
    }

    fun showLife() {
        board.players()
            .forEach { entity ->
                println(entity.javaClass.simpleName + ": " + entity.life)
            }
    }

    fun totalLife(): Int = board.totalLife()
}
