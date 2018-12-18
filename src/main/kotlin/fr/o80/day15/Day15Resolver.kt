package fr.o80.day15

class Day15Resolver(input: String) {

    private val board = Board(input)

    fun resolve(draw: Boolean) {
        // TODO Loop until one of the teams is totally dead.

        step()

        if (draw) {
            draw()
        }

        step()

        if (draw) {
            draw()
        }
    }

    private fun step() {
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
            entity.x = nextStep.x
            entity.y = nextStep.y
        }
    }

    fun draw() {
        val allEntities = board.entities().map { Point(it.x, it.y) to it }.toMap().toMutableMap()
            .apply { putAll(board.walls) }

        for (p in Point(0,0) .. board.maxPoint){
            val entity = allEntities[p]
            if (entity!= null) {
                print(entity.char())
            } else {
                print('.')
            }

            if (p.x == board.maxPoint.x) {
                println()
            }
        }
        println()
    }

    fun steps() = 0

    fun totalLife(): Int = 0
}
