package org.csystem

class Time {
    var hour: Int = 0
        set(value) {
            if (value < 0 || value > 59)
                throw IllegalArgumentException("Invalid value")

            field = value
            //...
        }

    //...
}

