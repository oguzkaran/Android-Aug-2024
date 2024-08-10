/*----------------------------------------------------------------------------------------------------------------------
    Kotlin 1.6 ile birlikte klavyeden (aslında stdin'dan) enter basılana kadar girilen yazıyı bir String olarak okuyan
    readln fonkiyonu eklenmiştir
----------------------------------------------------------------------------------------------------------------------*/
package org.csystem.app

fun main() {
    print("Bir sayı giriniz:")
    val a = readln().toInt()

    println("${a * a}")
}
