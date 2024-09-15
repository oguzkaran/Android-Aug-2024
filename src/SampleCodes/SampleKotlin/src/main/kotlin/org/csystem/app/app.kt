package org.csystem.app

import kotlin.random.Random

fun main() {
    while (true) {
        val a = generateA()

        doWork(a)
        Thread.sleep(500)
    }
}

fun generateA(random: Random = Random): A {
    return when (random.nextInt(1, 6)) {
        1 -> A(10)
        2 -> B(20, 23)
        3 -> C(30, 30)
        4 -> D(40, 20, 30)
        else -> E(50)
    }
}

fun doWork(a: A) {
    println(a.javaClass.name)
    println("x = ${a.x}")
    a.doSomething()
    println("-----------------------------")
}

open class E(x: Int) : A(10) {
    //...
}

open class D(x: Int, var y: Int, var z: Int) : A(x) {
    //...
}

open class C(x: Int, var z: Int) : B(x, 10) {
    //...
}

open class B(x: Int, var y: Int) : A(x) {
    //...
}

open class A(var x: Int) {
    //...
    fun doSomething() {
        println("A.doSomething")
    }
}