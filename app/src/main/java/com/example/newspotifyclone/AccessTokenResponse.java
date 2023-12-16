package com.example.newspotifyclone;

import com.google.gson.annotations.SerializedName;

public class AccessTokenResponse {
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

    @SerializedName("access_token")
    public String accessToken;
    @SerializedName("token_type")
    private String tokenType;
}
