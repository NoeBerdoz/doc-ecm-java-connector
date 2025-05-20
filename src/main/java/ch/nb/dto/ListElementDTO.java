package ch.nb.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListElementDTO {

    @JsonProperty("Id")
    private String id;

    @JsonProperty("DisplayValue")
    private String displayValue;

    @JsonProperty("Selected")
    private boolean selected;

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "ListElementDTO{" +
                "id='" + id + '\'' +
                ", displayValue='" + displayValue + '\'' +
                ", selected=" + selected +
                '}';
    }
}