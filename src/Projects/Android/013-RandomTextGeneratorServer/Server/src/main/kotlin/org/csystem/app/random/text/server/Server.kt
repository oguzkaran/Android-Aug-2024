package org.csystem.app.random.text.server

import com.karandev.util.net.TcpUtil
import org.csystem.app.random.text.server.constant.*
import org.csystem.kotlin.util.string.randomTextEN
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import java.net.ServerSocket
import java.net.Socket
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.ExecutorService
import kotlin.random.Random

@Component
class Server(private val mApplicationContext: ApplicationContext,
             private val mThreadPool: ExecutorService,
             private val mDateTimeFormatter: DateTimeFormatter) {
    private val mLogger = LoggerFactory.getLogger(Server::class.java)

    @Value("\${app.port}")
    private var mPort = 0

    @Value("\${app.text.length.max}")
    private var mTextMaxLength = 0

    @Value("\${app.backup.endpoint}")
    private var backupEndpoint: String = ""

    private fun sendTestCallback(socket: Socket, min: Int, max: Int) {
        val now = mApplicationContext.getBean(LocalDateTime::class.java)
        val textInfo = "${Random.randomTextEN(Random.nextInt(min, max + 1))}@${mDateTimeFormatter.format(now)}"

        TcpUtil.sendStringViaLength(socket, textInfo)
    }

    private fun validateParameters(socket: Socket, count:Long, minLength: Int, maxLength: Int): Boolean {
        if (maxLength - minLength > mTextMaxLength) {
            TcpUtil.sendInt(socket, MAX_LENGTH_ERROR)
            return false
        }

        if (maxLength < minLength) {
            TcpUtil.sendInt(socket, MAX_MIN_ERROR)
            return false
        }

        if (count <= 0) {
            TcpUtil.sendInt(socket, COUNT_NOT_POSITIVE_ERROR)
            return false
        }

        return true
    }

    private fun doRandomGenerator(socket: Socket) {
        val count = TcpUtil.receiveLong(socket)
        val minLength = TcpUtil.receiveInt(socket)
        val maxLength = TcpUtil.receiveInt(socket)

        if (!validateParameters(socket, count, minLength, maxLength))
            return

        TcpUtil.sendInt(socket, SUCCESS)
        generateSequence(0) {it + 1}.takeWhile { it < count }.forEach { _ -> sendTestCallback(socket, minLength, maxLength) }
    }

    private fun doBackupEndPoint(socket: Socket) {
        TcpUtil.sendStringViaLength(socket, backupEndpoint)
    }

    private fun handleClient(socket: Socket) {
        socket.use {s ->
            try {
                s.soTimeout = SOCKET_TIMEOUT
                val now = mApplicationContext.getBean(LocalDateTime::class.java)
                mLogger.info("{}:{} connected at {}: ", socket.inetAddress.hostAddress, socket.port, mDateTimeFormatter.format(now))
                val option = TcpUtil.receiveInt(s)

                when (option) {
                    RANDOM_GENERATOR_CODE -> doRandomGenerator(socket)
                    BACKUP_ENDPOINT_CODE -> doBackupEndPoint(socket)
                }
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