package br.ufal.ic.p2.jackut.system;

public class UserMessage extends Message<User> {


    /**
     * @param sender
     * @param message
     */
    public UserMessage(User sender, String message) {
        super(sender, message);
    }
}
