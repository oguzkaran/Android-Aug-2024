package org.csystem.kotlin.util.array

import kotlin.random.Random

fun fillArray(a: IntArray, origin: Int, bound: Int, random: Random = Random) {
    for (i in a.indices)
        a[i] = random.nextInt(origin, bound)
}

fun fillArray(a: DoubleArray, origin: Double, bound: Double, random: Random = Random) {
    for (i in a.indices)
        a[i] = random.nextDouble(origin, bound)
}

fun max(a: IntArray): Int {
    var result = a[0]

    for (i in 1..<a.size)
        if (result < a[i])
            result = a[i]

    return result
}

fun min(a: IntArray): Int {
    var result = a[0]

    for (i in 1..<a.size)
        if (a[i] < result)
            result = a[i]

    return result
}


fun randomArray(count: Int, origin: Int, bound: Int, random: Random = Random): IntArray {
    val a = IntArray(count)

    fillArray(a, origin, bound, random)

    return a
}

fun randomArray(count: Int, origin: Double, bound: Double, random: Random = Random): DoubleArray {
    val a = DoubleArray(count)

    fillArray(a, origin, bound, random)

    return a
}

fun secondMax(a: IntArray): Int {
    TODO("Not yet implemented!...")
}

fun secondMinx(a: IntArray): Int {
    TODO("Not yet implemented!...")
}

