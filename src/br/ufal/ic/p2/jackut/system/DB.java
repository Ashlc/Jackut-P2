package br.ufal.ic.p2.jackut.system;
import java.util.ArrayList;

public class DB {
    private ArrayList<User> users;
    private ArrayList<Session> sessions;
    private int activeSessions = 0;

    public DB() {
        this.users = new ArrayList<>();
        this.sessions = new ArrayList<>();
    }

    public void newUser(String username, String password, String displayName){
        this.users.add(new User(username, password, displayName));
    }

    public User findUser(String login) {
        for (User e : this.users) {
            if (e.getName().equals(login)) {
                return e;
            }
        }
        return null;
    }

    public int startSession(String login, String password){
        User e = findUser(login);

        if (e.matchPassword(password)){
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
    }
}

