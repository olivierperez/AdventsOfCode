package fr.o80.code2019.day12

import kotlin.math.abs

data class Moon(
    val position: Vec3,
    val velocity: Vec3
) {
    fun fullEnergy(): Int {
        return position.energy() * velocity.energy()
    }

    companion object {
        fun at(x: Int, y: Int, z: Int): Moon {
            return Moon(
                Vec3(x, y, z),
                Vec3(0, 0, 0)
            )
        }
    }
}

data class Vec3(
    var x: Int,
    var y: Int,
    var z: Int
) {
    fun energy(): Int {
        return abs(x) + abs(y) + abs(z)
    }
}
