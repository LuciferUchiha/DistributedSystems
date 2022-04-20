package ch.georgerowlands.webservers.net;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class NetHttpClient {
    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://www.google.com"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Statuscode: " + response.statusCode());
        System.out.println("Headers:");
        response.headers().map().forEach((k, v) -> System.out.println(k + ": " + v));
        System.out.println("Body:");
        System.out.println(response.body());
    }
}
