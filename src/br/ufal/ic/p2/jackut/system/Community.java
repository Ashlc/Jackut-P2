package br.ufal.ic.p2.jackut.system;

public class Community {
    private final int session;
    private final String name;
    private final String description;
    private User ownerUser;

    public Community(int session, String name, String description) {
        this.session = session;
        this.name = name;
        this.description = description;
    }

    public String getComunityDescription() {
        return description;
    }

    public void setCommunityOwner(Database DB) {
        ownerUser = DB.getUserBySessionId(Integer.toString(session));
        String owner = ownerUser.getName();
    }

}
