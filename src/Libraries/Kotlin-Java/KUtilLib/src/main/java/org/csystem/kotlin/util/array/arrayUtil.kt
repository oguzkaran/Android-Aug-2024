package org.csystem.kotlin.util.array

    import kotlin.random.Random

    /**
     * Extension function to fill an IntArray with random integers within a specified range.
     *
     * @param origin The lower bound (inclusive) of the random values.
     * @param bound The upper bound (exclusive) of the random values.
     * @param random The Random instance to use for generating random values. Defaults to Random.
     */
    fun IntArray.fillArray(origin: Int, bound: Int, random: Random = Random) {
        for (i in this.indices)
            this[i] = random.nextInt(origin, bound)
    }

    /**
     * Extension function to fill a DoubleArray with random doubles within a specified range.
     *
     * @param origin The lower bound (inclusive) of the random values.
     * @param bound The upper bound (exclusive) of the random values.
     * @param random The Random instance to use for generating random values. Defaults to Random.
     */
    fun DoubleArray.fillArray(origin: Double, bound: Double, random: Random = Random) {
        for (i in this.indices)
            this[i] = random.nextDouble(origin, bound)
    }

    /**
     * Extension function to find the maximum value in an IntArray.
     *
     * @return The maximum value in the array.
     */
    fun IntArray.max(): Int {
        var result = this[0]

        for (i in 1 until this.size)
            if (result < this[i])
                result = this[i]

        return result
    }

    /**
     * Extension function to find the minimum value in an IntArray.
     *
     * @return The minimum value in the array.
     */
    fun IntArray.min(): Int {
        var result = this[0]

        for (i in 1 until this.size)
            if (this[i] < result)
                result = this[i]

        return result
    }

    /**
     * Extension function to generate an IntArray with random integers within a specified range.
     *
     * @param count The number of random integers to generate.
     * @param origin The lower bound (inclusive) of the random values.
     * @param bound The upper bound (exclusive) of the random values.
     * @return An IntArray filled with random integers.
     */
    fun Random.randomArray(count: Int, origin: Int, bound: Int): IntArray {
        val a = IntArray(count)

        a.fillArray(origin, bound, this)

        return a
    }

    /**
     * Extension function to generate a DoubleArray with random doubles within a specified range.
     *
     * @param count The number of random doubles to generate.
     * @param origin The lower bound (inclusive) of the random values.
     * @param bound The upper bound (exclusive) of the random values.
     * @return A DoubleArray filled with random doubles.
     */
    fun Random.randomArray(count: Int, origin: Double, bound: Double): DoubleArray {
        val a = DoubleArray(count)

        a.fillArray(origin, bound, this)

        return a
    }

    /**
     * Extension function to find the second maximum value in an IntArray.
     *
     * @return The second maximum value in the array.
     */
    fun IntArray.secondMax(): Int {
        TODO("Not yet implemented!...")
    }

    /**
     * Extension function to find the second minimum value in an IntArray.
     *
     * @return The second minimum value in the array.
     */
    fun IntArray.secondMin(): Int {
        TODO("Not yet implemented!...")
    }