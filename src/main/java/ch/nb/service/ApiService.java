package ch.nb.service;

import ch.nb.auth.DocEcmApiAuth;
import ch.nb.auth.PropertiesLoader;
import ch.nb.dto.AttachmentDTO;
import ch.nb.dto.MetadataDTO;
import ch.nb.utils.SimpleLogger;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ApiService {

    private static final PropertiesLoader propertiesLoader = new PropertiesLoader("api.properties");
    private static final String API_BASE_URL = propertiesLoader.getProperty("api.url");
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private static void getAuthentication() {
        if (DocEcmApiAuth.currentToken == null || DocEcmApiAuth.isTokenExpired(60)) {
            SimpleLogger.info("Token is null or expired. Attempting to re-authenticate.");
            if (!DocEcmApiAuth.authenticate()) {
                SimpleLogger.error("Authentication failed. Cannot proceed with API call.");
                // TODO manage this with a custom exception
            }
        }
    }

    private static HttpResponse<String> sendHttpGetRequest(String endpoint) throws IOException, InterruptedException {
        getAuthentication();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + DocEcmApiAuth.currentToken.getAccessToken())
                .GET()
                .timeout(Duration.ofSeconds(30)) // Increased timeout for potentially large files
                .build();
        try {
            SimpleLogger.info("Attempting GET request: " + endpoint);
            return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            SimpleLogger.error("Error sending HTTP GET request to " + endpoint + ": " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    private static HttpResponse<String> sendHttpPostRequest(String endpoint, String payload) throws IOException, InterruptedException {
        getAuthentication();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + DocEcmApiAuth.currentToken.getAccessToken())
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .timeout(Duration.ofSeconds(30))
                .build();
        try {
            SimpleLogger.info("Attempting POST request: " + endpoint);
            return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            SimpleLogger.error("Error sending HTTP POST request to " + endpoint + ": " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Retrieves a document attachment by its ID.
     *
     * @param objectID The ID of the document.
     * @return AttachmentDTO containing the attachment details, or null if an error occurs.
     */
    public static AttachmentDTO getDocumentAttachment(int objectID) {

        String endpoint = API_BASE_URL + "/api/document/" + objectID + "/attachment";

        try {
            HttpResponse<String> response = sendHttpGetRequest(endpoint);

            SimpleLogger.info("Get Document Attachment Response Status Code: " + response.statusCode());

            if (response.statusCode() == 200) {
                SimpleLogger.info("Successfully retrieved document attachment.");
                AttachmentDTO attachmentDTO = objectMapper.readValue(response.body(), AttachmentDTO.class);
                SimpleLogger.info("Attachment DTO: " + attachmentDTO.toString());
                return attachmentDTO;
            } else {
                SimpleLogger.error("Failed to get document attachment. HTTP Status: " + response.statusCode() + " Body: " + response.body());
                return null;
            }
        } catch (Exception e) {
            SimpleLogger.error("An error occurred during the HTTP request for getDocumentAttachment: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves document metadata by its ID.
     *
     * @param objectID The ID of the document.
     * @return MetadataDTO containing the document metadata, or null if an error occurs.
     */
    public static MetadataDTO getDocumentMetadata(int objectID) {
        String endpoint = API_BASE_URL + "/api/document/" + objectID + "/metadata";

        try {
            HttpResponse<String> response = sendHttpGetRequest(endpoint);
            SimpleLogger.info("Get Document Metadata response: " + response.body());
            MetadataDTO metadataDTO = objectMapper.readValue(response.body(), MetadataDTO.class);
            SimpleLogger.info(metadataDTO.toString());
            return metadataDTO;
        } catch (Exception e) {
            SimpleLogger.error("An error occurred during the HTTP request for getDocumentAttachment: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static void searchDocumentsMetadata() {
        String endpoint = API_BASE_URL + "/api/search/advanced";
        String payload = "{\"searchPattern\": \";FF_INTEGRE|l01|Non|list;AND;FF_TRAITE|l01|Traité|list;\"}";

        try {
            HttpResponse<String> response = sendHttpPostRequest(endpoint, payload);

            SimpleLogger.info("Search Response Status Code: " + response.statusCode());

            if (response.statusCode() == 200) {
                SimpleLogger.info("Successfully searched.");
                System.out.println(response.body());
            } else {
                SimpleLogger.error("Failed to search. HTTP Status: " + response.statusCode() + " Body: " + response.body());
            }
        } catch (Exception e) {
            SimpleLogger.error("An error occurred during the HTTP request for search: " + e.getMessage());
            e.printStackTrace();
        }
    }
}