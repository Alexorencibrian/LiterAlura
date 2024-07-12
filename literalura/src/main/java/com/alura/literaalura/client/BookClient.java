package com.alura.literaalura.client;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class BookClient {
    private static final String BASE_URL = "https://gutendex.com/books/";

    public String getBooksByTitle(String title) {
        HttpClient client = HttpClient.newHttpClient();
        String[] words = title.split(" ");
        StringBuilder combinedResults = new StringBuilder();

        for (String word : words) {
            String encodedWord = URLEncoder.encode(word, StandardCharsets.UTF_8);
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "?search=" + encodedWord))
                .build();

            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                combinedResults.append(response.body());
                combinedResults.append("\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return combinedResults.toString();
    }
}
