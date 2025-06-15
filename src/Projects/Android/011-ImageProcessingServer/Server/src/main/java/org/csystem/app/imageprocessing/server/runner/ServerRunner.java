package org.csystem.app.imageprocessing.server.runner;

import org.csystem.app.imageprocessing.server.Server;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

@Component
public class ServerRunner implements ApplicationRunner {
    private final ExecutorService m_executorService;
    private final Server m_server;

    public ServerRunner(ExecutorService executorService, Server server)
    {
        m_executorService = executorService;
        m_server = server;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        m_executorService.execute(m_server::start);
    }
}
