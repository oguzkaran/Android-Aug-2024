package org.csystem.app

fun main() {
    print("Bir karakter giriniz:")
    val ch = readln()[0]

    println(ch.uppercase())
    println(ch.uppercaseChar())
    println(ch.lowercase())
    println(ch.lowercaseChar())
}