package rrjetat.skaner;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PortScanner {

    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);

        // Marrim nje liste IP-sh nga perdoruesi
        System.out.print("Vendos IP-ne fillestare (p.sh., 192.168.1.1): ");
        String baseIP = scanner.nextLine();

        System.out.print("Vendos IP-ne perfundimtare (p.sh., 192.168.1.10): ");
        String endIP = scanner.nextLine();

        System.out.println("Duke skanuar adresat IP nga " + baseIP + " deri ne " + endIP);
        
        // Harta e porteve standarde dhe sherbimeve
        Map<Integer, String> portServices = getStandardPortServices();

        // Krijojme intervalin e IP-ve dhe skano secilen IP
        String[] startParts = baseIP.split("\\.");
        String[] endParts = endIP.split("\\.");

        int baseLastOctet = Integer.parseInt(startParts[3]);
        int endLastOctet = Integer.parseInt(endParts[3]);

        // Skanojme te gjitha IP me cikel for
        for (int i = baseLastOctet; i <= endLastOctet; i++) {
            String currentIP = startParts[0] + "." + startParts[1] + "." + startParts[2] + "." + i;
            System.out.println("Duke skanuar IP-ne: " + currentIP);

            // Skanimi i porteve per IP aktuale (vetem ato porta qe jane te perfshira ne hart)
            for (Map.Entry<Integer, String> entry : portServices.entrySet()) {
                int port = entry.getKey();
                String serviceName = entry.getValue();

                if (isPortOpen(currentIP, port)) {
                    System.out.println("Porta " + port + " eshte e hapur. Sherbimi: " + serviceName);
                }
            }
        }

        System.out.println("Skanimi perfundoi.");

        // Mbyllim Scanner-in
        scanner.close();
    }

    // Funksion qe kontrollon nese porta esht e hapur
    private static boolean isPortOpen(String ip, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(ip, port), 200); // Timeout 200ms
            return true;
        } catch (Exception e) {
            return false; // Porta eshte e mbyllur
        }
    }

    // Harta e portave dhe sherbimet e tyre
    private static Map<Integer, String> getStandardPortServices() {
        Map<Integer, String> portServices = new HashMap<>();
        portServices.put(21, "FTP");
        portServices.put(22, "SSH");
        portServices.put(23, "Telnet");
        portServices.put(25, "SMTP");
        portServices.put(53, "DNS");
        portServices.put(80, "HTTP");
        portServices.put(110, "POP3");
        portServices.put(143, "IMAP");
        portServices.put(443, "HTTPS");
        portServices.put(3306, "MySQL");
        portServices.put(3389, "RDP");
        return portServices;
    }
}

