package org.csystem.app.android.randomusers.databinding.converter

import androidx.databinding.InverseMethod

object CountToStringConverter {
    fun toInt(str: String) : Int {
        var result = 10

        try {
            result = str.toInt()
        }
        catch (ex: NumberFormatException) {

        }

        return result
    }

    @InverseMethod("toInt")
    fun toString(count: Int) = count.toString()
}