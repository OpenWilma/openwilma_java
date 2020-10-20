package org.openwilma.java.classes;

public class Authentication {
    private WilmaServer wilmaServer;
    private String sessionId;
    private Role role;
    private User user;
    private boolean requiresRole;

    public Authentication(WilmaServer wilmaServer, String sessionId, Role role) {
        this.wilmaServer = wilmaServer;
        this.sessionId = sessionId;
        this.role = role;
    }

    public Authentication(WilmaServer wilmaServer, String sessionId, boolean requiresRole) {
        this.wilmaServer = wilmaServer;
        this.sessionId = sessionId;
        this.role = null;
        this.requiresRole = requiresRole;
    }

    public boolean isRequiresRole() {
        return requiresRole;
    }

    public void setRequiresRole(boolean requiresRole) {
        this.requiresRole = requiresRole;
    }

    public Role getRole() {
        if (role == null && requiresRole)
            throw new RuntimeException("Role is required for this account");
        return role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
