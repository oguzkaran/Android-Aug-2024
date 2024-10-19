package org.csystem.app

import org.csystem.data.processing.test.loadProductsFromFileAsArray
import org.csystem.kotlin.util.console.commandline.lengthEquals

fun main(args: Array<String>) {
    try {
        lengthEquals(1, args.size, "Geçersiz komut satırı argümanları")
        val products = loadProductsFromFileAsArray(args[0])

        if (products.none { it.stock <= 0 })
            println("Her ürün stokta var")
        else {
            println("Stokta olmayan ürünler:")
            products.filter { it.stock <= 0 }.map { it.name }.map { it.uppercase() }.forEach(::println)
        }
    } catch (ex: Throwable) {
        println(ex.message)
    }
}