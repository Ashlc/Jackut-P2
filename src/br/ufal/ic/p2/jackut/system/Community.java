package br.ufal.ic.p2.jackut.system;

import br.ufal.ic.p2.jackut.exceptions.CommunityException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * This class represents a community in the system.
 * It contains the community's name, description, owner and members.
 */

public class Community {
    private final String name;
    private final String description;
    private final String owner;
    private ArrayList<String> members;

    /**
     * Creates a new community.
     *
     * @param name        the name of the community.
     * @param description the description of the community.
     * @param owner       the owner of the community.
     * @param members     the members of the community.
     */

    @JsonCreator
    public Community(
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("owner") String owner,
            @JsonProperty("members") ArrayList<String> members) {

        this.owner = owner;
        this.name = name;
        this.description = description;
        this.members = members;

    }

    /**
     * Returns the name of the community.
     * @return
     */

    public String getName() {
        return this.name;
    }

    /**
     * Returns the description of the community.
     * @return
     */

    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the owner of the community.
     * @return
     */

    public String getOwner() {
        return this.owner;
    }

    /**
     * Returns the members of the community.
     * @return
     */

    public ArrayList<String> getMembers() {
        return this.members;
    }

    /**
     * Returns the members of the community as a string.
     * @return
     */

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

    /**
     * Adds a member to the community.
     * @param login
     */

    public void addMember(String login) {
        if (!this.members.contains(login)) {
            this.members.add(login);
        } else throw new CommunityException("Usuario já faz parte dessa comunidade.");
    }

    /**
     * Returns true if the community has the provided member.
     * @param login
     * @return
     */

    public boolean hasMember(String login) {
        return this.members.contains(login);
    }

    /**
     * Removes a member from the community.
     * @param login
     */

    public void removeMember(String login) {
        if (this.members.contains(login)) {
            this.members.remove(login);
        } else throw new CommunityException("Usuario não faz parte dessa comunidade.");
    }
}
