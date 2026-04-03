package org.example.assigment1;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

public class JsonUtil {

    //converts the full vinyl list into a JSON response
    public static String buildListResponse(List<Vinyl> vinyls)
    {
        JsonObject response = new JsonObject();
        response.addProperty("type", "list");

        JsonArray array = new JsonArray();

        for(Vinyl v : vinyls)
        {
            array.add(vinylToJson(v));
        }
        response.add("vinyls", array);

        return response.toString();
    }

    public static String buildOkResponse(String message)
    {
        JsonObject response = new JsonObject();
        response.addProperty("type", "response");
        response.addProperty("status", "ok");
        response.addProperty("message", message);
        return response.toString();
    }

    public static String buildErrorResponse(String message)
    {
        JsonObject response = new JsonObject();
        response.addProperty("type", "response");
        response.addProperty("status", "error");
        response.addProperty("message", message);
        return response.toString();
    }

    //converts a single Vinyl to json object
    private static JsonObject vinylToJson(Vinyl vinyl)
    {
        JsonObject obj = new JsonObject();
        obj.addProperty("title", vinyl.getTitle());
        obj.addProperty("artist", vinyl.getArtist());
        obj.addProperty("releaseYear", vinyl.getReleaseYear());
        obj.addProperty("state", vinyl.getCurrentState().getClass().getSimpleName());
        obj.addProperty("borrowedBy", vinyl.getBorrowedBy());
        obj.addProperty("reservedBy", vinyl.getReservedBy());
        obj.addProperty("pendingRemoval", vinyl.getPendingRemoval());
        return obj;

    }

    //broadcast when a vinyl changes state
    public static String buildBroadcast(String event, Vinyl vinyl)
    {
        JsonObject broadcast = new JsonObject();
        broadcast.addProperty("type","broadcast");
        broadcast.addProperty("event", event);
        broadcast.addProperty("title", vinyl.getTitle());
        broadcast.addProperty("artist", vinyl.getArtist());
        broadcast.addProperty("state", vinyl.getCurrentState().getClass().getSimpleName());
        broadcast.addProperty("borrowedBy", vinyl.getBorrowedBy());
        broadcast.addProperty("reservedBy", vinyl.getReservedBy());
        broadcast.addProperty("pendingRemoval", vinyl.getPendingRemoval());
        return broadcast.toString();
    }

    //broadcast when a vinyl is removed
    public static String builldRemovalBroadcast(String title)
    {
        JsonObject broadcast = new JsonObject();
        broadcast.addProperty("type", "broadcast");
        broadcast.addProperty("event", "removed");
        broadcast.addProperty("title", title);
        return broadcast.toString();
    }
}
