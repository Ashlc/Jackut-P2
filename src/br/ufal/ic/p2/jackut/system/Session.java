package br.ufal.ic.p2.jackut.system;

/**
 * Represents a user session in the system.
 */

public record Session(User user, String sessionId) {
    /**
     * Constructs a new Session object with the provided user and session ID.
     *
     * @param user      The user associated with the session.
     * @param sessionId The unique identifier for the session.
     */

    public Session {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (sessionId == null) {
            throw new IllegalArgumentException("Session ID cannot be null");
        }
    }

}
