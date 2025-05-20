package ch.nb.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidationsDTO {

    @JsonProperty("Mask")
    private Object mask; // Can be String or null

    @JsonProperty("MinVal")
    private Object minVal; // Can be number or null

    @JsonProperty("MaxVal")
    private Object maxVal; // Can be number or null

    @JsonProperty("Scale")
    private Object scale; // Can be number or null

    @JsonProperty("MaxLength")
    private Object maxLength; // Can be number or null

    // Getters and Setters

    public Object getMask() {
        return mask;
    }

    public void setMask(Object mask) {
        this.mask = mask;
    }

    public Object getMinVal() {
        return minVal;
    }

    public void setMinVal(Object minVal) {
        this.minVal = minVal;
    }

    public Object getMaxVal() {
        return maxVal;
    }

    public void setMaxVal(Object maxVal) {
        this.maxVal = maxVal;
    }

    public Object getScale() {
        return scale;
    }

    public void setScale(Object scale) {
        this.scale = scale;
    }

    public Object getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Object maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public String toString() {
        return "ValidationsDTO{" +
                "mask=" + mask +
                ", minVal=" + minVal +
                ", maxVal=" + maxVal +
                ", scale=" + scale +
                ", maxLength=" + maxLength +
                '}';
    }
}