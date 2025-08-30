package org.csystem.app.random.text.server

import com.karandev.util.net.TcpUtil
import org.csystem.app.random.text.server.constant.*
import org.csystem.kotlin.util.string.randomTextEN
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.ExecutorService
import kotlin.random.Random

@Component
class Server(private val mThreadPool: ExecutorService) {
    private val mLogger = LoggerFactory.getLogger(Server::class.java)

    @Value("\${app.port}")
    private var mPort = 0

    @Value("\${app.text.length.max}")
    private var mTextMaxLength = 0

    private fun handleClient(socket: Socket) {
        socket.use {s ->
            try {
                s.soTimeout = SOCKET_TIMEOUT
                mLogger.info("Client connected: {}:{}", socket.inetAddress.hostAddress, socket.port)
                val count = TcpUtil.receiveLong(s)
                val min = TcpUtil.receiveInt(s)
                val max = TcpUtil.receiveInt(s)

                if (max - min > mTextMaxLength) {
                    TcpUtil.sendInt(s, MAX_LENGTH_ERROR)
                    return
                }

                if (max < min) {
                    TcpUtil.sendInt(s, MAX_MIN_ERROR)
                    return
                }

                if (count <= 0) {
                    TcpUtil.sendInt(s, COUNT_NOT_POSITIVE_ERROR)
                    return
                }

                TcpUtil.sendInt(s, SUCCESS)
                generateSequence(0) {it + 1}.takeWhile { it < count }
                    .forEach { _ -> TcpUtil.sendStringViaLength(s, Random.randomTextEN(Random.nextInt(min, max + 1))) }
            } catch (e: Exception) {
                mLogger.error("Client disconnected:{}", e.message)
            }
        }
    }

    fun start() {
        try {
            mLogger.info("Starting server on port $mPort")
            ServerSocket(mPort).use {
                while (true) {
                    val clientSocket = it.accept()

                    mThreadPool.execute {handleClient(clientSocket) }
                }
            }
        } catch (e: Exception) {
            mLogger.error("Error occurred: {}", e.message)
        }
    }
}