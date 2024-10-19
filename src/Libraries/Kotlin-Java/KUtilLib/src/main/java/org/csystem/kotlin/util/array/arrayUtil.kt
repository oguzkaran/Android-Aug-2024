package org.csystem.kotlin.util.array

import kotlin.random.Random

fun IntArray.fillArray(origin: Int, bound: Int, random: Random = Random) {
    for (i in this.indices)
        this[i] = random.nextInt(origin, bound)
}

fun DoubleArray.fillArray(origin: Double, bound: Double, random: Random = Random) {
    for (i in this.indices)
        this[i] = random.nextDouble(origin, bound)
}

fun IntArray.max(): Int {
    var result = this[0]

    for (i in 1..<this.size)
        if (result < this[i])
            result = this[i]

    return result
}

fun IntArray.min(): Int {
    var result = this[0]

    for (i in 1..<this.size)
        if (this[i] < result)
            result = this[i]

    return result
}


fun Random.randomArray(count: Int, origin: Int, bound: Int): IntArray {
    val a = IntArray(count)

    a.fillArray(origin, bound, this)

    return a
}

fun Random.randomArray(count: Int, origin: Double, bound: Double): DoubleArray {
    val a = DoubleArray(count)

    a.fillArray(origin, bound, this)

    return a
}



fun IntArray.secondMax(): Int {
    TODO("Not yet implemented!...")
}

fun IntArray.secondMin(): Int {
    TODO("Not yet implemented!...")
}

