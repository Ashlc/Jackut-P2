package br.ufal.ic.p2.jackut.system;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private final String login;
    private final String password;
    private final String name;

    public User(String username, String password, String name) {

        this.login = username;
        this.password = password;
        this.name = name;
    }

    public String getLogin() {
        return this.login;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean matchPassword(String incoming) {
        return Objects.equals(incoming, this.password);
    }
}
