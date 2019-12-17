package fr.o80.code2019.day13

import java.math.BigInteger

class Values(input: List<BigInteger>) {
    private val map = mutableMapOf<Int, BigInteger>()

    init {
        input.forEachIndexed { index, v -> map[index] = v }
    }

    operator fun get(index: Int): BigInteger {
        return map.getOrDefault(index, BigInteger.ZERO)
    }

    operator fun set(index: Int, v: BigInteger) {
        map[index] = v
    }
}