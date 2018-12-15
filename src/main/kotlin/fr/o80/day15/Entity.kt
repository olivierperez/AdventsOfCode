package fr.o80.day15

sealed class Entity(val x: Int, val y: Int) {
    open infix fun isEnemyOf(other: Entity): Boolean = false
}

class Elf(x: Int, y: Int) : Entity(x, y) {
    override fun isEnemyOf(other: Entity): Boolean {
        return other is Goblin
    }
}

class Goblin(x: Int, y: Int) : Entity(x, y) {
    override fun isEnemyOf(other: Entity): Boolean {
        return other is Elf
    }
}

class Wall(x: Int, y: Int) : Entity(x, y)
