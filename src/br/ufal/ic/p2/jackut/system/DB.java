package br.ufal.ic.p2.jackut.system;
import java.util.ArrayList;
import java.util.Objects;

public class DB {
    private ArrayList<User> users;
    private ArrayList<Session> sessions;
    private int activeSessions = 0;

    public DB() {
        this.users = new ArrayList<>();
        this.sessions = new ArrayList<>();
    }

    public void newUser(String login, String password, String name){
        try{
            User e = findUser(login);
            if (e != null) throw new RuntimeException("Conta com esse nome já existe.");
        } catch(RuntimeException error) {
            User user = new User(login, password, name);
            System.out.print("New user: ");
            System.out.println(user.getName());
            this.users.add(user);
        }
    }

    public User findUser(String login) {
        for (User user : this.users) {
            System.out.print("Found user: ");
            System.out.println(user.getName());
            if (user.getName().equals(login)) return user;
        }

        throw new RuntimeException("Usuário não cadastrado.");
    }

    public int startSession(String login, String password){

        User e = findUser(login);
        if (e.matchPassword(password)) {
            int id = activeSessions;
            sessions.add(new Session(e, id));
            activeSessions++;
            return id;
        }

        return -1;
    }

    public void flush() {
        this.users = new ArrayList<>();
        this.sessions = new ArrayList<>();
        System.out.println("Flushed data.");
    }

    public String getUserAttribute(String login, String attribute) {
        User user = findUser(login);
        System.out.print("Got attribute: ");
        System.out.println(user.getName());
        if(Objects.equals(attribute, "nome")) return user.getName();

        return null;
    }
}

