package fr.o80.code2019.day4

import kotlin.system.measureTimeMillis

fun main() {
    val time = measureTimeMillis {
        val dayPart1 = Day4Part1()
        val partOne = dayPart1.gogogo(264360, 746325)
        println("PartOne: $partOne")
        val dayPart2 = Day4Part2()
        val partTwo = dayPart2.gogogo(264360, 746325)
        println("PartTwo: $partTwo")
    }

    println("${time}ms")
}

class Day4Part1 : Day4(listOf(SameDigitPart1(), IncreaseDigits()))
class Day4Part2 : Day4(listOf(SameDigitPart2(), IncreaseDigits()))

abstract class Day4(private val checks: List<Checking>) {

    fun gogogo(min: Int, max: Int = min): Int {
        val passwords = generatePasswords(min, max)
        return passwords.size
    }

    private fun generatePasswords(min: Int, max: Int): List<String> {
        return (min..max)
            .map(Int::toString)
            .filter { check(it, checks) }
            .toList()
    }

    private fun check(password: String, checks: List<Checking>): Boolean {
        for (checker in checks) {
            if (!checker.check(password)) {
                return false
            }
        }

        return true
    }

}

interface Checking {
    fun check(password: String): Boolean
}

class SameDigitPart1 : Checking {
    override fun check(password: String): Boolean {
        for (i in 0 until password.length - 1) {
            if (password[i] == password[i + 1])
                return true
        }
        return false
    }
}

class SameDigitPart2 : Checking {
    override fun check(password: String): Boolean {
        var i = 0
        var matched = false
        while (i < password.length) {
            val end = password.substring(i)
            val count = end.count { it == end[0] }
            if (count == 2) {
                matched = true
            }
            i += count
        }

        return matched
    }
}

class IncreaseDigits : Checking {
    override fun check(password: String): Boolean {
        for (i in 0 until password.length - 1) {
            if (password[i] > password[i + 1])
                return false
        }
        return true
    }
}
