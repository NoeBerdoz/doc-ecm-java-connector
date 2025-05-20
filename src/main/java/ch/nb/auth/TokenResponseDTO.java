package ch.nb.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

// Ignore any properties in the JSON that are not defined in this class
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponseDTO {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private long expiresIn;

    @JsonProperty("UserID")
    private String userId;

    // ".issued" and ".expires" start with a dot, which is not a standard Java identifier start.
    // Jackson can handle this with @JsonProperty.
    @JsonProperty(".issued")
    private String issuedAt;

    @JsonProperty(".expires")
    private String expiresAt;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(String issuedAt) {
        this.issuedAt = issuedAt;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    @Override
    public String toString() {
        return "TokenResponseDTO{" +
                "accessToken='...' (masked)" + // Mask token in logs
                ", tokenType='" + tokenType + '\'' +
                ", expiresIn=" + expiresIn +
                ", userId='" + userId + '\'' +
                ", issuedAt='" + issuedAt + '\'' +
                ", expiresAt='" + expiresAt + '\'' +
                '}';
    }
}