package ch.nb.service;

import ch.nb.auth.DocEcmApiAuth;
import ch.nb.auth.PropertiesLoader;
import ch.nb.dto.AttachmentDTO;
import ch.nb.dto.MetadataDTO;
import ch.nb.utils.SimpleLogger;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ApiService {

    private static final PropertiesLoader propertiesLoader = new PropertiesLoader("api.properties");
    private static final String API_BASE_URL = propertiesLoader.getProperty("api.url");
    private static final ObjectMapper objectMapper = new ObjectMapper();

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

    private static List<Integer> searchDocumentsToIntegrate() {
        String endpoint = API_BASE_URL + "/api/search/advanced";
        String payload = "{\"searchPattern\": \";FF_INTEGRE|l01|Non|list;AND;FF_TRAITE|l01|Traité|list;\"}";

        try {
            HttpResponse<String> response = sendHttpPostRequest(endpoint, payload);

            if (response.statusCode() == 200) {
                SimpleLogger.info("Successfully searched.");
                ObjectMapper objectMapper = new ObjectMapper();

                // Parse the JSON
                List<Map<String, Object>> searchResults = objectMapper.readValue(
                        response.body(),
                        new TypeReference<List<Map<String, Object>>>() {}
                );

                // Use a stream to retrieve each ObjectID in the parsed JSON
                List<Integer> objectIDs = searchResults.stream()
                        .map(resultMap -> (Integer) resultMap.get("ObjectID"))
                        .collect(Collectors.toList());

                SimpleLogger.info("Found " + objectIDs.size() + " documents to integrate");
                return objectIDs;
            } else {
                SimpleLogger.error("Failed to search. HTTP Status: " + response.statusCode() + " Body: " + response.body());
            }
        } catch (Exception e) {
            SimpleLogger.error("An error occurred during the HTTP request for search: " + e.getMessage());
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private static void setDocumentAsIntegrated(int objectID) throws IOException, InterruptedException {
        int flowID = 82; // This id is from the flow "Flux Integration ERP" in the ECM.

        String endpoint = API_BASE_URL + "/api/flow/validate/" + objectID;
        String payload = "{\"ObjectID\":" + objectID + ",\"FlowID\":" + flowID + "}";

        HttpResponse<String> response = sendHttpPostRequest(endpoint, payload);

        if (response.statusCode() == 200) {
            SimpleLogger.info("Successfully set document as integrated. Document with ID: " + objectID);
        } else {
            SimpleLogger.error("Failed to validate flow integration. HTTP Status: " + response.statusCode() + " Body: " + response.body());
        }
    }

    private static void storeDocument(int objectID) {
        AttachmentDTO attachmentDTO = getDocumentAttachment(objectID);

        if (attachmentDTO == null) {
            SimpleLogger.error("Could not retrieve attachment for ObjectID" + objectID);
        }

        // Only try to store the file if there is a file
        // the API let the user create records without attachment.
        String base64Content = attachmentDTO.getFile();
        if (base64Content == null || base64Content.isEmpty()) {
            // Fallback to "Base64File" if "File" is empty,
            // I don't know why, the API also have another field for the same thing
            base64Content = attachmentDTO.getBase64File();
        }

        if (base64Content == null) {
            SimpleLogger.warning("No attachment file found for " + objectID + " maybe the user didn't put a file with the entry");
            return;
        }

        String fileName = attachmentDTO.getFileName();

        try {
            // Decode the Base64 string into a byte array
            byte[] decodedBytes = Base64.getDecoder().decode(base64Content);

            Path localDocumentDirectory = Paths.get(System.getProperty("user.dir"), "ecm_integrated_documents");

            // Create directory if it doesn't exist
            if (!Files.exists(localDocumentDirectory)) {
                Files.createDirectories(localDocumentDirectory);
                SimpleLogger.info("Created directory at: " + localDocumentDirectory.toString());
            }

            Path destinationPath = localDocumentDirectory.resolve(fileName);
            // Write the bytes to the file
            Files.write(destinationPath, decodedBytes);
            SimpleLogger.info("Successfully stored document '" + fileName + "' at: " + destinationPath);

        } catch (IllegalArgumentException e) {
            SimpleLogger.error("Failed to decode Base64 content for file '" + fileName + "': " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            SimpleLogger.error("Failed to write file '" + fileName + "': " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void integrateDocuments() throws IOException, InterruptedException {
        List<Integer> documentsIds = searchDocumentsToIntegrate();
        for (Integer id : documentsIds) {
            SimpleLogger.info("Trying to integrate document with ID: " + id);
            storeDocument(id);
            setDocumentAsIntegrated(id);
        }
    }

    public static void setDocumentAsPaid(Integer objectID) throws IOException, InterruptedException {
        int flowID = 83; // This id is from the flow "Flux Règlement" in the ECM.

        String endpoint = API_BASE_URL + "/api/flow/validate/" + objectID;
        String payload = "{\"ObjectID\":" + objectID + ", \"FlowID\":" + flowID + "}";

        HttpResponse<String> response = sendHttpPostRequest(endpoint, payload);

        // api returns the object id on response, status code is not trustworthy
        if (response.statusCode() == 200) {
            SimpleLogger.info("Successfully set document as paid. Document with ID: " + objectID);
            SimpleLogger.warning("Document ID went from: " + objectID + " to: " + response.body());
        } else {
            SimpleLogger.error("Failed to set document as paid " + response.statusCode() + " Body: " + response.body());
        }
    }

}