package ch.georgerowlands;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Optional;

public class NetworkInterfaces {
    public static void main(String[] args) throws SocketException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface intf = interfaces.nextElement();

            System.out.print(intf.getName());
            System.out.println(" [" + intf.getDisplayName() + "]");

            Enumeration<InetAddress> adr = intf.getInetAddresses();
            while (adr.hasMoreElements()) {
                System.out.println("\t" + adr.nextElement());
            }

            Optional<byte[]> hardwareAddress = Optional.ofNullable(intf.getHardwareAddress());
            hardwareAddress.ifPresentOrElse(
                    (address) -> {
                        StringBuilder mac = new StringBuilder();
                        for (int i = 0; i < address.length; i++) {
                            mac.append(String.format((i == 0 ? "" : "-") + "%02X", address[i]));
                        }
                        System.out.println(mac);
                    },
                    () -> System.out.println("no mac address"));
        }
    }
}
