package ch.nb;

import ch.nb.auth.DocEcmApiAuth;
import ch.nb.dto.AttachmentDTO;
import ch.nb.dto.MetadataDTO;
import ch.nb.service.ApiService;
import ch.nb.utils.SimpleLogger;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        SimpleLogger.info("STARTING");

        DocEcmApiAuth.authenticate();
        DocEcmApiAuth.isTokenExpired(60);

        ApiService.integrateDocuments();

        // Few exemples of callable methods
        // ApiService.setDocumentAsPaid(3006);
        // AttachmentDTO attachment = ApiService.getDocumentAttachment(2690);
        // MetadataDTO metadata = ApiService.getDocumentMetadata(2690);

        SimpleLogger.info("END");
    }
}