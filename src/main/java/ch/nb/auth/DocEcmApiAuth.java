package ch.nb.auth;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DocEcmApiAuth {

    private static PropertiesLoader propertiesLoader = new PropertiesLoader("api.properties");

    private static final String API_URL = propertiesLoader.getProperty("api.url");
    private static final String API_USERNAME = propertiesLoader.getProperty("api.username");
    private static final String API_PASSWORD = propertiesLoader.getProperty("api.password");

    private static final String TOKEN_ENDPOINT = API_URL + "/token";

    public static void main(String[] args) {

        // Create the HttpClient
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        // Prepare form data for the request body
        Map<String, String> formData = new HashMap<>();
        formData.put("grant_type", "password"); //
        formData.put("username", API_USERNAME); //
        formData.put("password", API_PASSWORD); //

        // Encode form data to x-www-form-urlencoded format
        String form = formData.entrySet()
                .stream()
                .map(e -> URLEncoder.encode(e.getKey(), StandardCharsets.UTF_8) + "="
                        + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        // Build the HttpRequest
        HttpRequest tokenRequest = HttpRequest.newBuilder()
                .uri(URI.create(TOKEN_ENDPOINT))
                .header("Content-Type", "application/x-www-form-urlencoded") //
                .POST(HttpRequest.BodyPublishers.ofString(form))
                .timeout(Duration.ofSeconds(10))
                .build();

        System.out.println("Attempting to authenticate to: " + TOKEN_ENDPOINT);
        System.out.println("Request Body: " + form);


        try {
            // Send the request and get the response
            // HttpResponse.BodyHandlers.ofString() means the response body will be read as a String
            HttpResponse<String> response = httpClient.send(tokenRequest, HttpResponse.BodyHandlers.ofString());

            System.out.println("Response Status Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());

            if (response.statusCode() == 200) {
                System.out.println("\nAuthentication successful!");
                // TODO parse the response.body() here to extract the actual access token.
            } else {
                System.out.println("\nAuthentication failed.");
            }

        } catch (Exception e) {
            System.err.println("An error occurred during the HTTP request: " + e.getMessage());
            e.printStackTrace();
        }
    }
}