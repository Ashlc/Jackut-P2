package br.ufal.ic.p2.jackut.system;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Database {
    private ArrayList<User> users;
    private ArrayList<Session> sessions;
    private int activeSessions = 0;

    public Database() {
        this.users = (ArrayList<User>) importUsers("data.txt");
        this.sessions = new ArrayList<>();
    }

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

        User user = new User(login, password, name);
        this.users.add(user);
    }

    public User findUser(String login) {
        for (User user : this.users) {
            if (user.getLogin().equals(login)) {

                return user;
            }
        }

        throw new RuntimeException("Usuário não cadastrado.");
    }

    public int startSession(String login, String password){
        try {
            User user = findUser(login);
            if (user.matchPassword(password)) {
                int id = activeSessions;
                sessions.add(new Session(user, id));
                activeSessions++;
                return id;
            } else {
                throw new RuntimeException("Login ou senha inválidos.");
            }
        } catch (RuntimeException error) {
            throw new RuntimeException("Login ou senha inválidos.");
        }
    }

    public void flush() {
        this.users = new ArrayList<>();
        this.sessions = new ArrayList<>();
        System.out.println("Flushed data.");
    }

    public String getUserAttribute(String login, String attribute) {
        User user = findUser(login);
        if(Objects.equals(attribute, "nome")) return user.getName();

        return null;
    }

    public void shutdown() {
        exportUsers();
    }

    public static List<User> importUsers(String filePath) {
        List<User> data = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    String login = parts[0];
                    String name = parts[1];
                    String password = parts[2];
                    data.add(new User(login, password, name));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (data.isEmpty()) {
            data = new ArrayList<>();
        }

        return data;
    }
    public void exportUsers() {

        try (FileWriter fileWriter = new FileWriter("data.txt")) {
            for (User user : this.users) {
                fileWriter.write(
                        user.getLogin() + ';' +
                                user.getName() + ';' +
                                user.getPassword() +
                                System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
