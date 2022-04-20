package ch.georgerowlands.webservers.sun;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class SunHttpServer {
    public static void main(String[] args) {
        final String host = "localhost";
        final int port = 1234;
        try {
            // backlog 0 = system default
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/date", new DateHandler());
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
