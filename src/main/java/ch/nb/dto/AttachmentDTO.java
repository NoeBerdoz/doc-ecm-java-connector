package ch.nb.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AttachmentDTO {

    @JsonProperty("Id")
    private Integer id;

    @JsonProperty("FileName")
    private String fileName;

    @JsonProperty("File")
    private String file; // Base64 encoded string

    @JsonProperty("Base64File")
    private String base64File;

    @JsonProperty("FileID")
    private String fileID;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getBase64File() {
        return base64File;
    }

    public void setBase64File(String base64File) {
        this.base64File = base64File;
    }

    public String getFileID() {
        return fileID;
    }

    public void setFileID(String fileID) {
        this.fileID = fileID;
    }

    @Override
    public String toString() {
        return "AttachmentDTO{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", file='<byte_array_content_omitted>'" + // Avoid logging potentially large file content
                ", base64File='<base64_content_omitted>'" +
                ", fileID='" + fileID + '\'' +
                '}';
    }
}