package br.ufal.ic.p2.jackut.system;

import java.util.ArrayList;
import java.util.Objects;

import br.ufal.ic.p2.jackut.exceptions.AttributeException;
import br.ufal.ic.p2.jackut.exceptions.MessageException;
import br.ufal.ic.p2.jackut.exceptions.RelationshipException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * This class represents a user account in the system.
 * It contains the user's login name, password, and name.
 */

@JsonIdentityInfo(
        generator = ObjectIdGenerators.UUIDGenerator.class,
        property = "@json_id"
)

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
    private final UserList friends;
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

    @JsonManagedReference
    private final UserList fans;

    /**
     * The user's idols.
     */
    private final UserList idols;

    /**
     * The user's flirts.
     */
    private final UserList flirts;

    /**
     * The user's enemies.
     */
    private final UserList enemies;

    private ArrayList<Community> communities = new ArrayList<>();

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
            @JsonProperty("friends") UserList friends,
            @JsonProperty("inbox") ArrayList<Message> inbox,
            @JsonProperty("timeline") ArrayList<Message> timeline,
            @JsonProperty("fans") UserList fans,
            @JsonProperty("idols") UserList idols,
            @JsonProperty("flirts") UserList flirts,
            @JsonProperty("enemies") UserList enemies,
            @JsonProperty("communities") ArrayList<Community> communities) {
        this.login = username;
        this.password = password;
        this.name = name;
        this.attributes = attributes != null ? attributes : new ArrayList<>();
        this.friends = friends != null ? friends : new UserList();
        this.inbox = inbox != null ? inbox : new ArrayList<Message>();
        this.timeline = timeline != null ? timeline : new ArrayList<>();
        this.fans = fans != null ? fans : new UserList();
        this.idols = idols != null ? idols : new UserList();
        this.flirts = flirts != null ? flirts : new UserList();
        this.enemies = enemies != null ? enemies : new UserList();
        this.communities = communities != null ? communities : new ArrayList<>();
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

    public String getAttribueValue(String attribute) {
        if(attribute.equals("nome")) return name;
        if((attributes.isEmpty())) throw new AttributeException("Atributo não preenchido.");

        for (UserAttribute userAttribute : attributes) {
            if (userAttribute.getName().equals(attribute)) {
                return userAttribute.getValue();
            }
        }

        throw new AttributeException("Atributo não preenchido.");
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
     * @param user The user to be added.
     */

    public void addFriend(User user) {
        if (friends.contains(user) && user.hasOnFriendList(this))
            throw new RelationshipException("Usuário já está adicionado como amigo.");
        if (friends.contains(user) && !user.hasOnFriendList(this))
            throw new RelationshipException("Usuário já está adicionado como amigo, esperando aceitação do convite.");
        if (user.equals(this)) throw new RelationshipException("Usuário não pode adicionar a si mesmo como amigo.");
        if (enemies.contains(user) || user.isEnemyOf(user))
            throw new RelationshipException("Função inválida: " + user.getName() + " é seu inimigo.");
        friends.add(user);
    }

    /**
     * Returns a list of the user's friends.
     *
     * @return A list of the user's friends.
     */

    public UserList getFriends() {
        return friends;
    }

    public String printFriends() {
        return friends.printAll();
    }

    /**
     * Returns true if the provided user is a friend of this user.
     *
     * @param user The user to be checked.
     * @return True if the provided user is a friend of this user.
     */

    public boolean hasOnFriendList(User user) {
        return friends.contains(user);
    }

    public boolean isFriendOf(User user) {
        return friends.contains(user) && user.hasOnFriendList(this);
    }

    /**
     * Adds a message to the user's inbox.
     *
     * @param message The message to be added.
     */

    public void addMessage(UserMessage message) {
        if(enemies.contains(message.sender()) || message.sender().isEnemyOf(this)) throw new RelationshipException("Função inválida: " + name + " é seu inimigo.");
        if(message.sender().equals(this)) throw new RuntimeException("Usuário não pode enviar recado para si mesmo.");
        inbox.add(message);
    }

    /**
     * Returns the first message in the user's inbox.
     * The message is removed from the inbox.
     *
     * @return The first message in the user's inbox.
     */

    public String readMessage() {
        if (inbox.isEmpty()) throw new MessageException("Não há recados.");
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

    public void addFan(User fan) {
        fans.add(fan);
    }

    /**
     * Returns a list of the user's fans.
     *
     * @return A list of the user's fans.
     */

    public UserList getFans() {
        return fans;
    }

    public String printFans() {
        return fans.printAll();
    }

    /**
     * Returns true if the provided user is a fan of this user.
     *
     * @param idol The user to be checked.
     * @return True if the provided user is a fan of this user.
     */

    public boolean isFanOf(User idol) {
        return this.idols.contains(idol);
    }

    /**
     * Adds an idol to the user's idol list.
     *
     * @param idol The user to be added.
     */

    public void addIdol(User idol) {
        if(idol.equals(this)) throw new RelationshipException("Usuário não pode ser ídolo de si mesmo.");
        if(idols.contains(idol)) throw new RelationshipException("Usuário já está adicionado como ídolo.");
        if(enemies.contains(idol) || idol.isEnemyOf(this)) throw new RelationshipException("Função inválida: " + idol.getName() + " é seu inimigo.");
        idols.add(idol);
        idol.addFan(this);
    }

    /**
     * Returns a list of the user's idols.
     *
     * @return A list of the user's idols.
     */

    public UserList getIdols() {
        return idols;
    }

    /**
     * Adds a flirt to the user's flirt list.
     *
     * @param flirt The user to be added.
     */

    public void addFlirt(User flirt) {
        if(flirt.equals(this)) throw new RelationshipException("Usuário não pode ser paquera de si mesmo.");
        if(flirts.contains(flirt)) throw new RelationshipException("Usuário já está adicionado como paquera.");
        if(enemies.contains(flirt) || flirt.isEnemyOf(this)) throw new RelationshipException("Função inválida: " + flirt.getName() + " é seu inimigo.");
        flirts.add(flirt);
        if(flirt.hasFlirt(this)) {
            inbox.add(
                    new SystemMessage(flirt.getName() + " é seu paquera - Recado do Jackut.")
            );
        }
    }

    /**
     * Returns a list of the user's flirts.
     *
     * @return A list of the user's flirts.
     */

    public UserList getFlirts() {
        return flirts;
    }

    public String printFlirts() {
        return flirts.printAll();
    }

    /**
     * Returns true if the provided user is a flirt of this user.
     *
     * @param flirt The user to be checked.
     * @return True if the provided user is a flirt of this user.
     */

    public boolean hasFlirt(User flirt) {
        return this.flirts.contains(flirt);
    }

    /**
     * Adds an enemy to the user's enemy list.
     *
     * @param enemy The user to be added.
     */

    public void addEnemy(User enemy) {
        if(enemies.contains(enemy)) throw new RelationshipException("Usuário já está adicionado como inimigo.");
        if(enemy.equals(this)) throw new RelationshipException("Usuário não pode ser inimigo de si mesmo.");
        enemies.add(enemy);
    }

    /**
     * Returns a list of the user's enemies.
     *
     * @return A list of the user's enemies.
     */

    public UserList getEnemies() {
        return enemies;
    }


    /**
     * Returns true if the provided user is an enemy of this user.
     *
     * @param enemy The user to be checked.
     * @return True if the provided user is an enemy of this user.
     */

    public boolean isEnemyOf(User enemy) {
        return this.enemies.contains(enemy);
    }

    /**
     * Removes the provided friend from the user's friend list.
     *
     * @param friend The friend to be removed.
     */

    public void removeFriend(User friend) {
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

    public String printCommunities() {
        if(communities.isEmpty()) {
            return "{}";
        }
            StringBuilder sb = new StringBuilder();
            sb.append('{');

            for (Community community : communities) {
                sb.append(community.toString());
                sb.append(',');
            }

            if (sb.length() > 1) {
                sb.deleteCharAt(sb.length() - 1);
            }

            sb.append('}');

            return sb.toString();
    }

    public void removeMessagesFromSender(User sender) {
        for (Message message : inbox) {
            if (message.sender().equals(sender)) {
                inbox.remove(message);
                return;
            }
        }
    }

    public void receivePost(Message message) {
        timeline.add(message);
    }

    public void deleteAccount() {
        for (User user : friends) {
            user.removeFriend(this);
        }
        for (User user : fans) {
            user.removeFan(this.login);
        }
        for (User user : idols) {
            user.removeIdol(this.login);
        }
        for (User user : flirts) {
            user.removeFlirt(this.login);
        }
        for (User user : enemies) {
            user.removeEnemy(this.login);
        }
    }
}
