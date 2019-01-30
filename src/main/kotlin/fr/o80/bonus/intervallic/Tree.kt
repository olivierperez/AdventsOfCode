package fr.o80.bonus.intervallic

class Tree<E>(root: TreeNode<E>) {

    private val list = mutableListOf<TreeNode<E>>()

    init {
        list.add(root)
    }

    fun addChild(parent: TreeNode<E>, element: E) {
        parent.right += 2
        val node = TreeNode(element, parent.right - 2, parent.right - 1, parent.depth + 1)
        list.add(node)
    }

    fun childrenOf(parent: TreeNode<E>): List<TreeNode<E>> =
        list.filter { node ->
            node.left > parent.left && node.right < parent.right
        }

}

data class TreeNode<E>(
    val value: E,
    var left: Int = 0,
    var right: Int = 1,
    var depth: Int = 0
)
