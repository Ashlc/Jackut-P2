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
    private final ArrayList<String> friends;
    /**
     * The user's inbox.
     */
    private final ArrayList<Message> inbox;

    /**
     * The user's timeline.
     */

    private final ArrayList<Message> timeline;

    /**
     * The user's fans.
     */

    private ArrayList<String> fans;

    /**
     * The user's idols.
     */
    private ArrayList<String> idols = new ArrayList<>();

    /**
     * The user's flirts.
     */
    private ArrayList<String> flirts = new ArrayList<>();

    /**
     * The user's enemies.
     */
    private ArrayList<String> enemies = new ArrayList<>();

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
            @JsonProperty("inbox") ArrayList<Message> inbox,
            @JsonProperty("timeline") ArrayList<Message> timeline,
            @JsonProperty("fans") ArrayList<String> fans,
            @JsonProperty("idols") ArrayList<String> idols,
            @JsonProperty("flirts") ArrayList<String> flirts,
            @JsonProperty("enemies") ArrayList<String> enemies)
    {
        this.login = username;
        this.password = password;
        this.name = name;
        this.attributes = attributes != null ? attributes : new ArrayList<>();
        this.friends = friends != null ? friends : new ArrayList<>();
        this.inbox = inbox != null ? inbox : new ArrayList<>();
        this.timeline = timeline != null ? timeline : new ArrayList<>();
        this.fans = fans != null ? fans : new ArrayList<>();
        this.idols = idols != null ? idols : new ArrayList<>();
        this.flirts = flirts != null ? flirts : new ArrayList<>();
        this.enemies = enemies != null ? enemies : new ArrayList<>();
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
        if (inbox.isEmpty()) throw new RuntimeException("Não há recados.");
        Message message = inbox.get(0);
        inbox.remove(0);
        return message.message();
    }

    /**
     * Returns the first message in the user's inbox.
     * The message is removed from the inbox.
     *
     * @return The first message in the user's inbox.
     */

    public ArrayList<Message> getInbox() {
        return inbox;
    }

    /**
     * Adds a message to the user's timeline.
     *
     * @param message The message to be added.
     */

    public void getPost(Message message) {
        timeline.add(message);
    }

    /**
     * Returns the first message in the user's inbox.
     * The message is removed from the inbox.
     *
     * @return The first message in the user's inbox.
     */

    public String readPost() {
        if (timeline.isEmpty()) throw new RuntimeException("Não há mensagens.");
        Message message = timeline.get(0);
        timeline.remove(0);
        return message.message();
    }

    /**
     * Returns the first message in the user's inbox.
     * The message is removed from the inbox.
     *
     * @return The first message in the user's inbox.
     */

    public ArrayList<Message> getTimeline() {
        return timeline;
    }

    /**
     * Adds a fan to the user's fan list.
     *
     * @param fan The user to be added.
     */

    public void addFan(String fan) {
        fans.add(fan);
    }

    /**
     * Returns a list of the user's fans.
     *
     * @return A list of the user's fans.
     */

    public ArrayList<String> getFans() {
        return fans;
    }

    /**
     * Returns true if the provided user is a fan of this user.
     *
     * @param idol The user to be checked.
     * @return True if the provided user is a fan of this user.
     */

    public boolean isFanOf(String idol) {
        return this.idols.contains(idol);
    }

    /**
     * Adds an idol to the user's idol list.
     *
     * @param idol The user to be added.
     */

    public void addIdol(String idol) {
        idols.add(idol);
    }

    /**
     * Returns a list of the user's idols.
     *
     * @return A list of the user's idols.
     */

    public ArrayList<String> getIdols() {
        return idols;
    }

    /**
     * Adds a flirt to the user's flirt list.
     *
     * @param flirt The user to be added.
     */

    public void addFlirt(String flirt) {
        flirts.add(flirt);
    }

    /**
     * Returns a list of the user's flirts.
     *
     * @return A list of the user's flirts.
     */

    public ArrayList<String> getFlirts() {
        return flirts;
    }

    /**
     * Returns true if the provided user is a flirt of this user.
     *
     * @param flirt The user to be checked.
     * @return True if the provided user is a flirt of this user.
     */

    public boolean hasFlirt(String flirt) {
        return this.flirts.contains(flirt);
    }

    /**
     * Adds an enemy to the user's enemy list.
     *
     * @param enemy The user to be added.
     */

    public void addEnemy(String enemy) {
        enemies.add(enemy);
    }

    /**
     * Returns a list of the user's enemies.
     *
     * @return A list of the user's enemies.
     */

    public ArrayList<String> getEnemies() {
        return enemies;
    }

    /**
     * Returns true if the provided user is an enemy of this user.
     *
     * @param enemy The user to be checked.
     * @return True if the provided user is an enemy of this user.
     */

    public boolean hasEnemy(String enemy) {
        return this.enemies.contains(enemy);
    }

    /**
     * Removes the provided friend from the user's friend list.
     *
     * @param friend The friend to be removed.
     */

    public void removeFriend(String friend) {
        friends.remove(friend);
    }

    /**
     * Removes the provided fan from the user's fan list.
     *
     * @param fan The fan to be removed.
     */

    public void removeFan(String fan) {
        fans.remove(fan);
    }

    /**
     * Removes the provided idol from the user's idol list.
     *
     * @param idol The idol to be removed.
     */

    public void removeIdol(String idol) {
        idols.remove(idol);
    }

    /**
     * Removes the provided flirt from the user's flirt list.
     *
     * @param flirt The flirt to be removed.
     */

    public void removeFlirt(String flirt) {
        flirts.remove(flirt);
    }

    /**
     * Removes the provided enemy from the user's enemy list.
     *
     * @param enemy The enemy to be removed.
     */

    public void removeEnemy(String enemy) {
        enemies.remove(enemy);
    }

    /**
     * Removes all messages from the user's inbox.
     */

    public void removeMessagesFromSender(String sender) {
        for (Message message : inbox) {
            if (message.sender().equals(sender)) {
                inbox.remove(message);
                return;
            }
        }
    }
}
