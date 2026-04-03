package org.example.assigment1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class VinylServer {

    private final int port;
    private final VinylLibrary library;
    private final List<ClientHandler> clients = new CopyOnWriteArrayList<>();

    public VinylServer(int port) {
        this.port = port;
        this.library = new VinylLibrary();

        library.addVinyl(new Vinyl("Abbey Road", "The Beatles", "1969"));
        library.addVinyl(new Vinyl("Rumours", "Fleetwood Mac", "1977"));
        library.addVinyl(new Vinyl("Dark Side of the Moon", "Pink Floyd", "1973"));
        library.addVinyl(new Vinyl("Thriller", "Michael Jackson", "1982"));
        library.addVinyl(new Vinyl("Kind of Blue", "Miles Davis", "1959"));
    }

   public void start()
   {
       try(ServerSocket serverSocket = new ServerSocket(port))
       {
           System.out.println("Server is listening on port " + port);
           while (true)
           {
               Socket clientSocket = serverSocket.accept();
               System.out.println("Client connected from " + clientSocket.getInetAddress().getHostAddress());

               ClientHandler handler = new ClientHandler(clientSocket, this);
               addClient(handler);
               Thread thread = new Thread(handler);
               thread.start();
           }
       }catch (IOException e)
       {
           System.out.println("Server error: " + e.getMessage());
       }
   }

   public VinylLibrary getLibrary()
   {
       return library;
   }

    public static void main(String[] args) {
        VinylServer server = new VinylServer(8080);
        server.start();
    }

    public void addClient(ClientHandler handler)
    {
        clients.add(handler);
    }

    public void removeClient(ClientHandler handler)
    {
        clients.remove(handler);
        System.out.println("Client disconnected. Active clients: " + clients.size());
    }

    public void broadcast(String json)
    {
        System.out.println("Broadcasting " + json);
        for (ClientHandler client : clients)
        {
            client.sendMessage(json);
        }
    }
}
