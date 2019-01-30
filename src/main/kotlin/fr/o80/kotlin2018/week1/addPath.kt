package fr.o80.kotlin2018.week1

fun addPath(input: String): String {

    val nodesMap = AdventMap(input)
    val nodesList: MutableList<Node> = nodesMap.noBlockAsList()

    // Compute distances

    while (nodesList.isNotEmpty()) {
        val current = nodesList.minBy { node -> node.dist }!!
        nodesList.remove(current)

        for (neighbor in nodesMap neighborsOf current) {
            val alt = current.dist + current.dist(neighbor)
            if (alt < neighbor.dist) {
                neighbor.dist = alt
                neighbor.prev = current
            }
        }
    }

    // Compute path

    for (n: Node in nodesMap.pathToEnd()) {
        n.type = NodeType.PATH
    }

    // Draw path

    return nodesMap.toString()
}