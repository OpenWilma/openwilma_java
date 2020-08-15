package org.openwilma.java.classes;

import com.google.gson.annotations.SerializedName;

public class WilmaServer {
    @SerializedName("url")
    private String serverURL;

    @SerializedName("name")
    private String name;

    public WilmaServer(String serverURL, String name) {
        this.serverURL = serverURL;
        this.name = name;
    }

    public WilmaServer(String serverURL) {
        this.serverURL = serverURL;
    }

    public String getServerURL() {
        return serverURL;
    }

    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
