package br.ufal.ic.p2.jackut.system;

import java.util.ArrayList;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a user account in the system.
 * It contains the user's login name, password, and name.
 */

public class User {
    /**
     * The login name of the user.
     */
    private final String login;
    /**
     * The password of the user.
     */
    private final String password;
    /**
     * The name of the user.
     */
    private final String name;
    /**
     * The user's attributes.
     */
    private final ArrayList<UserAttribute> attributes;
    /**
     * The user's friends.
     */
    private ArrayList<String> friends;
    /**
     * The user's inbox.
     */
    private ArrayList<Message> inbox;

    /**
     * Constructs a User object with the provided login, password, and name.
     *
     * @param username The login name for the new user.
     * @param password The password for the new user.
     * @param name     The name of the new user.
     */

    @JsonCreator
    public User(
            @JsonProperty("username") String username,
            @JsonProperty("password") String password,
            @JsonProperty("name") String name,
            @JsonProperty("attributes") ArrayList<UserAttribute> attributes,
            @JsonProperty("friends") ArrayList<String> friends,
            @JsonProperty("inbox") ArrayList<Message> inbox)
    {
        this.login = username;
        this.password = password;
        this.name = name;
        if(attributes != null) this.attributes = attributes;
        else this.attributes = new ArrayList<>();
        if(friends != null) this.friends = friends;
        else this.friends = new ArrayList<>();
        if(inbox != null) this.inbox = inbox;
        else this.inbox = new ArrayList<>();
    }

    /**
     * Returns the login name of the user.
     *
     * @return The login name of the user.
     */

    public String getLogin() {
        return this.login;
    }

    /**
     * Returns the name of the user.
     *
     * @return The name of the user.
     */

    public String getName() {
        return this.name;
    }

    /**
     * Returns the password of the user.
     *
     * @return The password of the user.
     */

    public String getPassword() {
        return this.password;
    }

    /**
     * Returns true if the provided password matches the user's password.
     *
     * @param incoming The password to be checked.
     * @return True if the provided password matches the user's password.
     */

    public boolean matchPassword(String incoming) {
        return Objects.equals(incoming, this.password);
    }

    /**
     * Adds an attribute to the user's profile.
     *
     * @param attribute The attribute to be added.
     * @param value     The value of the attribute.
     */

    public void addAttribute(String attribute, String value) {
        attributes.add(new UserAttribute(attribute, value));
    }

    /**
     * Returns the value of the provided attribute.
     *
     * @param attribute The attribute to be checked.
     * @return The value of the provided attribute.
     */

    public void editAttribute(String attribute, String value) {
        for (UserAttribute userAttribute : attributes) {
            if (userAttribute.getName().equals(attribute)) {
                userAttribute.setValue(value);
                return;
            }
        }
        addAttribute(attribute, value);
    }

    /**
     * Returns the value of the provided attribute.
     *
     * @return The value of the provided attribute.
     */

    public ArrayList<UserAttribute> getAttributes() {
        return attributes;
    }

    /**
     * Exports attributes to be saved in a file.
     */

    public ArrayList<UserAttribute> exportAttributes() {
        return attributes;
    }

    /**
     * Adds a friend to the user's friend list.
     *
     * @param friend The user to be added.
     */

    public void addFriend(String friend) {
        friends.add(friend);
    }

    /**
     * Returns a list of the user's friends.
     *
     * @return A list of the user's friends.
     */

    public ArrayList<String> getFriends() {
        return friends;
    }

    /**
     * Returns true if the provided user is a friend of this user.
     *
     * @param friend The user to be checked.
     * @return True if the provided user is a friend of this user.
     */

    public boolean isFriend(String friend) {
        return friends.contains(friend);
    }

    /**
     * Adds a message to the user's inbox.
     *
     * @param message The message to be added.
     */

    public void addMessage(Message message) {
        inbox.add(message);
    }

    /**
     * Returns the first message in the user's inbox.
     * The message is removed from the inbox.
     *
     * @return The first message in the user's inbox.
     */

    public String readMessage() {
        if(inbox.isEmpty()) throw new RuntimeException("Não há recados.");
        Message message = inbox.get(0);
        inbox.remove(0);
        return message.message();
    }

    public ArrayList<Message> getInbox() {
        return inbox;
    }
}
