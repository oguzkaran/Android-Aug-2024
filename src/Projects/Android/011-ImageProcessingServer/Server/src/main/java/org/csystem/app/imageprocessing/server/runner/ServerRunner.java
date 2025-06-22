package org.csystem.app.imageprocessing.server.runner;

import org.csystem.app.imageprocessing.server.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;

@Component
public class ServerRunner implements ApplicationRunner {
    private final ExecutorService m_executorService;
    private final Server m_server;

    @Value("${app.image.directory}")
    private String m_imagesPath;

    public ServerRunner(ExecutorService executorService, Server server)
    {
        m_executorService = executorService;
        m_server = server;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        Files.createDirectories(Path.of(m_imagesPath));
        m_executorService.execute(m_server::start);
    }
}
