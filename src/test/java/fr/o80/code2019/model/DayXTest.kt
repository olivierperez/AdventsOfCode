package fr.o80.code2019.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class DayXTest {

    @Test
    fun testBilly() {
        val dayX = DayX()
        assertEquals(42, dayX.goBilly(mutableListOf()))
    }

}
