package br.ufal.ic.p2.jackut.system;

/**
 * Represents a user session in the system.
 */

public class Session {
    private final String sessionId;
    private final User user;

    /**
     * Constructs a new Session object with the provided user and session ID.
     *
     * @param user       The user associated with the session.
     * @param sessionId  The unique identifier for the session.
     */

    public Session(User user, String sessionId) {
        this.sessionId = sessionId;
        this.user = user;
    }

    public String getSessionId() {
        return sessionId;
    }

    public User getUser() {
        return user;
    }

}
