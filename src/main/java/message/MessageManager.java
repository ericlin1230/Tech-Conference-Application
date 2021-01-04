package message;

import java.util.ArrayList;
import java.util.HashMap;
import interfaces.IManager;

import java.io.Serializable;

/**
 * The MessageManager class stores and modifies Message instances
 *
 * @author Ryan Wang
 * @version 1.0
 */
public class MessageManager implements Serializable, IManager{

    private HashMap<String, ArrayList<Integer>> messageSenders;
    private HashMap<String, ArrayList<Integer>> messageRecipients;
    private HashMap<Integer, Message> messageIDs;

    /**
     * Constructor for MessageManager
     *
     * @param senders HashMap mapping User senders to messageIDs
     * @param recipients HashMap mapping User recipients to messageIDs
     * @param messages HashMap mapping User messageIDs to messages
     */
    public MessageManager(HashMap<String,ArrayList<Integer>> senders, HashMap<String,ArrayList<Integer>> recipients,
                          HashMap<Integer,Message> messages){
        this.messageSenders = senders; // Sender = msgID
        this.messageRecipients = recipients; // Recipient = msgID
        this.messageIDs = messages; // ID = Message
    }

    /**
     * Sends a Message from a given User to an ArrayList of User recipients
     *
     * @param sender The User that sends the message
     * @param recipients The ArrayList of User that receives the message
     * @param message The String message being sent
     * @param isRequest boolean telling if the message is a request
     */
    public void sendMessage(String sender, ArrayList<String> recipients, String message, boolean isRequest){

        if(!recipients.isEmpty()) {
            // Initializing a new Message

            Message newMessage = new Message(sender, recipients, message, isRequest);

            // Creating the Message ID
            ArrayList<Integer> val = new ArrayList<>();
            val.add(messageIDs.size());

            if(recipients.size()==3){
                System.out.println("WW");
            }

            messageIDs.put(messageIDs.size(), newMessage); // ID = Message
            // Mapping the message ID to a sender
            if (!messageSenders.containsKey(sender)) {
                messageSenders.put(sender, val);
            } else {
                messageSenders.get(sender).add(val.get(0));
            }

            // Mapping message ID to the recipients
            for (String recipient : recipients) {
                if (!messageRecipients.containsKey(recipient)) {
                    ArrayList<Integer> val2 = new ArrayList<>();
                    val2.add(messageIDs.size()-1);
                    messageRecipients.put(recipient, val2);
                } else {
                    messageRecipients.get(recipient).add(val.get(0));
                }
            }
        }
    }

    /**
     * Gets a list of messages that User has sent
     *
     * @param account User that wants to see sent messages
     * @return ArrayList of Message of Message classes that a User has sent
     */
    public ArrayList<Message> getUserSent(String account){
        ArrayList<Message> messageList = new ArrayList<>();
        if(messageSenders.containsKey(account)) {
            for (Integer i : messageSenders.get(account)) {
                messageList.add(messageIDs.get(i));
            }
        }
        return messageList;
    }

    /**
     * Gets a list of messages that User has received
     *
     * @param account User that wants to see inbox
     * @return ArrayList of Message of Message classes that User has received
     */
    public ArrayList<Message> getUserInbox(String account){
        ArrayList<Message> messageList = new ArrayList<>();
        if(messageRecipients.containsKey(account)) { // check if an account has been sent a message
            for (Integer i : messageRecipients.get(account)) { // each message ID in account's received messages
                messageList.add(messageIDs.get(i));
            }
        }
        return messageList;
    }

    /**
     * Given a particular messageID this returns the isRequest variable of said message
     * @param messageID the ID of the message that is being checked
     *
     * @return boolean corresponding to the isRequest state of the message
     */
    public boolean isRequest(Integer messageID){
        return messageIDs.get(messageID).isRequest();
    }

    /**
     * Given a particular messageID this returns the isAccepted variable of said message
     * @param messageID the ID of the message that is being checked
     *
     * @return boolean corresponding to the isAccept state of the message
     */
    public boolean isAccepted(Integer messageID){
        return messageIDs.get(messageID).isAccepted();
    }

    /**
     * Getter for a specific message
     *
     * @param messageID Integer corresponding to the messageID
     * @return Message that corresponds to the messageID
     */
    public Message getMessage(Integer messageID){
        return messageIDs.get(messageID);
    }

    /**
     * Accepts a request
     *
     * @param messageID Integer referring to the ID of a request (Message)
     */
    public void acceptRequest(Integer messageID){
        messageIDs.get(messageID).acceptRequest();
    }

    /**
     * Getter for pending requests that have not been accepted
     *
     * @return ArrayList of type Integer referring to the IDs of pending requests (Message)
     */
    public ArrayList<Integer> getActiveRequests(){
        ArrayList<Integer> requests = new ArrayList<>();
        for(Integer id : messageIDs.keySet()){
            if (isRequest(id)){
                requests.add(id);
            }
        }
        return requests;
    }

    /**
     * Getter for pending requests that have been accepted
     *
     * @return ArrayList of type Message referring to IDs of accepted requests (Message)
     */
    public ArrayList<Message> getAcceptedRequests(){
        ArrayList<Message> requests = new ArrayList<>();
        for(Integer id : messageIDs.keySet()){
            if(isAccepted(id)){
                requests.add(getMessage(id));
            }
        }
        return requests;
    }

    /**
     * Getter for pending requests of a specific User
     *
     * @param username String referring to the User's username
     * @return ArrayList of type Integer referring to the IDs of User's pending outgoing requests (Message)
     */
    public ArrayList<Integer> getUserPendingRequests(String username){
        ArrayList<Integer> requests = new ArrayList<>();
        for(Integer id : getActiveRequests()){
            if(getUserSent(username).contains(getMessage(id))){
                requests.add(id);
            }
        }
        return requests;
    }
    /**
     * Getter for accepted requests of a specific User
     *
     * @param username String referring to the User's username
     * @return ArrayList of type Message referring to IDs of User's accepted Outgoing requests (Message)
     */
    public ArrayList<Message> getUserAcceptedRequests(String username){
        ArrayList<Message> requests = new ArrayList<>();
        for(Message request : getAcceptedRequests()){
            if(getUserSent(username).contains(request)){
                requests.add(request);
            }
        }
        return requests;
    }
}
