package org.csystem.kotlin.util.console.commandline

import kotlin.system.exitProcess

fun lengthEquals(len: Int, argsLen: Int, errorMessage: String, exitCode: Int = 1) {
    if (argsLen != len) {
        System.err.println(errorMessage)
        exitProcess(exitCode)
    }
}

fun lengthGreater(len: Int, argsLen: Int, errorMessage: String, exitCode: Int = 1) {
    if (argsLen <= len) {
        System.err.println(errorMessage)
        exitProcess(exitCode)
    }
}


fun lengthGreaterOrEquals(len: Int, argsLen: Int, errorMessage: String, exitCode: Int = 1) {
    if (argsLen < len) {
        System.err.println(errorMessage)
        exitProcess(exitCode)
    }
}

//...

