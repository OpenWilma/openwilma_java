package org.openwilma.java.classes.responses;

import com.google.gson.annotations.SerializedName;

public class SessionResponse {

    @SerializedName("SessionID")
    private String sessionId;

    @SerializedName("ApiVersion")
    private int apiVersion;

    public SessionResponse(String sessionId, int apiVersion) {
        this.sessionId = sessionId;
        this.apiVersion = apiVersion;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(int apiVersion) {
        this.apiVersion = apiVersion;
    }
}
