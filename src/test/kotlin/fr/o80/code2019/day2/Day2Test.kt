package fr.o80.code2019.day2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


internal class Day2Test {
    @Test
    fun shouldWork() {
        val result = Day1().compute(parseInput("1,9,10,3,2,3,11,0,99,30,40,50"))

        assertEquals(3500, result)
    }
}