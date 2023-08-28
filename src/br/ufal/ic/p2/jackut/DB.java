package br.ufal.ic.p2.jackut;
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

    public void startSession(String login, String password){
        User e = findUser(login);

        if (e.matchPassword(password)){
            sessions.add(new Session(e, activeSessions));
            activeSessions++;
        }
    }
}

