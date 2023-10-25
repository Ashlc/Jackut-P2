package br.ufal.ic.p2.jackut.system;

import br.ufal.ic.p2.jackut.exceptions.CommunityException;
import com.fasterxml.jackson.annotation.*;

/**
 * This class represents a community in the system.
 * It contains the community's name, description, owner and members.
 */

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "name"
)
public class Community {
    private final String name;
    private final String description;
    private final User owner;
    private final UserList members;

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
            @JsonProperty("owner") User owner,
            @JsonProperty("members") UserList members) {

        this.owner = owner;
        this.name = name;
        this.description = description;
        this.members = members;
        owner.addCommunity(this);
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
     * @return the owner of the community.
     */

    public User getOwner() {
        return this.owner;
    }

    @JsonIgnore
    public String getOwnerLogin() {
        return this.owner.getLogin();
    }

    /**
     * Returns the members of the community.
     * @return
     */

    public UserList getMembers() {
        return this.members;
    }

    /**
     * Returns the members of the community as a string.
     * @return
     */

    public String membersToString() {
        return this.members.printAll();
    }

    /**
     * Adds a member to the community.
     * @param user
     */

    public void addMember(User user) {
        if (!this.members.contains(user)) {
            this.members.add(user);
            user.addCommunity(this);
        } else throw new CommunityException("Usuario já faz parte dessa comunidade.");
    }

    /**
     * Returns true if the community has the provided member.
     * @param user
     * @return
     */

    public boolean hasMember(User user) {
        return this.members.contains(user);
    }

    /**
     * Removes a member from the community.
     * @param user
     */

    public void removeMember(User user) {
        if (this.members.contains(user)) {
            this.members.remove(user);
        } else throw new CommunityException("Usuario não faz parte dessa comunidade.");
    }

    public void sendPost (User sender, String contents) {
        UserMessage post = new UserMessage(sender, contents);
        for (User user : this.members) {
            user.receivePost(post);
        }
    }

    public void deleteCommunity() {
        for (User user : this.members) {
            user.removeCommunity(this);
        }
    }

}
