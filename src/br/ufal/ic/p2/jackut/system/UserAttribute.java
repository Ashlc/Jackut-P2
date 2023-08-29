package br.ufal.ic.p2.jackut.system;

import java.io.Serializable;

public class UserAttribute implements Serializable {
    private String name;
    private String value;

    public UserAttribute(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    public String getAttributeName() {
        return name;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
