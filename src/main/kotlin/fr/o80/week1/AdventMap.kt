package fr.o80.week1


class AdventMap(input: String) : ArrayList<MutableList<Node>>() {

    private var map: List<String> = input.split('\n')
    private val height = map.size
    private val width = map[0].length

    lateinit var end: Node

    init {
        for (y in 0 until height) {
            val line = mutableListOf<Node>()
            for (x in 0 until width) {
                val type = map[y][x].toNodeType()
                val node = Node(x, y, type)

                line.add(node)
                if (type == NodeType.END) {
                    end = node
                }
            }
            add(line)
        }
    }

    infix fun neighborsOf(n: Node): List<Node> {
        val x = n.x
        val y = n.y
        return listOfNotNull(
                this[x - 1, y - 1], this[x - 1, y], this[x - 1, y + 1],
                this[x, y - 1], this[x, y], this[x, y + 1],
                this[x + 1, y - 1], this[x + 1, y], this[x + 1, y + 1])
    }

    operator fun get(x: Int, y: Int): Node? {
        if (y < 0 || y >= this.size) {
            return null
        }
        if (x < 0 || x >= this[y].size) {
            return null
        }
        return this[y][x]
    }

    fun noBlockAsList(): MutableList<Node> {
        return this.asSequence().flatten().filter { it.type != NodeType.BLOCK }.toMutableList()
    }

    fun pathToEnd(): Iterable<Node> = NodeIterable(end)

    override fun toString(): String {
        val builder = StringBuilder()
        for (y in 0 until height) {
            for (x in 0 until width) {
                builder.append(this[x, y]?.type)
            }
            builder.append('\n')
        }

        return builder.deleteCharAt(builder.length - 1).toString()
    }

}

