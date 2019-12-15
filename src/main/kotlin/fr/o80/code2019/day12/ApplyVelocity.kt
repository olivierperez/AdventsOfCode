package fr.o80.code2019.day12

class ApplyVelocity {
    operator fun invoke(moon: Moon) {
        moon.position.x += moon.velocity.x
        moon.position.y += moon.velocity.y
        moon.position.z += moon.velocity.z
    }
}