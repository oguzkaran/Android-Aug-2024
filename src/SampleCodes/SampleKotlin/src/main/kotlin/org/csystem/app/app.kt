package org.csystem.app

fun main() {
    val (id, name) = Person(1, "ali")

    print("$id, $name")
}

open class Person(var id: Int, var name: String) {
    operator fun component1() = id
    operator fun component2() = name
    //...
}