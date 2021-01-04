package message;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Message class contains features of the Message entity
 *
 * @author Ryan Wang
 * @version 1.0
 */
public class Message implements Serializable{
    private String senderUsername;
    private ArrayList<String> recipientUsernames;
    private String message;
    private boolean isRequest;
    private boolean isAccept;

    /**
     * Constructor for Message class, initializing sender, recipients, and a message
     *
     * @param sender String username for the sender
     * @param recipients ArrayList of String usernames for recipients
     * @param message String message content
     * @param isRequest boolean telling if the message is a request
     */
    public Message(String sender, ArrayList<String> recipients, String message, boolean isRequest){
        this.senderUsername = sender;
        this.recipientUsernames = recipients;
        this.message = message;
        this.isRequest = isRequest;
        this.isAccept = false;
    }

    /**
     * Getter for isRequest
     * @return boolean telling if the message is a request
     */
    public boolean isRequest(){
        return isRequest;
    }

    /**
     * Getter for isAccept
     * @return boolean telling if the request has been accepted
     */
    public boolean isAccepted(){
        return isAccept;
    }

    /**
     * Set Message to be an accepted request
     */
    public void acceptRequest(){
        isRequest = false;
        isAccept = true;
    }

    /**
     * Overriding the string representation of Message depending on type of Message (non-request/request/accepted)
     *
     * @return String representation of Message
     */
    @Override
    public String toString(){
        if (!isAccept && !isRequest) {
            return senderUsername + ": " + message + " -> " + recipientUsernames;
        }
        else if (isRequest){
            return senderUsername + ": " + message + " -> " + "Pending";
        }
        else {
            return senderUsername + ": " + message + " -> " + "Accepted";
        }
    }
}
