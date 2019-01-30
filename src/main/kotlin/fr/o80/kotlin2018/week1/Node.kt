package fr.o80.kotlin2018.week1

class Node(
        val x: Int,
        val y: Int,
        var type: NodeType,
        var prev: Node? = null,
        var dist: Int = if (type == NodeType.START) 0 else Int.MAX_VALUE
) {

    fun dist(other: Node): Int =
            if (x == other.x || y == other.y) 2 else 3
}

fun Char.toNodeType(): NodeType = NodeType.from(this)

enum class NodeType(private val char: Char) {
    START('S'),
    END('X'),
    BLOCK('B'),
    EMPTY('.'),
    PATH('*');

    override fun toString(): String {
        return char.toString()
    }

    companion object {
        fun from(char: Char): NodeType {
            return values().first { it.char == char }
        }
    }
}