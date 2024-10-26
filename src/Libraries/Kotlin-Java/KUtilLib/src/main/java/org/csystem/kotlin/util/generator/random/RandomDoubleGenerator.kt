package org.csystem.kotlin.util.generator.random

import kotlin.random.Random

class RandomDoubleGenerator(private val count: Int, private val origin: Double, private val bound: Double,
                            private val random: Random = Random): Iterable<Double> {
    init {
        if (count <= 0 || origin >= bound)
            throw IllegalArgumentException("Invalid arguments")
    }

    private inner class RandomDoubleGeneratorIterator : Iterator<Double> {
        private var n: Int = 0
        override fun hasNext(): Boolean {
            return n + 1 <= count
        }

        override fun next(): Double {
            if (!hasNext())
                throw NoSuchElementException("All numbers generated")

            return random.nextDouble(origin, bound).also { ++n }
        }
    }
    override operator fun iterator(): Iterator<Double> = RandomDoubleGeneratorIterator()
}