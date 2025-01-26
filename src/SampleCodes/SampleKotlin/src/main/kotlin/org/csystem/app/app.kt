package org.csystem.app

import org.csystem.kotlin.util.console.readChar
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess

class CounterApplication(seconds: Long = 0) {
    private var mSeconds: Long = seconds
    private var mScheduledFuture: ScheduledFuture<*>? = null
    private var mFlag  = false
    private var mScheduler: ScheduledExecutorService = Executors.newScheduledThreadPool(1)

    private fun clearScreen() {
        for (i in 1..26)
            println()
    }

    private fun printDuration() {
        val hour = mSeconds / 60 / 60
        val minute = mSeconds / 60 % 60
        val second = mSeconds % 60

        print("$hour:$minute:$second\r")
    }

    private fun readCharProc() {
        while (true) {
            val c = readChar("")

            clearScreen()

            if (c == 'b')
                break

            if (c == 'e') {
                mScheduledFuture?.cancel(false)
                mScheduledFuture = null
                mFlag = false
                continue
            }

            if (c == 'q')
                exitProcess(0)
        }
    }

    fun run() {
        while (true) {
            readCharProc()

            if (mFlag)
                continue


            println("Press 'e' to stop, 'q' to quit")
            mScheduledFuture = mScheduler.scheduleAtFixedRate({  printDuration(); ++mSeconds} , 0, 1, TimeUnit.SECONDS)
            mFlag = true
        }
    }
}

private fun runApplication() {
    CounterApplication().run()
}

fun main() = runApplication()