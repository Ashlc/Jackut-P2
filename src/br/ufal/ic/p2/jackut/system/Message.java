package br.ufal.ic.p2.jackut.system;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record Message(String sender, String message) {

    @JsonCreator
    public Message(
            @JsonProperty("sender") String sender,
            @JsonProperty("message") String message) {

        this.sender = sender;
        this.message = message;
    }
}