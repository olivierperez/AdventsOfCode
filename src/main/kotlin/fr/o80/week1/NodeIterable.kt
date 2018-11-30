package fr.o80.week1

class NodeIterable(private val firstNode: Node) : Iterable<Node> {
    override fun iterator(): Iterator<Node> = NodeIterator(firstNode)
}

class NodeIterator(first: Node) : Iterator<Node> {

    private var current: Node? = first

    override fun hasNext(): Boolean = current != null

    override fun next(): Node {
        return current!!
                .also { current = current!!.prev }
    }

}