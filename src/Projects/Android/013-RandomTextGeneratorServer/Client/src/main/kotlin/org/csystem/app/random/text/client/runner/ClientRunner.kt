package org.csystem.app.random.text.client.runner

import org.csystem.app.random.text.client.Client
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.util.concurrent.ExecutorService

@Component
class ClientRunner(private val mThreadPool: ExecutorService, private val mClient: Client) : CommandLineRunner {
    override fun run(vararg args: String?) {
        mThreadPool.execute { mClient.start() }
        mThreadPool.shutdown()
    }
}