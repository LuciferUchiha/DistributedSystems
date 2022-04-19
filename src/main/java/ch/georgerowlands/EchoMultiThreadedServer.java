package ch.georgerowlands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoMultiThreadedServer {
    public static void main(String args[]) throws IOException {
        final int serverPort = 1234;
        try (ServerSocket server = new ServerSocket(serverPort)) {
            System.out.println("Startet Echo Server at " + server.getLocalSocketAddress());
            while (true) {
                Socket s = server.accept();
                Thread t = new Thread(() -> {
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                         PrintWriter out = new PrintWriter(s.getOutputStream(), true)
                    ) {
                        final String clientAddress = s.getInetAddress().getHostAddress() + ":" + s.getPort();
                        System.out.println("connection from " + clientAddress);
                        in.lines().forEach(line -> {
                            out.println(line);
                            System.out.println(clientAddress + ">> " + line);
                        });
                        System.out.println("done serving " + s);
                        s.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                });
                t.start();
            }
        }
    }
}