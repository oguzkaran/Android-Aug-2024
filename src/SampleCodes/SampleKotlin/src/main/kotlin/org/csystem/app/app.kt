package org.csystem.app

fun main() {
    val driver = Driver(/*...*/)
    val taxi = Taxi(driver/*...*/)

    //...

    val client = Client(/*...*/)

    taxi.take(client)

    val client2 = Client(/*...*/)

    taxi.take(client2)

    //...
}

class Taxi(var driver: Driver /*...*/) {
    //...

    fun take(c: Client) {
        //...
    }
}

class Client {
    //...
}

class Driver {
    //...
}