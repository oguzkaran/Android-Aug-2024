package org.csystem.app.random.text.client

import com.karandev.util.net.TcpUtil
import org.csystem.kotlin.util.console.readInt
import org.csystem.kotlin.util.console.readLong
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.net.Socket
import java.util.concurrent.ExecutorService

@Component
class Client(private val mThreadPool: ExecutorService) {
    private val mLogger = LoggerFactory.getLogger(Client::class.java)

    @Value("\${app.server.port}")
    private val mServerPort = 0

    @Value("\${app.server.host}")
    private val mServerHost = ""

    fun start() {
        try {
            val count = readLong("Input count:", "Invalid value!...")
            val min = readInt("Input min:", "Invalid value!...")
            val max = readInt("Input max:", "Invalid value!...")

            Socket(mServerHost, mServerPort).use {s ->
                TcpUtil.sendLong(s, count)
                TcpUtil.sendInt(s, min)
                TcpUtil.sendInt(s, max)

                val statusCode = TcpUtil.receiveInt(s)
                mLogger.info("Result:{}", statusCode)

                if (statusCode == 0)
                    generateSequence(0) {it + 1}.takeWhile { it < count }.forEach { _ -> println(TcpUtil.receiveStringViaLength(s)) }
            }
        } catch (e: Exception) {
            mLogger.error("Error occurred: {}", e.message)
        }
    }
}