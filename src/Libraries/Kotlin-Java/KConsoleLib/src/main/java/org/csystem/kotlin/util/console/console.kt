package org.csystem.kotlin.util.console

internal fun readWithPrompt(prompt: String): String {
    print(prompt)

    return readln()
}

fun readInt(prompt: String, errorPrompt: String = "", end: String = ""): Int {
    while (true) {
        try {
            return readWithPrompt("$prompt$end").toInt()
        }
        catch (e: NumberFormatException) {
            print("$errorPrompt$end")
        }
    }
}

fun readLong(prompt: String, errorPrompt: String = ""): Long {
    while (true) {
        try {
            return readWithPrompt(prompt).toLong()
        }
        catch (e: NumberFormatException) {
            print(errorPrompt)
        }
    }
}

fun readDouble(prompt: String, errorPrompt: String = ""): Double {
    while (true) {
        try {
            return readWithPrompt(prompt).toDouble()
        }
        catch (e: NumberFormatException) {
            print(errorPrompt)
        }
    }
}

fun readShort(prompt: String, errorPrompt: String = ""): Short {
    while (true) {
        try {
            return readWithPrompt(prompt).toShort()
        }
        catch (e: NumberFormatException) {
            print(errorPrompt)
        }
    }
}

fun readByte(prompt: String, errorPrompt: String): Byte {
    while (true) {
        try {
            return readWithPrompt(prompt).toByte()
        }
        catch (e: NumberFormatException) {
            print(errorPrompt)
        }
    }
}

fun readFloat(prompt: String, errorPrompt: String = ""): Float {
    while (true) {
        try {
            return readWithPrompt(prompt).toFloat()
        }
        catch (e: NumberFormatException) {
            print(errorPrompt)
        }
    }
}

fun readBoolean(prompt: String, errorPrompt: String = ""): Boolean {
    while (true) {
        val result = readWithPrompt(prompt).toBoolean()

        if (!result && !prompt.equals("false", true)) {
            println(errorPrompt)
            continue
        }

        return result;
    }
}

fun readString(prompt: String) = readWithPrompt(prompt)

fun IntArray.printArray(n: Int = -1, sep: String = " ", end: String = "\n") {
    val fmt = "%%0%dd%%s".format(if (n < 1) 1 else n)

    for (v in this)
        print(fmt.format(v, sep))

    print(end)
}

fun LongArray.printArray(n: Int = -1, sep: String = " ", end: String = "\n") {
    val fmt = "%%0%dd%%s".format(if (n < 1) 1 else n)

    for (v in this)
        print(fmt.format(v, sep))

    print(end)
}

fun DoubleArray.printArray(n: Int = -1, sep: String = "\n", end: String = "") {
    val fmt = if (n < 1) "%f%s" else "%%.%df%%s".format(n)

    for (v in this)
        print(fmt.format(v, sep))

    print(end)
}

fun ShortArray.printArray( n: Int = -1, sep: String = " ", end: String = "\n") {
    val fmt = "%%0%dd%%s".format(if (n < 1) 1 else n)

    for (v in this)
        print(fmt.format(v, sep))

    print(end)
}


fun ByteArray.printArray(n: Int = -1, sep: String = " ", end: String = "\n") {
    val fmt = "%%0%dd%%s".format(if (n < 1) 1 else n)

    for (v in this)
        print(fmt.format(v, sep))

    print(end)
}

fun FloatArray.printArray(n: Int = -1, sep: String = "\n", end: String = "") {
    val fmt = if (n < 1) "%f%s" else "%%.%df%%s".format(n)

    for (v in this)
        print(fmt.format(v, sep))

    print(end)
}

fun BooleanArray.printArray(sep: String = "\n", end: String = "") {
    for (v in this)
        print("$v$sep")

    print(end)
}

fun CharArray.printArray(sep: String = "\n", end: String = "") {
    for (v in this)
        print("$v$sep")

    print(end)
}

fun <T> Array<T>.printArray(sep: String = "\n", end: String = "") {
    for (v in this)
        print("$v$sep")

    print(end)
}

