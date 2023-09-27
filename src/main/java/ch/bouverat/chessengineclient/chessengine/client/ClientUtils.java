package ch.bouverat.chessengineclient.chessengine.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;

public class ClientUtils {
    public static int getHundreds(int nb){
        while (nb % 100 != 0) {
            nb --;
        }
        return (nb);
    }

    public static String getMacAdress () throws IOException {
        InetAddress localHost = InetAddress.getLocalHost();
        NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localHost);
        StringBuilder macAddress = new StringBuilder();
        byte[] mac = networkInterface.getHardwareAddress();

        if (mac != null) {

            for (int i = 0; i < mac.length; i++) {
                macAddress.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }

            System.out.println("Interface: " + networkInterface.getName());
            System.out.println("Adresse MAC: " + macAddress);
        }
        return (macAddress.toString());
    }
}