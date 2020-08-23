package org.openwilma.java.classes;

public class Authentication {
    private WilmaServer wilmaServer;
    private String sessionId;
    private Role role;

    public Authentication(WilmaServer wilmaServer, String sessionId, Role role) {
        this.wilmaServer = wilmaServer;
        this.sessionId = sessionId;
        this.role = role;
    }

    public Authentication(WilmaServer wilmaServer, String sessionId) {
        this.wilmaServer = wilmaServer;
        this.sessionId = sessionId;
        this.role = null;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public WilmaServer getWilmaServer() {
        return wilmaServer;
    }

    public void setWilmaServer(WilmaServer wilmaServer) {
        this.wilmaServer = wilmaServer;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
