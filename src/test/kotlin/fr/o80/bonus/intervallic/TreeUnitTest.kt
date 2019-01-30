package fr.o80.bonus.intervallic

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class TreeUnitTest {

    @Test
    @DisplayName("Can add child elements to root")
    fun addToRootChildren() {
        // Given
        val root = TreeNode(0)
        val tree = Tree(root)

        // When
        tree.addChild(root, 5)
        val children = tree.childrenOf(root)

        // Then
        assertEquals(1, children.size)
        assertEquals(5, children[0].value)
    }
}