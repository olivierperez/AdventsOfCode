package fr.o80.day15

class Day15Resolver(input: String) {

    private val board = Board(input)

    private var stepCount = 0

    fun resolve(draw: Boolean) {
        while(keepGoing()) {
            step()
            if (draw) {
                draw()
            }
        }
    }

    private fun keepGoing(): Boolean {
        val entities = board.entities()
        val elves = entities.filter{ it is Elf}

        return entities.size != elves.size && elves.isNotEmpty() && stepCount < 10
    }

    private fun step() {
        stepCount ++
        val entities = board.entities()

        entities.forEach { entity ->
            val fightingWith = board.neightborEnemies(entity)

            if (fightingWith.isNotEmpty()) {
                fight(entity)
            }

            if (fightingWith.isEmpty()) {
                move(entity)
            }
        }
    }

    private fun fight(entity: Entity) {
        println("TODO fight")
    }

    private fun move(entity: Entity) {
        val nextStep = board.enemiesOf(entity)
            .map { enemy -> board.nextStep(entity, enemy) }
            .minBy { (_, dist) -> dist } // TODO - C'est pas forcément le premier "min" qu'il faut choisir
            ?.first
        if (nextStep != null) {
            entity.moveTo(nextStep)
        }
    }

    fun draw() {
        val allEntities = board.entities().map { Point(it.x, it.y) to it }.toMap().toMutableMap()
            .apply { putAll(board.walls) }

        for (x in 0..board.maxPoint.x){
            for (y in 0..board.maxPoint.y) {
                val entity = allEntities[Point(x, y)]
                if (entity!= null) {
                    print(entity.char())
                } else {
                    print('.')
                }
            }
            println()
        }
        println()
    }

    fun steps() = 0

    fun totalLife(): Int = 0
}
