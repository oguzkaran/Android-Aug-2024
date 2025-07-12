package org.csystem.app.imageprocessing.client.runner;

import org.csystem.app.imageprocessing.client.Client;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientRunner implements ApplicationRunner {
    private final Client m_client;

    public ClientRunner(Client client)
    {
        m_client = client;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        new Thread(m_client::run).start();
    }
}
