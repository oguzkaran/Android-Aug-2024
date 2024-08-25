package org.csystem.app;

fun main() {
    print("Birinci sayıyı giriniz:")
    val a = readln().toInt()

    print("İkinci sayıyı giriniz:")
    val b = readln().toInt()

    println(a === b) //Deprecated
    println(a == b)
}