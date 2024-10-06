package org.csystem.kotlin.util.string

import kotlin.random.Random

fun String.changeCase(): String {
    val sb = StringBuilder(this)

    for (i in this.indices)
        sb[i] = when {
            sb[i].isUpperCase() -> sb[i].lowercaseChar()
            else -> sb[i].uppercaseChar()
        }

    return sb.toString()
}

fun String.capitalize() = if (this != "") this[0].uppercase() + this.substring(1).lowercase() else ""

fun String.countString(s: String, ignoreCase: Boolean = false): Int {
    var count = 0

    var index = -1

    while (true) {
        index = this.indexOf(s, index + 1, ignoreCase)

        if (index == -1)
            break

        ++count
    }

    return count
}


fun String.isIsogram(alphabet: String, ignoreCase: Boolean = false): Boolean {
    for (c in alphabet) {
        val i = this.indexOf(c, 0, ignoreCase)

        if (i == -1)
            return false

        if (this.indexOf(c, i + 1, ignoreCase) != -1)
            return false
    }

    return true
}

fun String.isIsogramEN() = this.lowercase().isIsogram("abcdefghijklmnopqrstuvwxyz")

fun String.isIsogramTR() = this.lowercase().isIsogram("abcçdefgğhıijklmnoöprsştuüvyz")


fun String.isPangram(alphabet: String, ignoreCase: Boolean = false): Boolean {
    for (c in alphabet)
        if (!this.contains(c, ignoreCase))
            return false

    return true
}

fun String.isPangramEN() = this.lowercase().isPangram("abcdefghijklmnopqrstuvwxyz")


fun String.isPangramTR() = this.lowercase().isPangram("abcçdefgğhıijklmnoöprsştuüvyz")

fun Random.randomText(count: Int, sourceText: String): String {
    val sb = StringBuilder();

    for (i in 1..count)
        sb.append(sourceText[this.nextInt(sourceText.length)])

    return sb.toString();
}

fun Random.randomTextEN(count: Int) =
    randomText(count, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz")

fun Random.randomTextTR(count: Int, random: Random = Random) =
    randomText(count, "ABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVYZabcçdefgğhıijklmnoöprsştuüvyz")

fun Random.randomTexts(n: Int, count: Int, sourceText: String) = Array(n) { randomText(count, sourceText) }

fun Random.randomTexts(n: Int, origin: Int, bound: Int, sourceText: String) =
    Array(n) { randomText(nextInt(origin, bound), sourceText) }

fun Random.randomTextsEN(n: Int, count: Int) =
    randomTexts(n, count, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz")

fun Random.randomTextsEN(n: Int, origin: Int, bound: Int) = Array(n) {randomTextEN(nextInt(origin, bound))}

fun Random.randomTextsTR(n: Int, count: Int) =
    randomTexts(n, count, "ABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVYZabcçdefgğhıijklmnoöprsştuüvyz")

fun Random.randomTextsTR(n: Int, origin: Int, bound: Int) = Array(n) {randomTextTR(nextInt(origin, bound))}
