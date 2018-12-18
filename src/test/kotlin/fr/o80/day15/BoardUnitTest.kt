package fr.o80.day15

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class BoardUnitTest {

    @Test
    @DisplayName("Board should hold entities and give them back")
    fun shouldHoldEntities() {
        // Given
        val map = """
            |######
            |#.GE.#
            |#....#
            |#....#
            |#####E
        """.trimMargin()
        val board = Board(map)

        // When
        val entities = board.entities()

        // Then
        assertEquals(5, entities[2].x, "Last entity should be the more down of the three")
        assertEquals(4, entities[2].y, "Last entity should be the more down of the three")
        assertEquals(2, entities[0].x, "The first entity should be the one on top-left")
        assertEquals(1, entities[0].y, "The first entity should be the one on top-left")
    }

    @Test
    @DisplayName("Board should give entities for a given type")
    fun shouldProvideEntitiesByType() {
        // Given
        val map = """
            |#....
            |..GE.
            |.....
            |.....
            |...E.
        """.trimMargin()
        val board = Board(map)

        // When
        val elves = board.enemiesOf(Goblin(2, 1))

        // Then
        assertEquals(2, elves.size, "Elves are only elves")
        assertTrue(elves[0] is Elf, "Elves are enemies of goblins")
        assertTrue(elves[1] is Elf, "Elves are enemies of goblins")
    }

    @Test
    @DisplayName("Board should load map")
    fun shouldLoadMap() {
        // Given
        val map = """
            |G..
            |.E.
            |..#
        """.trimMargin()

        // When
        val board = Board(map)

        // Then
        assertTrue(board.isFree(5, 4), "An empty block should be free")
        assertFalse(board.isFree(0, 0), "A goblin should not be free")
        assertFalse(board.isFree(1, 1), "An elf should not be free")
        assertFalse(board.isFree(2, 2), "A wall should not be free")
    }

    @Test
    @DisplayName("Board find shortest path")
    fun shouldFindShortestPath() {
        // Given
        val map = """
            |#######
            |#.....#
            |#.....#
            |#.#.#.#
            |#...#.#
            |#...#.#
            |#######
        """.trimMargin()
        val board = Board(map)

        // When
        val firstStep = board.nextStep(
            from = Point(1, 3),
            to = Point(5, 5)
        )
        val secondStep = board.nextStep(
            from = firstStep,
            to = Point(5, 5)
        )
        val thirdStep = board.nextStep(
            from = secondStep,
            to = Point(5, 5)
        )

        // Then
        assertEquals(Point(1, 2), firstStep, "First step should be [1;2]")
        assertEquals(Point(2, 2), secondStep, "Second step should be [2;2]")
        assertEquals(Point(3, 2), thirdStep, "Third step should be [3;2]")
    }

    @Test
    @DisplayName("Check if entity is side by side with an enemy")
    fun shouldCheckIfEntityIsFighting() {
        // Given
        val map = """
            |#######
            |#EG...#
            |#..EE.#
            |#.#.#.#
            |#...#.#
            |#..G#E#
            |#######
        """.trimMargin()
        val board = Board(map)

        // When
        val firstElf = board.entities()[0]
        val firstElfNeighbors = board.neightborEnemies(firstElf)
        val firstElfIsFighting = firstElfNeighbors.isNotEmpty()

        val secondElf = board.entities()[2]
        val secondElfNeighbors = board.neightborEnemies(secondElf)
        val secondElfIsFighting = secondElfNeighbors.isNotEmpty()

        val fourthElf = board.entities()[5]
        val fourthElfNeighbors = board.neightborEnemies(fourthElf)
        val fourthElfIsFighting = fourthElfNeighbors.isNotEmpty()

        // Then
        assertTrue(firstElf is Elf, "First elf is and Elf")
        assertTrue(firstElfIsFighting, "First elf is fighting")

        assertTrue(secondElf is Elf, "Second elf is and Elf")
        assertFalse(secondElfIsFighting, "Second elf is NOT fighting")

        assertTrue(fourthElf is Elf, "Fourth elf is and Elf")
        assertFalse(fourthElfIsFighting, "Fourth elf is NOT fighting")
    }
}