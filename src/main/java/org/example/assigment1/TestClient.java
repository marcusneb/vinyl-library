package org.example.assigment1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TestClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8080);
            System.out.println("Connected to server");

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);

            Thread listener = new Thread(() -> {
                try {
                  String line;
                  while ((line = in.readLine()) != null)
                  {
                      System.out.println("Server: " + line);
                  }
                }catch (IOException e)
                {
                    System.out.println("Disconnected");
                }
            });
            listener.setDaemon(true);
            listener.start();

            while (true) {
                System.out.print("> ");
                String input = scanner.nextLine();

                if (input.equals("quit")) break;

                out.println(input);

                String response = in.readLine();
                System.out.println("Server: " + response);
            }

            socket.close();
            System.out.println("Disconnected");

        } catch (IOException e) {
            System.out.println("Could not connect: " + e.getMessage());
        }
    }
}