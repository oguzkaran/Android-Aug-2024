package org.csystem.app.random.text.server.runner

import org.csystem.app.random.text.server.Server
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.util.concurrent.ExecutorService

@Component
class ServerRunner(private val mThreadPool: ExecutorService, private val mServer: Server) : CommandLineRunner {
    override fun run(vararg args: String?) {
        mThreadPool.execute { mServer.start() }
    }
}