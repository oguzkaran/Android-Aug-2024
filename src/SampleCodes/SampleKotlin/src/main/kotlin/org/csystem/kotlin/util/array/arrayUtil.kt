package org.csystem.kotlin.util.array

import kotlin.random.Random

fun fillArray(a: IntArray, origin: Int, bound: Int, random: Random = Random) {
    for (i in a.indices)
        a[i] = random.nextInt(origin, bound)
}

fun randomIntArray(count: Int, origin: Int, bound: Int, random: Random = Random): IntArray {
    val a = IntArray(count)

    fillArray(a, origin, bound, random)

    return a
}

fun write(n: Int, a: IntArray) {
    TODO("Not yet implemented")
}
