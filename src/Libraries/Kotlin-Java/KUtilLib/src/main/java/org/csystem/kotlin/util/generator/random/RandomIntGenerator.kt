package org.csystem.kotlin.util.generator.random

import kotlin.random.Random

class RandomIntGenerator(private val count: Int, private val origin: Int, private val bound: Int,
                         private val random: Random = Random): Iterable<Int> {
    init {
        if (count <= 0 || origin >= bound)
            throw IllegalArgumentException("Invalid arguments")
    }
    override operator fun iterator(): Iterator<Int> {
        var n = 0

        return object: Iterator<Int> {
            override fun hasNext(): Boolean {
                return n + 1 <= count
            }

            override fun next(): Int {
                if (!hasNext())
                    throw NoSuchElementException("All numbers generated")

                return random.nextInt(origin, bound).also { ++n }
            }
        }
    }
}