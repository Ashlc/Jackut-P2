package br.ufal.ic.p2.jackut.system;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a user attribute.
 */

public class UserAttribute {
    private final String name;
    private String value;

    @JsonCreator
    public UserAttribute(
            @JsonProperty("name") String name,
            @JsonProperty("value") String value) {
        this.name = name;
        this.value = value;
    }


    public String getValue() {
        return value;
    }
    public String getName() {
        return name;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
