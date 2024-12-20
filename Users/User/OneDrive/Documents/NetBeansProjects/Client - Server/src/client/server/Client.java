package client.server;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 5000;

        try (Socket socket = new Socket(host, port)) {
            System.out.println("Lidhje e suksesshme me serverin!");
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
            String message;

            while (true) {
                System.out.print("Klienti: ");
                message = consoleInput.readLine();

                if (message.equalsIgnoreCase("exit")) {
                    output.println("exit");
                    break;
                }

                output.println(message);

                String reply = input.readLine();
                if (reply == null || reply.equalsIgnoreCase("exit")) {
                    System.out.println("Serveri u shkeput.");
                    break;
                }
                System.out.println("Serveri: " + reply);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}






