package ch.georgerowlands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoClient {
    public static void main(String[] args) {
        final String host = "localhost";
        final int port = 1234;

        System.out.println("connecting to " + host + ":" + port);
        try (Socket s = new Socket(host, port);
             PrintWriter out = new PrintWriter(s.getOutputStream(), true); // autoFlush
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
             BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        ) {
            System.out.println("connected to " + s.getRemoteSocketAddress());
            String input = stdin.readLine();
            while (input != null && !input.equals("")) {
                out.println(input);
                System.out.println("Echo: " + in.readLine());
                input = stdin.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("disconnected.");
    }
}
