package org.csystem.app

import org.csystem.kotlin.util.console.readString
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

private fun createTimerTask(formatter: DateTimeFormatter): TimerTask {
    return object : TimerTask() {
        override fun run() {
            print("%s\r".format(formatter.format(LocalDateTime.now())))
        }
    }
}

fun main() {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy kk:mm:ss")
    val timer = Timer()

    timer.scheduleAtFixedRate(createTimerTask(formatter), 0, 1000)
    readString("Çıkmak için enter tuşuna basınız\n");
    timer.cancel()
}