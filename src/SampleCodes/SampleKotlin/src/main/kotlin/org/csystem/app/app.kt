package org.csystem.app

fun main() {
    print("Bir sayı giriniz:")
    val a = readln().toShort()
    val b = a.toInt()

    println("a = %d".format(a))
    println("a = 0x%04X".format(a))
    println("----------------------------------------------")
    println("b = %d".format(b))
    println("b = 0x%08X".format(b))
}