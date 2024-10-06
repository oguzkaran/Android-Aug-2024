package org.csystem.kotlin.math

class DoubleMatrix(row: Int, col: Int) {
    init {
        if (row <= 0 || col <= 0)
            throw IllegalArgumentException("row and col must be positive")
    }
    private var mData = Array(row) {DoubleArray(col)}

    operator fun get(i: Int): DoubleArray {
        TODO("m[i][j]")
    }

    //...

    operator fun plus(other: DoubleMatrix): DoubleMatrix {
        TODO()
    }

    operator fun minus(other: DoubleMatrix): DoubleMatrix {
        TODO()
    }

    operator fun times(other: DoubleMatrix): DoubleMatrix {
        TODO()
    }

    fun transpose() {
        TODO()
    }

    fun transposed(): DoubleMatrix {
        TODO()
    }

    fun isSquareMatrix(): Boolean {
        TODO()
    }

    fun diagonal(): DoubleArray {
        TODO()
    }
    //...
}