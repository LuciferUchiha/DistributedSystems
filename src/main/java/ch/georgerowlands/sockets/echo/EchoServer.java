package ch.georgerowlands.sockets.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String args[]) {
        final int serverPort = 1234;
        try (ServerSocket server = new ServerSocket(serverPort)) {
            System.out.println("Startet Echo Server at " + server.getLocalSocketAddress());
            while (true) {
                try (Socket s = server.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                     PrintWriter out = new PrintWriter(s.getOutputStream(), true); // autoFlush
                ) {
                    in.lines().forEach(line -> {
                        out.println(line);
                        System.out.println(">> " + line);
                    });
                    System.out.println("done serving " + s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
