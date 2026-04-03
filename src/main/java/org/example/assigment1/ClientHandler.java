package org.example.assigment1;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private VinylServer server;

    public ClientHandler(Socket socket, VinylServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
             in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             out = new PrintWriter(socket.getOutputStream(), true);
            String message;
            // keep reading messages until the client disconnects
            while((message = in.readLine()) != null)
            {
                System.out.println("Received: " + message);
                String response = processRequest(message);
                out.println(response);
                System.out.println("Sent: " + response);
            }

        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }finally {
            server.removeClient(this);
            try {
                socket.close();
            } catch (IOException e)
            {

            }
        }
    }

    private String processRequest(String jsonString)
    {
        try {
            JsonObject request = JsonParser.parseString(jsonString).getAsJsonObject();
            String type = request.get("type").getAsString();

            if(type.equals("getList"))
            {
                return JsonUtil.buildListResponse(server.getLibrary().getVinyls());
            }
            String title = request.get("title").getAsString();
            Vinyl vinyl = findVinyl(title);
            if(vinyl == null)
            {
                return JsonUtil.buildErrorResponse("Vinyl not found: " + title);
            }
            switch (type)
            {
                case "borrow":
                {
                    String userId = request.get("userId").getAsString();
                    String before = vinyl.getCurrentState().getClass().getSimpleName();
                    vinyl.borrow(userId);
                    String after = vinyl.getCurrentState().getClass().getSimpleName();
                    if(before.equals(after))
                    {
                        return JsonUtil.buildErrorResponse("Cannot borrow: " + title);
                    }
                    server.broadcast(JsonUtil.buildBroadcast("stateChanged", vinyl));
                    return JsonUtil.buildOkResponse("Vinyl borrowed: " + title);
                }
                case "reserve":
                {
                    String userId = request.get("userId").getAsString();
                    String before = vinyl.getCurrentState().getClass().getSimpleName();
                    vinyl.reserve(userId);
                    String after = vinyl.getCurrentState().getClass().getSimpleName();
                    if(before.equals(after))
                    {
                        return JsonUtil.buildErrorResponse("Cannot reserve: " + title);
                    }
                    server.broadcast(JsonUtil.buildBroadcast("stateChanged", vinyl));
                    return JsonUtil.buildOkResponse("Vinyl reserved " + title);
                }

                case "return":
                {
                    String before = vinyl.getCurrentState().getClass().getSimpleName();
                    vinyl.returnVinyl();
                    String after = vinyl.getCurrentState().getClass().getSimpleName();
                    if(before.equals(after))
                    {
                        return JsonUtil.buildErrorResponse("Cannot return vinyl: " + title);
                    }
                    server.broadcast(JsonUtil.buildBroadcast("stateChanged", vinyl));
                    return JsonUtil.buildOkResponse("Vinyl returned: " + title);
                }

                case "remove":
                {
                    if (vinyl.getCurrentState() instanceof AvailableState && vinyl.getReservedBy() == null)
                    {
                        server.getLibrary().removeVinyl(vinyl);
                        return JsonUtil.buildOkResponse("Vinyl removed: " + title);
                    }
                    vinyl.requestRemoval();
                    server.broadcast(JsonUtil.buildBroadcast("stateChanged", vinyl));
                    return JsonUtil.buildOkResponse("Vinyl flagged for removal: " + title);
                }
                default:
                    return JsonUtil.buildErrorResponse("Unknown request type: " + type);
            }

        } catch (Exception e)
        {
            return JsonUtil.buildErrorResponse("Invalid JSON: " + e.getMessage());
        }
    }

    private Vinyl findVinyl(String title)
    {
        for(Vinyl v : server.getLibrary().getVinyls())
        {
            if(v.getTitle().equals(title))
                return v;
        }
        return null;
    }

    public void sendMessage(String json)
    {
        if(out != null)
        {
            out.println(json);
        }
    }
}
