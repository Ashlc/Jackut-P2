package br.ufal.ic.p2.jackut.system;

import java.util.ArrayList;

public class Community {
    private final String session;
    private final String name;
    private final String description;
    private String owner;
    private ArrayList<String> members = new ArrayList<>();

    public Community(String session, String name, String description) {
        this.session = session;
        this.name = name;
        this.description = description;
    }

    public String getCommmunityName() {
        return name;
    }

    public String getComunityDescription() {
        return description;
    }

    public void setCommunityOwner(String session, String name) {
        owner = name;
    }

    public String getCommunitySession() {
        return session;
    }

    public String getCommunityOwner() {
        return owner;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMember(String login) {
        members.add(login);
    }
}
