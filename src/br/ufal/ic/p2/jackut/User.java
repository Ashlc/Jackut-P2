package br.ufal.ic.p2.jackut;

import java.util.Objects;

public class User {
    private String login;
    private String password;
    private String name;

    public User(String username, String password, String displayName) {
        this.login = username;
        this.password = password;
        this.name = displayName;
    }

    public String getLogin() {
        return this.login;
    }

    public String getName() {
        return this.name;
    }

    public boolean matchPassword(String incoming) {
        return Objects.equals(incoming, this.password);
    }
}
