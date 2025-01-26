package org.csystem.kotlin.util.console

object Console {
    internal fun readWithPrompt(prompt: String): String {
        print(prompt)

        return readln()
    }

    @JvmStatic
    fun readInt(prompt: String, errorPrompt: String = "", end: String = ""): Int {
        while (true) {
            try {
                return readWithPrompt("$prompt$end").toInt()
            } catch (e: NumberFormatException) {
                print("$errorPrompt$end")
            }
        }
    }

    @JvmStatic
    fun readInt(prompt: String, errorPrompt: String = "") = readInt(prompt, errorPrompt, "")

    @JvmStatic
    fun readInt(prompt: String) = readInt(prompt, "")

    @JvmStatic
    fun readLong(prompt: String, errorPrompt: String = "", end: String = ""): Long {
        while (true) {
            try {
                return readWithPrompt("$prompt$end").toLong()
            } catch (e: NumberFormatException) {
                print("$errorPrompt$end")
            }
        }
    }

    @JvmStatic
    fun readDouble(prompt: String, errorPrompt: String = "", end: String): Double {
        while (true) {
            try {
                return readWithPrompt("$prompt$end").toDouble()
            } catch (e: NumberFormatException) {
                print("$errorPrompt$end")
            }
        }
    }

    @JvmStatic
    fun readShort(prompt: String, errorPrompt: String = ""): Short {
        while (true) {
            try {
                return readWithPrompt(prompt).toShort()
            } catch (e: NumberFormatException) {
                print(errorPrompt)
            }
        }
    }

    @JvmStatic
    fun readByte(prompt: String, errorPrompt: String): Byte {
        while (true) {
            try {
                return readWithPrompt(prompt).toByte()
            } catch (e: NumberFormatException) {
                print(errorPrompt)
            }
        }
    }

    @JvmStatic
    fun readFloat(prompt: String, errorPrompt: String = ""): Float {
        while (true) {
            try {
                return readWithPrompt(prompt).toFloat()
            } catch (e: NumberFormatException) {
                print(errorPrompt)
            }
        }
    }

    @JvmStatic
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

    @JvmStatic
    fun readChar(prompt: String, errorPrompt: String = "", end: String = ""): Char {
        while (true) {
            val str = readString("$prompt$end")

            if (str.length == 1)
                return str[0]

            print("$errorPrompt$end")
        }
    }

    @JvmStatic
    fun readString(prompt: String) = readWithPrompt(prompt)

    @JvmStatic
    fun IntArray.printArray(n: Int = -1, sep: String = " ", end: String = "\n") {
        val fmt = "%%0%dd%%s".format(if (n < 1) 1 else n)

        for (v in this)
            print(fmt.format(v, sep))

        print(end)
    }

    @JvmStatic
    fun LongArray.printArray(n: Int = -1, sep: String = " ", end: String = "\n") {
        val fmt = "%%0%dd%%s".format(if (n < 1) 1 else n)

        for (v in this)
            print(fmt.format(v, sep))

        print(end)
    }

    @JvmStatic
    fun DoubleArray.printArray(n: Int = -1, sep: String = "\n", end: String = "") {
        val fmt = if (n < 1) "%f%s" else "%%.%df%%s".format(n)

        for (v in this)
            print(fmt.format(v, sep))

        print(end)
    }

    @JvmStatic
    fun ShortArray.printArray(n: Int = -1, sep: String = " ", end: String = "\n") {
        val fmt = "%%0%dd%%s".format(if (n < 1) 1 else n)

        for (v in this)
            print(fmt.format(v, sep))

        print(end)
    }


    @JvmStatic
    fun ByteArray.printArray(n: Int = -1, sep: String = " ", end: String = "\n") {
        val fmt = "%%0%dd%%s".format(if (n < 1) 1 else n)

        for (v in this)
            print(fmt.format(v, sep))

        print(end)
    }

    @JvmStatic
    fun FloatArray.printArray(n: Int = -1, sep: String = "\n", end: String = "") {
        val fmt = if (n < 1) "%f%s" else "%%.%df%%s".format(n)

        for (v in this)
            print(fmt.format(v, sep))

        print(end)
    }

    @JvmStatic
    fun BooleanArray.printArray(sep: String = "\n", end: String = "") {
        for (v in this)
            print("$v$sep")

        print(end)
    }

    @JvmStatic
    fun CharArray.printArray(sep: String = "\n", end: String = "") {
        for (v in this)
            print("$v$sep")

        print(end)
    }

    @JvmStatic
    fun <T> Array<T>.printArray(sep: String = "\n", end: String = "") {
        for (v in this)
            print("$v$sep")

        print(end)
    }
}

