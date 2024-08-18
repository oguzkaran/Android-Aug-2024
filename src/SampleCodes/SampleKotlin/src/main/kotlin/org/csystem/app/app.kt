package org.csystem.app

fun main() = runIsArmstrongTest()

fun runIsArmstrongTest() {
    for (a in -10..99999)
        if (isArmstrong(a))
            println(a)

    println("Tekrar yapıyor musunuz?")
}

fun isArmstrong(a: Int): Boolean {
    TODO("Test if a is an Armstrong number")
}