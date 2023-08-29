package br.ufal.ic.p2.jackut.system;

/**
 * Represents a user session in the system.
 */

public class Session {
    private final int sessionId;
    private final User user;

    /**
     * Constructs a new Session object with the provided user and session ID.
     *
     * @param user       The user associated with the session.
     * @param sessionId  The unique identifier for the session.
     */

    public Session(User user, int sessionId) {
        this.sessionId = sessionId;
        this.user = user;
    }
}
