package org.csystem.kotlin.util.console

import java.io.PrintStream
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext

fun readInt(prompt: String, errorPrompt: String = "", end: String = "") = Console.readInt(prompt, errorPrompt, end)

fun readLong(prompt: String, errorPrompt: String = "", end: String = "") = Console.readLong(prompt, errorPrompt, end)

fun readDouble(prompt: String, errorPrompt: String = ""): Double {
    while (true) {
        try {
            return Console.readWithPrompt(prompt).toDouble()
        } catch (_: NumberFormatException) {
            print(errorPrompt)
        }
    }
}

fun readShort(prompt: String, errorPrompt: String = ""): Short {
    while (true) {
        try {
            return Console.readWithPrompt(prompt).toShort()
        } catch (_: NumberFormatException) {
            print(errorPrompt)
        }
    }
}

fun readByte(prompt: String, errorPrompt: String): Byte {
    while (true) {
        try {
            return Console.readWithPrompt(prompt).toByte()
        } catch (_: NumberFormatException) {
            print(errorPrompt)
        }
    }
}

fun readFloat(prompt: String, errorPrompt: String = ""): Float {
    while (true) {
        try {
            return Console.readWithPrompt(prompt).toFloat()
        } catch (_: NumberFormatException) {
            print(errorPrompt)
        }
    }
}

fun readBoolean(prompt: String, errorPrompt: String = ""): Boolean {
    while (true) {
        val result = Console.readWithPrompt(prompt).toBoolean()

        if (!result && !prompt.equals("false", true)) {
            println(errorPrompt)
            continue
        }

        return result;
    }
}

fun readChar(prompt: String, errorPrompt: String = "", end: String = "") = Console.readChar(prompt, errorPrompt, end)

fun readString(prompt: String) = Console.readString(prompt)


fun readBigDecimal(prompt: String, errorPrompt: String = ""): BigDecimal {
    while (true) {
        try {
            return Console.readWithPrompt(prompt).toBigDecimal()
        } catch (_: NumberFormatException) {
            print(errorPrompt)
        }
    }
}

fun readBigDecimal(prompt: String, mathContext: MathContext, errorPrompt: String = ""): BigDecimal {
    while (true) {
        try {
            return Console.readWithPrompt(prompt).toBigDecimal(mathContext)
        } catch (_: NumberFormatException) {
            print(errorPrompt)
        }
    }
}

fun readBigInteger(prompt: String, errorPrompt: String = ""): BigInteger {
    while (true) {
        try {
            return Console.readWithPrompt(prompt).toBigInteger()
        } catch (_: NumberFormatException) {
            print(errorPrompt)
        }
    }
}

fun IntArray.printArray(n: Int = -1, count: Int = this.size, sep: String = " ", end: String = "\n") {
    val fmt = "%%0%dd%%s".format(if (n < 1) 1 else n)

    (0..<count).forEach { print(fmt.format(get(it), sep)) }
    print(end)
}

fun LongArray.printArray(n: Int = -1, sep: String = " ", end: String = "\n") {
    val fmt = "%%0%dd%%s".format(if (n < 1) 1 else n)

    forEach { print(fmt.format(it, sep)) }
    print(end)
}

fun DoubleArray.printArray(n: Int = -1, sep: String = "\n", end: String = "") {
    val fmt = if (n < 1) "%f%s" else "%%.%df%%s".format(n)

    forEach { print(fmt.format(it, sep)) }
    print(end)
}

fun ShortArray.printArray(n: Int = -1, sep: String = " ", end: String = "\n") {
    val fmt = "%%0%dd%%s".format(if (n < 1) 1 else n)

    forEach { print(fmt.format(it, sep)) }
    print(end)
}


fun ByteArray.printArray(n: Int = -1, sep: String = " ", end: String = "\n") {
    val fmt = "%%0%dd%%s".format(if (n < 1) 1 else n)

    forEach { print(fmt.format(it, sep)) }
    print(end)
}

fun FloatArray.printArray(n: Int = -1, sep: String = "\n", end: String = "") {
    val fmt = if (n < 1) "%f%s" else "%%.%df%%s".format(n)

    forEach { print(fmt.format(it, sep)) }
    print(end)
}

fun BooleanArray.printArray(sep: String = "\n", end: String = "") {
    forEach { print("$it$sep") }
    print(end)
}

fun CharArray.printArray(sep: String = "\n", end: String = "") {
    forEach { print("$it$sep") }
    print(end)
}

fun <T> Array<T>.printArray(sep: String = "\n", end: String = "") {
    forEach { print("$it$sep") }
    print(end)
}

fun <T> Iterable<T>.printIterable(sep: String = " ", end: String = "\n") {
    forEach { print("$it$sep") }
    print(end)
}