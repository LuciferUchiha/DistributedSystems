package ch.georgerowlands.sockets;

import java.io.InputStream;
import java.net.Socket;

public class DaytimeClient {
    public static void main(String[] args) throws Exception {
        try (Socket s = new Socket("time-a-b.nist.gov", 13)) {
            System.out.println("Connected to " + s.getInetAddress() + ":" + s.getPort());
            System.out.println("From " + s.getLocalAddress() + ":" + s.getLocalPort());
            InputStream in = s.getInputStream();
            int ch = in.read();
            while (ch != -1) {
                System.out.print((char) ch);
                ch = in.read();
            }
        }
    }
}
