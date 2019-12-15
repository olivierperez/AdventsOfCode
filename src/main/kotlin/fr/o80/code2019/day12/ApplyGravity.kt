package fr.o80.code2019.day12

class ApplyGravity {
    operator fun invoke(moon: Moon, others: List<Moon>) {
        for (other in others) {
            if (moon !== other) {
                applyGravity(moon, other)
            }
        }
    }

    private fun applyGravity(moon: Moon, other: Moon) {
        moon.velocity.x += if (moon.position.x < other.position.x) 1 else if (moon.position.x > other.position.x) -1 else 0
        moon.velocity.y += if (moon.position.y < other.position.y) 1 else if (moon.position.y > other.position.y) -1 else 0
        moon.velocity.z += if (moon.position.z < other.position.z) 1 else if (moon.position.z > other.position.z) -1 else 0
    }
}
