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
    private ArrayList<User> users;
    private ArrayList<Session> sessions;
    //    private ArrayList<Comunity> comunities;
    private HashMap<String, Community> communities = new HashMap<>();

    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Constructs a Database object with initial user data imported from a file.
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

    public void shutdown() {
        usersToJSON();
        communitiesToJSON();
//        exportUsers();
    }

    public void usersToJSON() {
        try {
            objectMapper.writeValue(new File("users.json"), users);
        } catch (IOException e) {
            throw new JsonException("Erro ao escrever arquivo de usuários.");
        }
    }

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

    public void communitiesToJSON() {
        try {
            objectMapper.writeValue(new File("communities.json"), communities);
        } catch (IOException e) {
            throw new JsonException("Erro ao escrever arquivo de comunidades.");
        }
    }

    public void communitiesFromJSON() {
        File json = new File("communities.json");
        if (json.exists()) {
            try {
                this.communities = objectMapper.readValue(json, objectMapper.getTypeFactory().constructMapType(HashMap.class, String.class, Community.class));
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
     * Flushes all user and session data from the database.
     */

    public void flush() {
        this.users = new ArrayList<>();
        this.sessions = new ArrayList<>();
        if (new File("users.json").exists()) {
            new File("users.json").delete();
        }
        if (new File("communities.json").exists()) {
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

        User user = new User(login, password, name, null, null, null);
        this.users.add(user);
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
     * Adds a friend to a user's friend list.
     *
     * @param sessionId   The ID of the session.
     * @param friendLogin The login of the friend to add.
     * @throws RuntimeException if the session ID is invalid or if the friend login is invalid.
     */

    public void addFriend(String sessionId, String friendLogin) {
        User user = getUserBySessionId(sessionId);
        User friend = findUser(friendLogin);
        if (user.equals(friend)) {
            throw new UserException("Usuário não pode adicionar a si mesmo como amigo.");
        }
        if (user.getFriends().contains(friend.getLogin()) && !friend.getFriends().contains(user.getLogin())) {
            throw new UserException("Usuário já está adicionado como amigo, esperando aceitação do convite.");
        }

        if (user.getFriends().contains(friend.getLogin()) && friend.getFriends().contains(user.getLogin())) {
            throw new UserException("Usuário já está adicionado como amigo.");
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
        if (sender.equals(recipientUser)) throw new MessageException("Usuário não pode enviar recado para si mesmo.");
        if (recipientUser != null) {
            recipientUser.addMessage(new Message(sender.getLogin(), recipient, message));
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

    public void createComunity(String session, String name, String description) {
        String login = getUserBySessionId(session).getLogin();
        if (communities.containsKey(name)) {
            throw new CommunityException("Comunidade com esse nome já existe.");
        }

        Community community = new Community(name, description, login, new ArrayList<>());
        communities.put(name, community);

    }

    public String getCommunityDescription(String name) {
        if (communities.containsKey(name)) {
            return communities.get(name).getDescription();
        } else {
            throw new CommunityException("Comunidade não existe.");
        }
    }

    public String getCommunityOwner(String name) {
        if (communities.containsKey(name)) {
            return communities.get(name).getOwner();
        } else {
            throw new CommunityException("Comunidade não existe.");
        }
    }

    public String getCommunityMembers(String name) {

        if (communities.containsKey(name)) {
            Community community = communities.get(name);
            return community.membersToString();

        } else {
            throw new CommunityException("Comunidade não existe.");
        }
    }
}