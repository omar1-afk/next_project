package org.noteam.nextclient.utils;

import com.google.gson.JsonObject;
import org.json.JSONObject;
import org.noteam.nextclient.Config;

import javax.imageio.IIOException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.logging.Logger;

public class ApiUtil {
    private static final String SPRINGBOOT_URL = "http://localhost:8080";

    public enum RequestMethod {
        GET,
        POST,
        PUT,
        PATCH,
        DELETE
    }

    private static String token;

    public static void setToken(String token) {
        ApiUtil.token = token;
    }

    public static HttpURLConnection fetchApi(String path, RequestMethod requestMethod, JsonObject jsObject) {
        try {
            URL url = new URL(SPRINGBOOT_URL + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestMethod.toString());
            if (token != null) {
                connection.setRequestProperty("Authorization", "Bearer " + token);
            }
            if (jsObject != null && requestMethod != RequestMethod.GET) {
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
                try (OutputStream outputStream = connection.getOutputStream()) {
                    byte[] input = jsObject.toString().getBytes(StandardCharsets.UTF_8);
                    outputStream.write(input, 0, input.length);
                }
            }
            return connection;
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        return null;
    }

    public static String readResponse(HttpURLConnection connection) {
        try {
            StringBuilder jsonResult = new StringBuilder();
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNext()) {
                jsonResult.append(scanner.nextLine());
            }
            scanner.close();
            return jsonResult.toString();
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        return null;
    }
}
