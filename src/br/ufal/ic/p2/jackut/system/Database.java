package br.ufal.ic.p2.jackut.system;

import br.ufal.ic.p2.jackut.exceptions.*;

import java.io.*;
import java.util.*;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.*;

/**
 * This class represents a simple database system for managing user accounts and sessions.
 * It provides methods for creating, finding, and managing user accounts, as well as handling
 * user sessions and data persistence.
 */

public class Database {
    ObjectMapper objectMapper = new ObjectMapper();
    private ArrayList<User> users;
    private ArrayList<Session> sessions;
    //    private ArrayList<Comunity> comunities;
    private HashMap<String, Community> communities = new HashMap<>();

    /**
     * Constructs a Database object with initial user data imported from a file if file exists.
     */

    public Database() {
        this.users = new ArrayList<>();
        this.sessions = new ArrayList<>();

        if (new File("users.json").exists()) {
            usersFromJSON();
        }

        if (new File("communities.json").exists()) {
            communitiesFromJSON();
        }
    }

    /**
     * Imports user data from a file.
     */

    public void usersFromJSON() {
        File json = new File("users.json");
        if (json.exists()) {
            try {
                this.users = objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));
            } catch (StreamReadException e) {
                throw new RuntimeException(e);
            } catch (DatabindException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Imports community data from a file.
     */

    public void communitiesFromJSON() {
        File json = new File("communities.json");
        if (json.exists()) {
            try {
                this.communities = objectMapper.readValue(json, objectMapper.getTypeFactory().constructMapType(HashMap.class, String.class, Community.class));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Shuts down the database, exporting all data to files.
     */

    public void shutdown() {
        usersToJSON();
        communitiesToJSON();
    }

    /**
     * Exports all user data to a file.
     */

    public void usersToJSON() {
        try {
            objectMapper.writeValue(new File("users.json"), users);
        } catch (IOException e) {
            throw new JsonException("Erro ao escrever arquivo de usuários.");
        }
    }

    /**
     * Exports all community data to a file.
     */

    public void communitiesToJSON() {
        try {
            objectMapper.writeValue(new File("communities.json"), communities);
        } catch (IOException e) {
            throw new JsonException("Erro ao escrever arquivo de comunidades.");
        }
    }

    /**
     * Flushes all user and session data from the database.
     */

    public void flush() {
        this.users = new ArrayList<>();
        this.communities = new HashMap<>();
        this.sessions = new ArrayList<>();
        if (new File("users.json").exists()) {
            System.out.println("users.json exists");
            new File("users.json").delete();
        }
        if (new File("communities.json").exists()) {
            System.out.println("communities.json exists");
            new File("communities.json").delete();
        }
        System.out.println("Flushed data.");
    }

    /**
     * Creates a new user account with the provided login, password, and name.
     *
     * @param login    The login name for the new user.
     * @param password The password for the new user.
     * @param name     The name of the new user.
     * @throws UserException if the login or password is invalid or if an account with the same login already exists.
     */

    public void newUser(String login, String password, String name) {
        if (login == null) {
            throw new UserException("Login inválido.");
        }

        if (password == null) {
            throw new UserException("Senha inválida.");
        }

        for (User user : this.users) {
            if (user.getLogin().equals(login)) {
                throw new UserException("Conta com esse nome já existe.");
            }
        }

        User user = new User(login, password, name, null, null, null, null, null, null, null, null);        this.users.add(user);
    }

    /**
     * Starts a new session for a user with the provided login and password.
     *
     * @param login    The login of the user to start a session for.
     * @param password The password of the user.
     * @return The ID of the new session.
     * @throws RuntimeException if the login or password is invalid.
     */

    public String startSession(String login, String password) {

        try {
            User user = findUser(login); // find user by login
            if (user.matchPassword(password)) { // if user password is equal to password
                String id = String.valueOf(this.sessions.size()); // id is equal to activeSessions
                sessions.add(new Session(user, id)); // add new session to sessions
                return id;
            } else {
                throw new UserException("Login ou senha inválidos."); // if user password is not equal to password
            }
        } catch (RuntimeException error) {
            throw new UserException("Login ou senha inválidos."); // if user not found
        }
    }

    /**
     * Finds a user by their login.
     *
     * @param login The login of the user to find.
     * @return The User object matching the login.
     * @throws RuntimeException if no user with the given login is found.
     */

    public User findUser(String login) {
        for (User user : this.users) { // for each user in users
            if (user.getLogin().equals(login)) { // if user login is equal to login
                return user;
            }
        }
        throw new UserException("Usuário não cadastrado."); // if user not found
    }

    /**
     * Retrieves a specific attribute of a user.
     *
     * @param login     The login of the user.
     * @param attribute The attribute to retrieve ("nome" for the user's name).
     * @return The value of the requested attribute.
     */

    public String getUserAttribute(String login, String attribute) {
        User user = findUser(login); // find user by login
        if (attribute.equals("nome")) {
            return user.getName();
        }

        if (user.getAttributes().isEmpty()) throw new AttributeException("Atributo não preenchido.");

        for (UserAttribute userAttribute : user.getAttributes()) {
            if (userAttribute.getName().equals(attribute)) {
                return userAttribute.getValue();
            }
        }

        throw new AttributeException("Atributo não preenchido.");
    }

    /**
     * Edits a user's profile.
     *
     * @param sessionId The ID of the session.
     * @param attribute The attribute to edit ("nome" for the user's name).
     * @param value     The new value for the attribute.
     * @throws RuntimeException if the session ID is invalid or if the attribute is invalid.
     */

    public void editProfile(String sessionId, String attribute, String value) {
        getUserBySessionId(sessionId).editAttribute(attribute, value);
    }

    /**
     * Finds a user by their session ID.
     *
     * @param sessionId The ID of the session to find the user for.
     * @return The User object matching the session ID.
     * @throws RuntimeException if no user with the given session ID is found.
     */

    public User getUserBySessionId(String sessionId) {
        Session session = findSession(sessionId);
        User user = session.user();

        if (user == null) {
            throw new UserException("Usuário não cadastrado.");
        }

        return user;
    }

    /**
     * Finds a session by its ID.
     *
     * @param sessionId The ID of the session to find.
     * @return The Session object matching the ID.
     * @throws RuntimeException if no session with the given ID is found.
     */

    public Session findSession(String sessionId) {
        for (Session session : this.sessions) { // for each session in sessions
            if (Objects.equals(session.sessionId(), sessionId)) { // if session id is equal to sessionId
                return session;
            }
        }

        throw new UserException("Usuário não cadastrado."); // if session not found
    }

    /**
     * Deletes a user account.
     *
     * @param sessionId The ID of the session.
     * @throws RuntimeException if the session ID is invalid.
     */

    public void deleteAccount(String sessionId) {
        User user = getUserBySessionId(sessionId);
        for (User friend : users) {
            if (friend.getFriends().contains(user.getLogin())) {
                friend.removeFriend(user.getLogin());
            }

            if (friend.getFans().contains(user.getLogin())) {
                friend.removeFan(user.getLogin());
            }

            if(friend. getIdols().contains(user.getLogin())) {
                friend.removeIdol(user.getLogin());
            }

            if(friend.getFlirts().contains(user.getLogin())) {
                friend.removeFlirt(user.getLogin());
            }

            if(friend.getEnemies().contains(user.getLogin())) {
                friend.removeEnemy(user.getLogin());
            }

            friend.removeMessagesFromSender(user.getLogin());

        }
        for (Community community : communities.values()) {
            if(community.getOwner().equals(user.getLogin())) {
                communities.remove(community.getName());
            }
            if (community.hasMember(user.getLogin())) {
                community.removeMember(user.getLogin());
            }
        }
        users.remove(user);
        sessions.remove(findSession(sessionId));
    }

    /**
     * Adds a friend to a user's friend list.
     *
     * @param sessionId   The ID of the session.
     * @param friendLogin The login of the friend to add.
     * @throws RuntimeException if the session ID is invalid or if the friend login is invalid.
     */

    public void addFriend(String sessionId, String friendLogin) {
        User user = getUserBySessionId(sessionId);
        User friend = findUser(friendLogin);
        if(user.hasEnemy(friendLogin) || friend.hasEnemy(user.getLogin())) throw new RelationshipException("Função inválida: " + friend.getName() + " é seu inimigo.");
        if (user.equals(friend)) {
            throw new RelationshipException("Usuário não pode adicionar a si mesmo como amigo.");
        }
        if (user.getFriends().contains(friend.getLogin()) && !friend.getFriends().contains(user.getLogin())) {
            throw new RelationshipException("Usuário já está adicionado como amigo, esperando aceitação do convite.");
        }

        if (user.getFriends().contains(friend.getLogin()) && friend.getFriends().contains(user.getLogin())) {
            throw new RelationshipException("Usuário já está adicionado como amigo.");
        }
        user.addFriend(friend.getLogin());
    }

    /**
     * Checks if two users are friends.
     *
     * @param userLogin   The login of the first user.
     * @param friendLogin The login of the second user.
     * @return True if the users are friends.
     * @throws RuntimeException if either user login is invalid.
     */

    public boolean areFriends(String userLogin, String friendLogin) {
        User user = findUser(userLogin);
        User friend = findUser(friendLogin);
        return (user.getFriends().contains(friend.getLogin()) && friend.getFriends().contains(user.getLogin()));
    }

    /**
     * Retrieves a user's friend list.
     *
     * @param login The login of the user.
     * @return A string containing the user's friend list.
     * @throws RuntimeException if the user login is invalid.
     */

    public String getFriends(String login) {
        User user = findUser(login);
        if (user.getFriends().isEmpty()) return "{}";

        StringBuilder friends = new StringBuilder();
        friends.append('{');

        for (String friendName : user.getFriends()) {
            User friend = findUser(friendName);
            if (friend.getFriends().contains(user.getLogin())) {
                friends.append(friendName);
                if (user.getFriends().indexOf(friendName) != user.getFriends().size() - 1) {
                    friends.append(",");
                }
            }
        }
        friends.append('}');
        return friends.toString();
    }

    /**
     * Sends a message from one user to another.
     *
     * @param sessionId The ID of the session.
     * @param recipient The login of the recipient.
     * @param message   The message to send.
     * @throws RuntimeException if the session ID is invalid or if the recipient login is invalid.
     */

    public void sendMessage(String sessionId, String recipient, String message) {
        User sender = getUserBySessionId(sessionId);
        User recipientUser = findUser(recipient);
        if(sender.hasEnemy(recipient) || recipientUser.hasEnemy(sender.getLogin())) throw new RelationshipException("Função inválida: " + recipientUser.getName() + " é seu inimigo.");
        if (sender.equals(recipientUser)) throw new MessageException("Usuário não pode enviar recado para si mesmo.");
        if (recipientUser != null) {
            recipientUser.addMessage(new Message(sender.getLogin(), message));
        }
    }

    /**
     * Reads a message from a user's inbox.
     *
     * @param sessionId The ID of the session.
     * @return The message.
     * @throws RuntimeException if the session ID is invalid.
     */

    public String readMessage(String sessionId) {
        User user = getUserBySessionId(sessionId);
        if (user != null) {
            return user.readMessage();
        }
        return null;
    }

    /**
     * Sends a message to a community.
     *
     * @param sessionId     The ID of the session.
     * @param communityName The name of the community.
     * @param contents      The contents of the message.
     * @throws RuntimeException if the session ID is invalid or if the community name is invalid.
     */

    public void sendCommunityMessage(String sessionId, String communityName, String contents) {
        User sender = getUserBySessionId(sessionId);
        String login = sender.getLogin();
        Message message = new Message(login, contents);

        Community community = getCommunity(communityName);
        for (String member : community.getMembers()) {
            User user = findUser(member);
            user.getPost(message);
        }
    }

    /**
     * Returns a community object.
     *
     * @param name The name of the community.
     * @return The community object.
     * @throws RuntimeException if the community name is invalid.
     */

    public Community getCommunity(String name) {
        if (communities.containsKey(name)) {
            return communities.get(name);
        } else {
            throw new CommunityException("Comunidade não existe.");
        }
    }

    /**
     * Reads a post from a user's inbox.
     *
     * @param sessionId The ID of the session.
     * @return The post.
     * @throws RuntimeException if the session ID is invalid.
     */

    public String readPosts(String sessionId) {
        User user = getUserBySessionId(sessionId);
        return user.readPost();
    }

    /**
     * Creates a new community.
     *
     * @param session     The ID of the session.
     * @param name        The name of the community.
     * @param description The description of the community.
     * @throws RuntimeException if the session ID is invalid or if the community name is invalid.
     */

    public void createComunity(String session, String name, String description) {
        String login = getUserBySessionId(session).getLogin();
        if (communities.containsKey(name)) {
            throw new CommunityException("Comunidade com esse nome já existe.");
        }

        ArrayList<String> members = new ArrayList<>();
        members.add(login);

        Community community = new Community(name, description, login, members);
        communities.put(name, community);

    }

    /**
     * Returns the description of a community.
     *
     * @param name The name of the community.
     * @return The description of the community.
     * @throws RuntimeException if the community name is invalid.
     */

    public String getCommunityDescription(String name) {
        if (communities.containsKey(name)) {
            return communities.get(name).getDescription();
        } else {
            throw new CommunityException("Comunidade não existe.");
        }
    }

    /**
     * Returns the owner of a community.
     *
     * @param name The name of the community.
     * @return The login of the owner of the community.
     * @throws RuntimeException if the community name is invalid.
     */

    public String getCommunityOwner(String name) {
        if (communities.containsKey(name)) {
            return communities.get(name).getOwner();
        } else {
            throw new CommunityException("Comunidade não existe.");
        }
    }

    /**
     * Returns a string containing the members of a community.
     *
     * @param name The name of the community.
     * @return A string containing the members of the community.
     * @throws RuntimeException if the community name is invalid.
     */

    public String getCommunityMembers(String name) {

        if (communities.containsKey(name)) {
            Community community = communities.get(name);
            return community.membersToString();

        } else {
            throw new CommunityException("Comunidade não existe.");
        }
    }

    /**
     * Adds a user to a community.
     *
     * @param session The ID of the session.
     * @param name    The name of the community.
     * @throws RuntimeException if the session ID is invalid or if the community name is invalid.
     */

    public void addToCommunity(String session, String name) {
        String member = getUserBySessionId(session).getLogin();
        if (communities.containsKey(name)) {
            Community community = communities.get(name);
            community.addMember(member);
        } else {
            throw new CommunityException("Comunidade não existe.");
        }
    }

    /**
     * Returns a string containing the communities a user is a member of.
     *
     * @param login The login of the user.
     * @return A string containing the communities the user is a member of.
     * @throws RuntimeException if the user login is invalid.
     */

    public String getUserCommunities(String login) {
        findUser(login);
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (Community community : communities.values()) {
            if (community.hasMember(login)) {
                sb.append(community.getName());
                sb.append(",");
            }
        }
        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
        }

        sb.append('}');
        System.out.println(sb.toString());
        return sb.toString();
    }

    /**
     * Adds a user as an idol of another user.
     *
     * @param session The ID of the session.
     * @param idol    The login of the idol.
     * @throws RuntimeException if the session ID is invalid or if the idol login is invalid.
     */

    public void addIdol(String session, String idol) {
        User user = getUserBySessionId(session);
        User idolUser = findUser(idol);
        if(user.hasEnemy(idol) || idolUser.hasEnemy(user.getLogin())) throw new RelationshipException("Função inválida: " + idolUser.getName() + " é seu inimigo.");
        if(Objects.equals(user.getLogin(), idol)) throw new RelationshipException("Usuário não pode ser fã de si mesmo.");
        if(user.isFanOf(idol)) throw new RelationshipException("Usuário já está adicionado como ídolo.");
        user.addIdol(idol);
        idolUser.addFan(user.getLogin());
    }

    /**
     * Checks if a user is a fan of another user.
     *
     * @param login The login of the user.
     * @param idol  The login of the idol.
     * @return True if the user is a fan of the idol.
     * @throws RuntimeException if either user login is invalid.
     */

    public boolean isFan(String login, String idol) {
        User user = findUser(login);
        findUser(idol);
        return user.isFanOf(idol);
    }

    /**
     * Returns the login name of the user.
     *
     * @return The login name of the user.
     */

    public String getFans(String login) {
        User user = findUser(login);
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (String fan : user.getFans()) {
            sb.append(fan).append(",");
        }
        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append('}');
        return sb.toString();
    }

    /**
     * Adds a user as a flirt of another user.
     *
     * @param session The ID of the session.
     * @param flirt    The login of the flirt.
     * @throws RuntimeException if the session ID is invalid or if the flirt login is invalid.
     */

    public void addFlirt (String session, String flirt) {
        User user = getUserBySessionId(session);
        User flirted = findUser(flirt);
        if(user.hasEnemy(flirt) || flirted.hasEnemy(user.getLogin())) throw new RelationshipException("Função inválida: " + flirted.getName() + " é seu inimigo.");
        if (user.getLogin().equals(flirt)) throw new RelationshipException("Usuário não pode ser paquera de si mesmo.");
        if(user.hasFlirt(flirt)) throw new RelationshipException("Usuário já está adicionado como paquera.");
        user.addFlirt(flirt);
        if(flirted.hasFlirt(user.getLogin())) {
            user.addMessage(new Message("Sistema", flirted.getName() + " é seu paquera - Recado do Jackut." ));
            flirted.addMessage(new Message("Sistema", user.getName() + " é seu paquera - Recado do Jackut." ));
        }
    }

    /**
     * Checks if a user is a flirt of another user.
     *
     * @param login The login of the user.
     * @param flirt  The login of the flirt.
     * @return True if the user is a flirt of the flirt.
     * @throws RuntimeException if either user login is invalid.
     */

    public boolean isFlirt (String session, String flirt) {
        User user = getUserBySessionId(session);
        findUser(flirt);
        return user.hasFlirt(flirt);
    }

    /**
     * Returns the login name of the user.
     *
     * @return The login name of the user.
     */

    public String getFlirts (String session) {
        User user = getUserBySessionId(session);
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (String flirt : user.getFlirts()) {
            sb.append(flirt).append(",");
        }
        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append('}');
        return sb.toString();
    }

    /**
     * Adds a user as an enemy of another user.
     *
     * @param session The ID of the session.
     * @param enemy    The login of the enemy.
     * @throws RuntimeException if the session ID is invalid or if the enemy login is invalid.
     */

    public void addEnemy(String session, String enemy) {
        User user = getUserBySessionId(session);
        User enemyUser = findUser(enemy);
        if (user.getLogin().equals(enemy)) throw new RelationshipException("Usuário não pode ser inimigo de si mesmo.");
        if(user.hasEnemy(enemy)) throw new RelationshipException("Usuário já está adicionado como inimigo.");
        user.addEnemy(enemy);
    }
}