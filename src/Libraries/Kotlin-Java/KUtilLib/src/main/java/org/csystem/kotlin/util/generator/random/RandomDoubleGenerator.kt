package org.csystem.kotlin.util.generator.random

import kotlin.random.Random

class RandomDoubleGenerator(private val count: Int, private val origin: Double, private val bound: Double,
                            private val random: Random = Random): Iterable<Double> {
    init {
        if (count <= 0 || origin >= bound)
            throw IllegalArgumentException("Invalid arguments")
    }
    override operator fun iterator(): Iterator<Double> {
        return object: Iterator<Double> {
            override fun hasNext(): Boolean {
                TODO("Not yet implemented")
            }

            override fun next(): Double {
                TODO("Not yet implemented")
            }
        }
    }
}