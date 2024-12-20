package client.server;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        int port = 5000; 
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Serveri eshte gati dhe po pret lidhje...");

            Socket socket = serverSocket.accept();
            System.out.println("Klienti u lidh: " + socket.getInetAddress());

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            String message;
            while ((message = input.readLine()) != null) {
                if (message.equalsIgnoreCase("exit")) {
                    System.out.println("Klienti kerkoi te shkeputet.");
                    break;
                }

                System.out.println("Klienti: " + message);

                BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Serveri: ");
                String reply = consoleInput.readLine();
                if (reply.equalsIgnoreCase("exit")) {
                    output.println("exit");
                    break;
                }

                output.println(reply);
            }

            System.out.println("Lidhja me klientin perfundoi.");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



