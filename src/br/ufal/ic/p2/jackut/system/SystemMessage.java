package br.ufal.ic.p2.jackut.system;

public class SystemMessage extends Message<String> {
    public SystemMessage(String message) {
        super("System", message);
    }
}
