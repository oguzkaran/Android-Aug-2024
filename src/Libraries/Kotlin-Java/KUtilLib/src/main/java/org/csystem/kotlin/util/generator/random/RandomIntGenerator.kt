package org.csystem.kotlin.util.generator.random

import kotlin.random.Random

class RandomIntGenerator(private val count: Int, private val origin: Int, private val bound: Int,
                         private val random: Random = Random): Iterable<Int> {
    init {
        if (count <= 0 || origin >= bound)
            throw IllegalArgumentException("Invalid arguments")
    }
    override operator fun iterator(): Iterator<Int> {
        return object: Iterator<Int> {
            override fun hasNext(): Boolean {
                TODO("Not yet implemented")
            }

            override fun next(): Int {
                TODO("Not yet implemented")
            }
        }
    }
}