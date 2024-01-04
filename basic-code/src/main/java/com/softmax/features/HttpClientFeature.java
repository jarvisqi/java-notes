package com.softmax.features;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpClientFeature {


    public static void main(String[] args) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.example.com/"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());


        HttpClient clientBuild = HttpClient.newBuilder().build();
        HttpRequest requestBuild = HttpRequest.newBuilder()
                .uri(URI.create("https://www.example.com/"))
                .header("Content-Type", "application/json")
                .timeout(Duration.ofMillis(1000))
                .version(HttpClient.Version.HTTP_2)
                .build();
        HttpResponse<String> httpResponse = clientBuild.send(requestBuild, HttpResponse.BodyHandlers.ofString());

        System.out.printf(httpResponse.body());

    }
}
