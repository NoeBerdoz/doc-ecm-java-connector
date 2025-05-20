package ch.nb.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldDTO {

    @JsonProperty("DefFieldID")
    private int defFieldID;

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Type")
    private int type;

    @JsonProperty("Code")
    private String code;

    @JsonProperty("Value")
    private String value;

    @JsonProperty("DefaultValue")
    private String defaultValue;

    @JsonProperty("Observations")
    private String observations;

    @JsonProperty("IsRequired")
    private boolean isRequired;

    @JsonProperty("IsVisible")
    private boolean isVisible;

    @JsonProperty("IsReadOnly")
    private boolean isReadOnly;

    @JsonProperty("IsUnique")
    private boolean isUnique;

    @JsonProperty("Multivalue")
    private boolean multivalue;

    @JsonProperty("InternalTableID")
    private Object internalTableID; // Can be number or null

    @JsonProperty("ListElements")
    private List<ListElementDTO> listElements;

    @JsonProperty("RelatedFieldsCodes")
    private List<String> relatedFieldsCodes;

    @JsonProperty("Validations")
    private ValidationsDTO validations;

    // Getters and Setters

    public int getDefFieldID() {
        return defFieldID;
    }

    public void setDefFieldID(int defFieldID) {
        this.defFieldID = defFieldID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public boolean isIsRequired() {
        return isRequired;
    }

    public void setIsRequired(boolean isRequired) {
        this.isRequired = isRequired;
    }

    public boolean isIsVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public boolean isIsReadOnly() {
        return isReadOnly;
    }

    public void setIsReadOnly(boolean isReadOnly) {
        this.isReadOnly = isReadOnly;
    }

    public boolean isIsUnique() {
        return isUnique;
    }

    public void setIsUnique(boolean isUnique) {
        this.isUnique = isUnique;
    }

    public boolean isMultivalue() {
        return multivalue;
    }

    public void setMultivalue(boolean multivalue) {
        this.multivalue = multivalue;
    }

    public Object getInternalTableID() {
        return internalTableID;
    }

    public void setInternalTableID(Object internalTableID) {
        this.internalTableID = internalTableID;
    }

    public List<ListElementDTO> getListElements() {
        return listElements;
    }

    public void setListElements(List<ListElementDTO> listElements) {
        this.listElements = listElements;
    }

    public List<String> getRelatedFieldsCodes() {
        return relatedFieldsCodes;
    }

    public void setRelatedFieldsCodes(List<String> relatedFieldsCodes) {
        this.relatedFieldsCodes = relatedFieldsCodes;
    }

    public ValidationsDTO getValidations() {
        return validations;
    }

    public void setValidations(ValidationsDTO validations) {
        this.validations = validations;
    }

    @Override
    public String toString() {
        return "FieldDTO{" +
                "defFieldID=" + defFieldID +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", code='" + code + '\'' +
                ", value='" + value + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                ", observations='" + observations + '\'' +
                ", isRequired=" + isRequired +
                ", isVisible=" + isVisible +
                ", isReadOnly=" + isReadOnly +
                ", isUnique=" + isUnique +
                ", multivalue=" + multivalue +
                ", internalTableID=" + internalTableID +
                ", listElements=" + listElements +
                ", relatedFieldsCodes=" + relatedFieldsCodes +
                ", validations=" + validations +
                '}';
    }
}