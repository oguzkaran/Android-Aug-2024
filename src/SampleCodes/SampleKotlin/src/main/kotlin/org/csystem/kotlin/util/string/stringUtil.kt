package org.csystem.kotlin.util.string

fun changeCase(s: String): String {
    val sb = StringBuilder(s)

    for (i in s.indices)
        sb[i] = when {
            sb[i].isUpperCase() -> sb[i].lowercaseChar()
            else -> sb[i].uppercaseChar()
        }

    return sb.toString()
}

fun capitalize(s: String) = if (s != "") s[0].uppercase() + s.substring(1).lowercase() else ""

fun countString(s1: String, s2: String, ignoreCase: Boolean = false): Int {
    var count = 0

    var index = -1

    while (true) {
        index = s1.indexOf(s2, index + 1, ignoreCase)

        if (index == -1)
            break

        ++count
    }

    return count
}


fun isIsogram(s: String, alphabet: String, ignoreCase: Boolean = false): Boolean {
    TODO()
}

fun isIsogramEN(s: String) = isPangram(s.lowercase(), "abcdefghijklmnopqrstuvwxyz")

fun isIsogramTR(s: String) = isPangram(s.lowercase(), "abcçdefgğhıijklmnoöprsştuüvyz")


fun isPangram(s: String, alphabet: String, ignoreCase: Boolean = false): Boolean {
    for (c in alphabet)
        if (!s.contains(c, ignoreCase))
            return false

    return true
}

fun isPangramEN(s: String) = isPangram(s.lowercase(), "abcdefghijklmnopqrstuvwxyz")


fun isPangramTR(s: String) = isPangram(s.lowercase(), "abcçdefgğhıijklmnoöprsştuüvyz")


