package fr.o80.day15

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 * @author Olivier Perez
 */
internal class MapReaderUnitTest {

    @Test
    @DisplayName("Reader should extract entitites")
    fun shouldReadEntities() {
        // Given
        val map = """
            |####
            |#EG#
            |#  #
            |####
        """.trimMargin()

        // When
        val reader = MapReader(map)
        val entities = reader.entities()

        // Then
        assertTrue(entities.contains(Elf(1,1)))
        assertTrue(entities.contains(Goblin(2,1)))
        assertTrue(entities.contains(Wall(0,0)))

        assertFalse(entities.contains(Elf(1,2)))
        assertFalse(entities.contains(Goblin(1,2)))
        assertFalse(entities.contains(Wall(1,2)))
        assertFalse(entities.contains(Elf(2,2)))
        assertFalse(entities.contains(Goblin(2,2)))
        assertFalse(entities.contains(Wall(2,2)))
    }

    @Test
    @DisplayName("Reader should retrieve size")
    fun shouldReadOutline() {
        // Given
        val map = """
            |####
            |#EG#
            |# ##
            |#  #
            |## #
            |####
        """.trimMargin()

        // When
        val reader = MapReader(map)

        // Then
        assertEquals(Point(3,5), reader.maxPoint, "The max point should be [4;6]")
    }
}