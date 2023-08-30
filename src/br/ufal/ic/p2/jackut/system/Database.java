package br.ufal.ic.p2.jackut.system;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a simple database system for managing user accounts and sessions.
 * It provides methods for creating, finding, and managing user accounts, as well as handling
 * user sessions and data persistence.
 */

public class Database {
    private ArrayList<User> users;
    private ArrayList<Session> sessions;
    private int activeSessions = 0;


    /**
     * Constructs a Database object with initial user data imported from a file.
     */

    public Database() {
        this.users = (ArrayList<User>) importUsers("data.txt");
        this.sessions = new ArrayList<>();

        /*for(User user : this.users) {
            if(user.getFriendImport() == null) {

            }
        }*/

    }

    public static List<User> importUsers(String filePath) {
        List<User> data = new ArrayList<>();
        ArrayList<String> friendlist = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 3) {
                    String login = parts[0];
                    String name = parts[1];
                    String password = parts[2];

                    int i;
                    ArrayList<UserAttribute> attributes = new ArrayList<>();
                    for (i = 3; i < parts.length; i++) {
                        if (parts[i].equals("{")) {
                            break;
                        }
                        String[] attributeParts = parts[i].split(";");
                        if (attributeParts.length == 2) {
                            String attributeName = attributeParts[0];
                            String attributeValue = attributeParts[1];
                            attributes.add(new UserAttribute(attributeName, attributeValue));
                        }
                    }

                    StringBuilder friendsLogin = new StringBuilder();
                    for (i = i+1; i < parts.length; i++) {
                        if (parts[i].equals("}")) {
                            break; // Stop the loop when '}' is encountered
                        }
                        friendsLogin.append(parts[i]);
                    }

                    String friends = friendsLogin.toString();

                    data.add(new User(login, password, name, attributes, friends));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    public void shutdown() {
        exportUsers();
    }

    /**
     * Exports all user data to a file.
     */

    private String serializeAttributes(ArrayList<UserAttribute> attributes) {
        StringBuilder serialized = new StringBuilder();
        for (UserAttribute attribute : attributes) {
            serialized.append(attribute.getAttributeName())
                    .append(':')
                    .append(attribute.getValue())
                    .append(';');
        }
        return serialized.toString();
    }

    private String serializeFriends(ArrayList<User> friends) {
        StringBuilder serialized = new StringBuilder();
        for (User friend : friends) {
            serialized.append(friend.getLogin())
                    .append(';');
        }
        return serialized.toString();
    }

    public void exportUsers() {

        try (FileWriter fileWriter = new FileWriter("data.txt")) {
            for (User user : this.users) {
                fileWriter.write(
                        user.getLogin() + ';' +
                                user.getName() + ';' +
                                user.getPassword() + ';' +
                                serializeAttributes(user.exportAttributes()) + ';' +
                                user.getFriends()  +
                                System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Flushes all user and session data from the database.
     */


    public void flush() {
        this.users = new ArrayList<>(); // flush users
        this.sessions = new ArrayList<>(); // flush sessions
        System.out.println("Flushed data.");
    }

    /**
     * Creates a new user account with the provided login, password, and name.
     *
     * @param login    The login name for the new user.
     * @param password The password for the new user.
     * @param name     The name of the new user.
     * @throws RuntimeException if the login or password is invalid or if an account with the same login already exists.
     */

    public void newUser(String login, String password, String name){
        if(login == null) {
            throw new RuntimeException("Login inválido.");
        }

        if(password == null) {
            throw new RuntimeException("Senha inválida.");
        }

        for (User user : this.users) {
            if (user.getLogin().equals(login)) {
                throw new RuntimeException("Conta com esse nome já existe.");
            }
        }

        User user = new User(login, password, name, null, null);
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

        throw new RuntimeException("Usuário não cadastrado."); // if user not found
    }

    /**
     * Starts a new session for a user with the provided login and password.
     *
     * @param login    The login of the user to start a session for.
     * @param password The password of the user.
     * @return The ID of the new session.
     * @throws RuntimeException if the login or password is invalid.
     */

    public String startSession(String login, String password){
        /**
         * Starts a new session for a user.
         * @param login The user's login.
         * @param password The user's password.
         * @return The session id.
         */

        try {
            User user = findUser(login); // find user by login
            if (user.matchPassword(password)) { // if user password is equal to password
                String id = String.valueOf(activeSessions); // id is equal to activeSessions
                sessions.add(new Session(user, id)); // add new session to sessions
                activeSessions++;
                return id;
            } else {
                throw new RuntimeException("Login ou senha inválidos."); // if user password is not equal to password
            }
        } catch (RuntimeException error) {
            throw new RuntimeException("Login ou senha inválidos."); // if user not found
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
        if(attribute.equals("nome")) {
            return user.getName();
        }

        for (UserAttribute userAttribute : user.getAttributes()) { // for each userAttribute in user attributes
            if (userAttribute.getAttributeName().equals(attribute)) {
                return userAttribute.getValue();
            }
        }

        throw new RuntimeException("Atributo não preenchido."); // if userAttribute not found
    }

    public void editProfile(String sessionId, String attribute, String value) {
        getUserBySessionId(sessionId).editAttribute(attribute, value);
    }

    public Session findSession(String sessionId) {
        for (Session session : this.sessions) { // for each session in sessions
            if (Objects.equals(session.getSessionId(), sessionId)) { // if session id is equal to sessionId
                return session;
            }
        }

        throw new RuntimeException("Usuário não cadastrado."); // if session not found
    }

    public User getUserBySessionId(String sessionId) {
        Session session = findSession(sessionId);
        User user = session.getUser();

        if(user == null) {
            throw new RuntimeException("Usuário não cadastrado.");
        }

        return user;
    }

    public void addFriend(String sessionId, String friendLogin) {
        User user = getUserBySessionId(sessionId);
        User friend = findUser(friendLogin);
        if(user.equals(friend)) {
            throw new RuntimeException("Usuário não pode adicionar a si mesmo como amigo.");
        }
        if(user.getFriends().contains(friend) && !friend.getFriends().contains(user)) {
            throw new RuntimeException("Usuário já está adicionado como amigo, esperando aceitação do convite.");
        }

        if(user.getFriends().contains(friend) && friend.getFriends().contains(user)) {
            throw new RuntimeException("Usuário já está adicionado como amigo.");
        }
        user.addFriend(friend);
    }

    public boolean areFriends(String userLogin, String friendLogin) {
        User user = findUser(userLogin);
        User friend = findUser(friendLogin);
        return (user.getFriends().contains(friend) && friend.getFriends().contains(user));
    }

    public String getFriends(String login) {
        User user = findUser(login);
        if (user.getFriends().isEmpty()) return "{}";

        StringBuilder friends = new StringBuilder();
        friends.append('{');

        for (User friend : user.getFriends()) {
            if(friend.getFriends().contains(user)){
                friends.append(friend.getLogin());
                if(user.getFriends().indexOf(friend) != user.getFriends().size() - 1) {
                    friends.append(",");
                }
            }
        }
        friends.append('}');
        return friends.toString();
    }


}
