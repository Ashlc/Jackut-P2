package br.ufal.ic.p2.jackut.system;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class represents a user account in the system.
 * It contains the user's login name, password, and name.
 */

public class User implements Serializable {
    private final String login;
    private final String password;
    private final String name;

    /**
     * Constructs a User object with the provided login, password, and name.
     *
     * @param username The login name for the new user.
     * @param password The password for the new user.
     * @param name     The name of the new user.
     */

    public User(String username, String password, String name) {

        this.login = username;
        this.password = password;
        this.name = name;
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
}
