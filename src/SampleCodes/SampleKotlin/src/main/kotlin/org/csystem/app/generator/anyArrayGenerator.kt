package org.csystem.app.generator

import org.csystem.kotlin.math.Complex
import org.csystem.kotlin.math.geometry.Point
import kotlin.random.Random

private fun createObject(): Any {
    return when (Random.nextInt(5)) {
        0 -> Point(Random.nextDouble(-100.0, 100.0), Random.nextDouble(-100.0, 100.0))
        1 -> Complex(Random.nextDouble(-10.0, 10.0), Random.nextDouble(-10.0, 10.0))
        2 -> Random.nextInt(-128, 128)
        3 -> Random.nextBoolean()
        else -> (if (Random.nextBoolean()) 'A' else 'a') + Random.nextInt(26)
    }
}

fun createArray(count: Int): Array<Any> {
    val result = Array<Any>(count) { }

    for (i in result.indices)
        result[i] = createObject()

    return result
}