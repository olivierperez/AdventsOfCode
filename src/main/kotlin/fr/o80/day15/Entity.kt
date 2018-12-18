package fr.o80.day15

sealed class Entity(var x: Int, var y: Int) {

    var life: Int = 200

    open infix fun isEnemyOf(other: Entity): Boolean = false

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Entity) return false

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }

    fun point(): Point = Point(x, y)
    open fun char(): Char = '?'
}

class Elf(x: Int, y: Int) : Entity(x, y) {
    override fun isEnemyOf(other: Entity): Boolean {
        return other is Goblin
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Elf) return false
        if (!super.equals(other)) return false
        return true
    }

    override fun char(): Char {
        return 'E'
    }
}

class Goblin(x: Int, y: Int) : Entity(x, y) {
    override fun isEnemyOf(other: Entity): Boolean {
        return other is Elf
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Goblin) return false
        if (!super.equals(other)) return false
        return true
    }

    override fun char(): Char {
        return 'G'
    }
}

class Wall(x: Int, y: Int) : Entity(x, y) {
    override fun char(): Char {
        return '#'
    }
}
