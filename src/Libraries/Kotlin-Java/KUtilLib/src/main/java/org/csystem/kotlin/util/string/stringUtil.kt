package org.csystem.kotlin.util.string

import kotlin.random.Random

private const val LETTERS_EN = "abcdefghijklmnopqrstuvwxyz"
private const val LETTERS_TR = "abcçdefgğhıijklmnoöprsştuüvyz"
private const val CAPITAL_LETTERS_EN = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
private const val CAPITAL_LETTERS_TR = "ABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVYZ"
private const val ALL_LETTERS_EN = LETTERS_EN + CAPITAL_LETTERS_EN
private const val ALL_LETTERS_TR = LETTERS_TR + CAPITAL_LETTERS_TR

/**
 * Extension function to change the case of each character in the string.
 * Uppercase characters are converted to lowercase and vice versa.
 *
 * @return A new string with the case of each character changed.
 */
fun String.changeCase(): String {
    val sb = StringBuilder(this)

    for (i in this.indices)
        sb[i] = when {
            sb[i].isUpperCase() -> sb[i].lowercaseChar()
            else -> sb[i].uppercaseChar()
        }

    return sb.toString()
}

/**
 * Extension function to capitalize the first character of the string.
 *
 * @return A new string with the first character capitalized.
 */
fun String.capitalize() = if (this != "") this[0].uppercase() + this.substring(1).lowercase() else ""

/**
 * Extension function to count the occurrences of a substring in the string.
 *
 * @param s The substring to count.
 * @param ignoreCase Whether to ignore case when counting.
 * @return The number of occurrences of the substring.
 */
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

/**
 * Extension function to check if the string is an isogram.
 * An isogram is a word with no repeating letters.
 *
 * @param alphabet The alphabet to use for checking.
 * @param ignoreCase Whether to ignore case when checking.
 * @return True if the string is an isogram, false otherwise.
 */
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

/**
 * Extension function to check if the string is an isogram using the English alphabet.
 *
 * @return True if the string is an isogram, false otherwise.
 */
fun String.isIsogramEN() = this.lowercase().isIsogram(LETTERS_EN)

/**
 * Extension function to check if the string is an isogram using the Turkish alphabet.
 *
 * @return True if the string is an isogram, false otherwise.
 */
fun String.isIsogramTR() = this.lowercase().isIsogram(LETTERS_TR)

/**
 * Extension function to check if the string is a pangram.
 * A pangram is a sentence containing every letter of the alphabet at least once.
 *
 * @param alphabet The alphabet to use for checking.
 * @param ignoreCase Whether to ignore case when checking.
 * @return True if the string is a pangram, false otherwise.
 */
fun String.isPangram(alphabet: String, ignoreCase: Boolean = false): Boolean {
    for (c in alphabet)
        if (!this.contains(c, ignoreCase))
            return false

    return true
}

/**
 * Extension function to check if the string is a pangram using the English alphabet.
 *
 * @return True if the string is a pangram, false otherwise.
 */
fun String.isPangramEN() = this.lowercase().isPangram(LETTERS_EN)

/**
 * Extension function to check if the string is a pangram using the Turkish alphabet.
 *
 * @return True if the string is a pangram, false otherwise.
 */
fun String.isPangramTR() = this.lowercase().isPangram(LETTERS_TR)

/**
 * Extension function to generate a random text of a given length from a source text.
 *
 * @param count The length of the random text.
 * @param sourceText The source text to generate the random text from.
 * @return A random text of the given length.
 */
fun Random.randomText(count: Int, sourceText: String): String {
    val sb = StringBuilder();

    for (i in 1..count)
        sb.append(sourceText[this.nextInt(sourceText.length)])

    return sb.toString();
}

/**
 * Extension function to generate a random text of a given length using the English alphabet.
 *
 * @param count The length of the random text.
 * @return A random text of the given length.
 */
fun Random.randomTextEN(count: Int) = randomText(count, ALL_LETTERS_EN)

/**
 * Extension function to generate a random text of a given length using the Turkish alphabet.
 *
 * @param count The length of the random text.
 * @param random The random instance to use.
 * @return A random text of the given length.
 */
fun Random.randomTextTR(count: Int, random: Random = Random) = randomText(count, ALL_LETTERS_TR)

/**
 * Extension function to generate an array of random texts.
 *
 * @param n The number of random texts to generate.
 * @param count The length of each random text.
 * @param sourceText The source text to generate the random texts from.
 * @return An array of random texts.
 */
fun Random.randomTexts(n: Int, count: Int, sourceText: String) = Array(n) { randomText(count, sourceText) }

/**
 * Extension function to generate an array of random texts with varying lengths.
 *
 * @param n The number of random texts to generate.
 * @param origin The minimum length of each random text.
 * @param bound The maximum length of each random text.
 * @param sourceText The source text to generate the random texts from.
 * @return An array of random texts.
 */
fun Random.randomTexts(n: Int, origin: Int, bound: Int, sourceText: String) =
    Array(n) { randomText(nextInt(origin, bound), sourceText) }

/**
 * Extension function to generate an array of random texts using the English alphabet.
 *
 * @param n The number of random texts to generate.
 * @param count The length of each random text.
 * @return An array of random texts.
 */
fun Random.randomTextsEN(n: Int, count: Int) = randomTexts(n, count, ALL_LETTERS_EN)

/**
 * Extension function to generate an array of random texts with varying lengths using the English alphabet.
 *
 * @param n The number of random texts to generate.
 * @param origin The minimum length of each random text.
 * @param bound The maximum length of each random text.
 * @return An array of random texts.
 */
fun Random.randomTextsEN(n: Int, origin: Int, bound: Int) = Array(n) {randomTextEN(nextInt(origin, bound))}

/**
 * Extension function to generate an array of random texts using the Turkish alphabet.
 *
 * @param n The number of random texts to generate.
 * @param count The length of each random text.
 * @return An array of random texts.
 */
fun Random.randomTextsTR(n: Int, count: Int) = randomTexts(n, count, ALL_LETTERS_TR)

/**
 * Extension function to generate an array of random texts with varying lengths using the Turkish alphabet.
 *
 * @param n The number of random texts to generate.
 * @param origin The minimum length of each random text.
 * @param bound The maximum length of each random text.
 * @return An array of random texts.
 */
fun Random.randomTextsTR(n: Int, origin: Int, bound: Int) = Array(n) {randomTextTR(nextInt(origin, bound))}