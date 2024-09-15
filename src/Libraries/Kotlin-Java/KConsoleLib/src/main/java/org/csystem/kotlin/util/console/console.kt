package org.csystem.kotlin.util.console

internal fun readWithPrompt(prompt: String): String {
    print(prompt)

    return readln()
}

fun readInt(prompt: String) = readWithPrompt(prompt).toInt()

fun readLong(prompt: String) = readWithPrompt(prompt).toLong()

fun readDouble(prompt: String) = readWithPrompt(prompt).toDouble()

fun readShort(prompt: String) = readWithPrompt(prompt).toShort()

fun readByte(prompt: String) = readWithPrompt(prompt).toByte()

fun readFloat(prompt: String) = readWithPrompt(prompt).toFloat()

fun readBoolean(prompt: String) = readWithPrompt(prompt).toBoolean()

fun readString(prompt: String) = readWithPrompt(prompt)

fun printArray(a: IntArray, n: Int = -1, sep: String = " ", end: String = "\n") {
    val fmt = "%%0%dd%%s".format(if (n < 1) 1 else n)

    for (v in a)
        print(fmt.format(v, sep))

    print(end)
}

fun printArray(a: LongArray, n: Int = -1, sep: String = " ", end: String = "\n") {
    val fmt = "%%0%dd%%s".format(if (n < 1) 1 else n)

    for (v in a)
        print(fmt.format(v, sep))

    print(end)
}

fun printArray(a: DoubleArray, n: Int = -1, sep: String = "\n", end: String = "") {
    val fmt = if (n < 1) "%f%s" else "%%.%df%%s".format(n)

    for (v in a)
        print(fmt.format(v, sep))

    print(end)
}

fun printArray(a: ShortArray, n: Int = -1, sep: String = " ", end: String = "\n") {
    val fmt = "%%0%dd%%s".format(if (n < 1) 1 else n)

    for (v in a)
        print(fmt.format(v, sep))

    print(end)
}


fun printArray(a: ByteArray, n: Int = -1, sep: String = " ", end: String = "\n") {
    val fmt = "%%0%dd%%s".format(if (n < 1) 1 else n)

    for (v in a)
        print(fmt.format(v, sep))

    print(end)
}

fun printArray(a: FloatArray, n: Int = -1, sep: String = "\n", end: String = "") {
    val fmt = if (n < 1) "%f%s" else "%%.%df%%s".format(n)

    for (v in a)
        print(fmt.format(v, sep))

    print(end)
}

fun printArray(a: BooleanArray, sep: String = "\n", end: String = "") {
    for (v in a)
        print("$v$sep")

    print(end)
}

fun printArray(a: CharArray, sep: String = "\n", end: String = "") {
    for (v in a)
        print("$v$sep")

    print(end)
}

