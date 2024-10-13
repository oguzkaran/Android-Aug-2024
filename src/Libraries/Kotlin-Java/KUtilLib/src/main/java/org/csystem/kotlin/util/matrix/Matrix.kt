package org.csystem.kotlin.util.matrix

import kotlin.random.Random

typealias IntMatrix = Array<IntArray>

fun createMatrix(row: Int, col: Int) = IntMatrix(row) { IntArray(col) }

fun Random.createRandomIntMatrix(row: Int, col: Int, origin: Int, bound: Int): IntMatrix {
    val m = createMatrix(row, col)

    m.fillRandom(origin, bound, this)

    return m
}

fun IntMatrix.fillRandom(origin: Int, bound: Int, random: Random = Random) {
    for (a in this)
        for (i in a.indices)
            a[i] = random.nextInt(origin, bound)
}
