package org.csystem.kotlin.util.generator.random

import kotlin.random.Random

class RandomLongGenerator(private val count: Int, private val origin: Long, private val bound: Long,
                          private val random: Random = Random): Iterable<Long> {
    init {
        if (count <= 0 || origin >= bound)
            throw IllegalArgumentException("Invalid arguments")
    }

    private class RandomLongGeneratorIterator(private val count: Int, private val origin: Long, private val bound: Long,
                                              private val random: Random) : Iterator<Long> {
        private var n: Int = 0

        override fun hasNext(): Boolean {
            return n + 1 <= count
        }

        override fun next(): Long {
            if (!hasNext())
                throw NoSuchElementException("All numbers generated")

            return random.nextLong(origin, bound).also { ++n }
        }
    }
    override operator fun iterator(): Iterator<Long> = RandomLongGeneratorIterator(count, origin, bound, random)
}