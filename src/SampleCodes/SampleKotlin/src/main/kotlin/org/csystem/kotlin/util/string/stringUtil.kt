package org.csystem.kotlin.util.string

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
