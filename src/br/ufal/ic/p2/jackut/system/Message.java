package br.ufal.ic.p2.jackut.system;

/**
 * This class represents a message in the system.
 * It contains the sender, recipient, and message.
 */
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
public class Message {
    /**
     * The sender of the message.
     */
    private final String sender;
    /**
     * The recipient of the message.
     */
    private final String recipient;
    /**
     * The content of the message.
     */
    private final String message;

    @JsonCreator
    public Message(
            @JsonProperty("sender")String sender,
            @JsonProperty("recipient")String recipient,
            @JsonProperty("message") String message) {

        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
    }

    /**
     * Returns the sender of the message.
     *
     * @return The sender of the message.
     */

    public String getSender() {
        return this.sender;
    }

    /**
     * Returns the recipient of the message.
     *
     * @return The recipient of the message.
     */

    public String getRecipient() {
        return this.recipient;
    }

    /**
     * Returns the content of the message.
     *
     * @return The content of the message.
     */

    public String getMessage() {
        return this.message;
    }
}