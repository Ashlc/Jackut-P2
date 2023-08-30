package br.ufal.ic.p2.jackut.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * This class represents a user account in the system.
 * It contains the user's login name, password, and name.
 */

public class User implements Serializable {
    private final String login;
    private final String password;
    private final String name;
    private ArrayList<UserAttribute> attributes;
    private ArrayList<User> friends;
    private String friendImport;

    /**
     * Constructs a User object with the provided login, password, and name.
     *
     * @param username The login name for the new user.
     * @param password The password for the new user.
     * @param name     The name of the new user.
     */

    public User(String username, String password, String name, ArrayList<UserAttribute> attributes, String friendImport){

        this.login = username;
        this.password = password;
        this.name = name;
        if(attributes != null) this.attributes = attributes;
        else this.attributes = new ArrayList<>();
        this.friends = new ArrayList<>();
        this.friendImport = friendImport;
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

    public void addAttribute(String attribute, String value) {
        attributes.add(new UserAttribute(attribute, value));
    }

    public void editAttribute(String attribute, String value) {
        for (UserAttribute userAttribute : attributes) {
            if (userAttribute.getAttributeName().equals(attribute)) {
                userAttribute.setValue(value);
                return;
            }
        }

        if(!value.isEmpty()) addAttribute(attribute, value);

        else throw new RuntimeException("Atributo não preenchido.");
    }

    public ArrayList<UserAttribute> getAttributes() {
        if(attributes.isEmpty()) throw new RuntimeException("Atributo não preenchido.");
        return attributes;
    }

    public ArrayList<UserAttribute> exportAttributes() {
        return attributes;
    }


    public void addFriend(User friend) {
        friends.add(friend);
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public boolean isFriend(User friend) {
        return friends.contains(friend);
    }

    public String getFriendImport() {
        return friendImport;
    }
}
