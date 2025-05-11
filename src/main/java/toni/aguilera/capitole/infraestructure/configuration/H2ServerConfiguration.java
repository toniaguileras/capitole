package toni.aguilera.capitole.infraestructure.configuration;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.h2.tools.Server;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
public class H2ServerConfiguration {

    private Server tcpServer;
    private Server webServer;

    @PostConstruct
    public void startServers() throws SQLException {
        this.tcpServer = Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092").start();
        this.webServer = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
    }

    @PreDestroy
    public void stopServers() {
        if (this.tcpServer != null) this.tcpServer.stop();
        if (this.webServer != null) this.webServer.stop();
    }
}