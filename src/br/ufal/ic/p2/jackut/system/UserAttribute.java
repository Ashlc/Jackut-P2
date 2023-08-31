package br.ufal.ic.p2.jackut.system;

import java.io.Serializable;

/**
 * This class represents a user account in the system.
 * It contains the user's login name, password, and name.
 */

public class UserAttribute implements Serializable {
    private String name;
    private String value;

    /**
     * Constructs a User object with the provided login, password, and name.
     *
     * @param name The login name for the new user.
     * @param value The password for the new user.
     */

    public UserAttribute(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Returns the value of the attribute.
     *
     * @return The attribute value.
     */

    public String getValue() {
        return value;
    }

    /**
     * Returns the name of the attribute.
     *
     * @return The attribute name.
     */

    public String getAttributeName() {
        return name;
    }

    /**
     * Sets the value of the attribute.
     *
     * @param value The new attribute value.
     */

    public void setValue(String value) {
        this.value = value;
    }
}
