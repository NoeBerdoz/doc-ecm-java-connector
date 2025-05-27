package ch.nb.auth;

import ch.nb.dto.TokenResponseDTO;
import ch.nb.utils.SimpleLogger;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    public static TokenResponseDTO currentToken = null;
    private static long tokenIssueTimeMillis = 0;

    public static boolean authenticate() {
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

        SimpleLogger.info("Attempting to authenticate to: " + TOKEN_ENDPOINT);
        SimpleLogger.info("Request Body: " + form);

        try {
            // Send the request and get the response
            // HttpResponse.BodyHandlers.ofString() means the response body will be read as a String
            HttpResponse<String> response = httpClient.send(tokenRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                SimpleLogger.info("Authentication successful");
                // TODO parse the response.body() here to extract the actual access token.
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    currentToken = objectMapper.readValue(response.body(), TokenResponseDTO.class);
                    SimpleLogger.info("Token retrieved successfully");
                    return true;
                } catch (Exception e) {
                    SimpleLogger.error("Failed to parse token JSON: " + e.getMessage());
                    e.printStackTrace();
                    currentToken = null;
                    return false;
                }
            } else {
                SimpleLogger.error("Authentication failed. HTTP Status: " + response.statusCode() + " Body: " + response.body());
                currentToken = null;
                return false;
            }
        } catch (Exception e) {
            SimpleLogger.error("An error occurred during the HTTP request: " + e.getMessage());
            e.printStackTrace();
            currentToken = null;
            return false;
        }
    }

    /**
     * Checks if the current token is expired or close to expiring.
     *
     * @param safetyMarginSeconds A buffer (in seconds) to consider the token expired earlier.
     * @return true if the token is null, has no expiry info, or is considered expired.
     */
    public static boolean isTokenExpired(long safetyMarginSeconds) {
        if (currentToken == null || currentToken.getExpiresIn() <= 0 || tokenIssueTimeMillis <= 0) {
            return true; // No token or no expiry info, assume expired/invalid
        }
        long currentTimeMillis = System.currentTimeMillis();
        long expiresInMillis = currentToken.getExpiresIn() * 1000;
        long expiryTimeMillis = tokenIssueTimeMillis + expiresInMillis;

        return currentTimeMillis >= (expiryTimeMillis - (safetyMarginSeconds * 1000));
    }
}