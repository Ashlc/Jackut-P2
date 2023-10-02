package br.ufal.ic.p2.jackut.system;

import br.ufal.ic.p2.jackut.exceptions.CommunityException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Community {
    private final String name;
    private final String description;
    private final String owner;
    private ArrayList<String> members;
    private ArrayList<Message> inbox;

    @JsonCreator
    public Community(
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("owner") String owner,
            @JsonProperty("members") ArrayList<String> members,
            @JsonProperty("inbox") ArrayList<Message> inbox) {

        this.owner = owner;
        this.name = name;
        this.description = description;
        this.members = members;
        this.inbox = inbox;

    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getOwner() {
        return this.owner;
    }

    public ArrayList<String> getMembers() {
        return this.members;
    }

    public String membersToString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (String member : this.members) {
            sb.append(member).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append('}');
        return sb.toString();
    }

    public void addMember(String login) {
        if (!this.members.contains(login)) {
            this.members.add(login);
        } else throw new CommunityException("Usuario já faz parte dessa comunidade.");
    }

    public void addMessage(Message message) {
        this.inbox.add(message);
    }

    public String readMessage() {
        if (!this.inbox.isEmpty()) {
            String message = this.inbox.get(0).message();
            this.inbox.remove(0);
            return message;
        } else {
            throw new CommunityException("Não há mensagens.");
        }
    }

    public boolean hasMember(String login) {
        return this.members.contains(login);
    }
}
