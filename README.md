# Doc.ECM Java Connector

This project is a Java-based connector designed to interact with a Document Enterprise Content Management (ECM) system's API. It provides functionalities to authenticate, retrieve, and manage documents within the ECM.

## Key Features

* **Authentication**: Handles authentication with the ECM API using OAuth2 (password grant type) to obtain access tokens. It also includes a mechanism to check for token expiry.
* **Document Retrieval**:
    * Fetches document metadata, including fields, content type, and other properties.
    * Retrieves document attachments, decoding Base64 encoded files.
* **Document Management**:
    * Searches for documents based on specified criteria.
    * Updates document status within the ECM, for example, marking documents as "integrated" or "paid" by interacting with defined workflows.
    * Stores retrieved document attachments locally on the file system.
* **Configuration**: API connection details (URL, username, password) are managed through an `api.properties` file.
* **Logging**: Includes a simple logger for console output with different log levels (INFO, WARNING, ERROR) and colored highlighting for better readability.

## Core Components

* `DocEcmApiAuth.java`: Manages authentication and token handling.
* `ApiService.java`: Contains methods for interacting with the ECM API endpoints (e.g., fetching documents, metadata, attachments, updating status).
* `PropertiesLoader.java`: Loads configuration from the `api.properties` file.
* **DTOs** (Data Transfer Objects): A set of classes (e.g., `AttachmentDTO`, `MetadataDTO`, `TokenResponseDTO`) used to map JSON responses from the API to Java objects.
* `Main.java`: Example usage of the connector.

## Dependencies

The project uses the following main dependency:
* **Jackson**: For JSON parsing and generation (jackson-core, jackson-databind, jackson-annotations).

This connector facilitates programmatic interaction with the ECM system, automating tasks like document integration and status updates.