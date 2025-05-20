package ch.nb.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MetadataDTO {

    @JsonProperty("ObjectID")
    private int objectID;

    @JsonProperty("OriginalID")
    private int originalID;

    @JsonProperty("ContentTypeID")
    private int contentTypeID;

    @JsonProperty("ContentType")
    private String contentType;

    @JsonProperty("HasAutomaticRecognition")
    private boolean hasAutomaticRecognition;

    @JsonProperty("Source")
    private Object source; // Can be String or null, using Object

    @JsonProperty("IpAddress")
    private Object ipAddress; // Can be String or null, using Object

    @JsonProperty("CreationDate")
    private String creationDate; // Assuming String representation of date-time

    @JsonProperty("IsLastVersion")
    private boolean isLastVersion;

    @JsonProperty("IsDigitallySigned")
    private boolean isDigitallySigned;

    @JsonProperty("Version")
    private String version;

    @JsonProperty("Author")
    private String author;

    @JsonProperty("Fields")
    private List<FieldDTO> fields;

    @JsonProperty("Attachment")
    private Object attachment; // Can be an object or null

    @JsonProperty("AttachmentFileName")
    private String attachmentFileName;

    @JsonProperty("Attachments")
    private Object attachments; // Can be a list or null

    // Getters and Setters

    public int getObjectID() {
        return objectID;
    }

    public void setObjectID(int objectID) {
        this.objectID = objectID;
    }

    public int getOriginalID() {
        return originalID;
    }

    public void setOriginalID(int originalID) {
        this.originalID = originalID;
    }

    public int getContentTypeID() {
        return contentTypeID;
    }

    public void setContentTypeID(int contentTypeID) {
        this.contentTypeID = contentTypeID;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public boolean isHasAutomaticRecognition() {
        return hasAutomaticRecognition;
    }

    public void setHasAutomaticRecognition(boolean hasAutomaticRecognition) {
        this.hasAutomaticRecognition = hasAutomaticRecognition;
    }

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    public Object getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(Object ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isIsLastVersion() {
        return isLastVersion;
    }

    public void setIsLastVersion(boolean isLastVersion) {
        this.isLastVersion = isLastVersion;
    }

    public boolean isIsDigitallySigned() {
        return isDigitallySigned;
    }

    public void setIsDigitallySigned(boolean isDigitallySigned) {
        this.isDigitallySigned = isDigitallySigned;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<FieldDTO> getFields() {
        return fields;
    }

    public void setFields(List<FieldDTO> fields) {
        this.fields = fields;
    }

    public Object getAttachment() {
        return attachment;
    }

    public void setAttachment(Object attachment) {
        this.attachment = attachment;
    }

    public String getAttachmentFileName() {
        return attachmentFileName;
    }

    public void setAttachmentFileName(String attachmentFileName) {
        this.attachmentFileName = attachmentFileName;
    }

    public Object getAttachments() {
        return attachments;
    }

    public void setAttachments(Object attachments) {
        this.attachments = attachments;
    }

    @Override
    public String toString() {
        return "MetadataDTO{" +
                "objectID=" + objectID +
                ", originalID=" + originalID +
                ", contentTypeID=" + contentTypeID +
                ", contentType='" + contentType + '\'' +
                ", hasAutomaticRecognition=" + hasAutomaticRecognition +
                ", source=" + source +
                ", ipAddress=" + ipAddress +
                ", creationDate='" + creationDate + '\'' +
                ", isLastVersion=" + isLastVersion +
                ", isDigitallySigned=" + isDigitallySigned +
                ", version='" + version + '\'' +
                ", author='" + author + '\'' +
                ", fields=" + fields +
                ", attachment=" + attachment +
                ", attachmentFileName='" + attachmentFileName + '\'' +
                ", attachments=" + attachments +
                '}';
    }
}