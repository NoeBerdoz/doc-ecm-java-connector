package ch.nb;

import ch.nb.auth.DocEcmApiAuth;
import ch.nb.dto.AttachmentDTO;
import ch.nb.dto.MetadataDTO;
import ch.nb.service.ApiService;
import ch.nb.utils.SimpleLogger;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        DocEcmApiAuth.authenticate();
        DocEcmApiAuth.isTokenExpired(60);
        AttachmentDTO attachment = ApiService.getDocumentAttachment(2414);
        MetadataDTO metadata = ApiService.getDocumentMetadata(2414);
        SimpleLogger.info("END");
    }
}