package org.csystem.util.io

import java.io.PrintStream

fun readInt(path: String) : Int {
    TODO("Not yet implemented")
}

fun <T> Iterable<T>.write(sep: String = "\n", end: String = "", output: PrintStream = System.out) {
    forEach { output.print("$it$sep") }
    output.print(end)
}

fun <T> Array<T>.write(sep: String = "\n", end: String = "", output: PrintStream = System.out) {
    forEach { output.print("$it$sep") }
    output.print(end)
}

fun IntArray.write(n: Int = -1, count: Int = this.size, sep: String = " ", end: String = "\n", output: PrintStream = System.out) {
    val fmt = "%%0%dd%%s".format(if (n < 1) 1 else n)

    (0..<count).forEach { output.print(fmt.format(this[it], sep)) }
    output.print(end)
}