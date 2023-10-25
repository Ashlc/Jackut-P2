package br.ufal.ic.p2.jackut.system;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Message
 */

@JsonIdentityInfo(
        generator = ObjectIdGenerators.UUIDGenerator.class,
        property = "@json_id"
)

public class Message<T> {

    private final T sender;
    private final String message;

    /**
     * @param sender
     * @param message
     */

    @JsonCreator
    public Message(
            @JsonProperty("sender") T sender,
            @JsonProperty("message") String message) {

        this.sender = (T) sender;
        this.message = message;
    }

    public T sender() {
        return sender;
    }

    public String message() {
        return message;
    }
}