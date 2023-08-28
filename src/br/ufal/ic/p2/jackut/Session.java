package br.ufal.ic.p2.jackut;

public class Session {
    private final int sessionId;
    private final User user;

    public Session(User user, int sessionId) {
        this.sessionId = sessionId;
        this.user = user;
    }
}
