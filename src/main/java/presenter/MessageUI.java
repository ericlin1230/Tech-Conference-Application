package presenter;

import event.EventManager;
import interfaces.IManager;
import interfaces.IPresenter;
import message.Message;
import message.MessageManager;
import user.SpeakerManager;
import user.UserManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The MessageUI provides the prompts for messaging services
 *
 * @author Ryan Wang
 * @version 1.0
 */
public class MessageUI implements IPresenter {
    private MessageManager messageManager;
    private EventManager eventManager;
    private UserManager userManager;
    private SpeakerManager speakerManager;

    /**
     * Constructor for MessageUI, initializes a MessageUI object
     *
     * @param managers HashMap of String keys that map to IManager objects (Managers)
     */
    public MessageUI(HashMap<String, IManager> managers){
        this.userManager = (UserManager) managers.get("userManager");
        this.messageManager = (MessageManager) managers.get("messageManager");
        this.eventManager = (EventManager) managers.get("eventManager");
        this.speakerManager = (SpeakerManager) managers.get("speakerManager");
    }


    /**
     * Prints the list of Contacts
     *
     * @param statement  an arraylist of contacts
     */
    public void printContactList(ArrayList<String> statement){
        System.out.println(statement);
    }


    /**
     * Prints different messages when adding or removing a user
     *
     * @param type  A integer that determines the print statement
     */
    public void contactReport(int type){
        switch (type) {
            case 1:
                System.out.println("User is already in contact");
                break;
            case 2 :
                System.out.println("User is not part of contacts");
                break;
            case 3:
                System.out.println("That user does not exist");
                break;
        }
    }

    /**
     * Prints the options after selecting the contact menu option
     */
    public void contactOptions(){
        System.out.println("(1) View contacts (2) Add new contacts (3) Remove Contacts");
    }


    /**
     * Prints the question that asks for contact to add
     */
    public void askForContact(){
        System.out.println("What is the username of the contact you would like to add?");
    }

    /**
     * Prints the question that asks for contact to remove
     */
    public void askForContactRemove(){
        System.out.println("What is the username of the contact you would like to remove?");
    }

    /**
     * Prints options after accessing messaging menu options
     */
    public void messageMenuOptions(){
        System.out.println("Would you like to (1) Send a message and/or access request options if available");
        System.out.println("(2) View your inbox, (3) View your sent messages");
    }

    /**
     * Prints options given after attendee selects (1) Send a message
     */
    public void attendeeSend(){
        System.out.println("(1) View suggested recipients, (2) View outgoing requests, (3) Send a message");
    }

    /**
     * Prints options given after speaker selects (1) Send a mass message to attendees
     */
    public void speakerAttendeeSend(){
        System.out.println("(1) Message all attendees of all your events, " +
                "(2) Message all attendees of specified events");
    }


    /**
     * Prints options given after speaker selects (1) Send a message
     */
    public void speakerSend(){
        System.out.println("(1) View suggested recipients, (2) Send a message ");
    }

    /**
     * Prints options given after speaker selects (2) Continue to sending message
     */
    public void speakerSendOptions(){
        System.out.println("(1) Send a mass message to attendees, (2) General messaging");
    }
    /**
     * Prints options given after organizer selects (1) Send a message
     */
    public void organizerSend(){
        System.out.println("(1) View suggested recipients, (2) View incoming requests, (3) Send a message");
    }

    public void requestOptions(){
        System.out.println("(1) Accept a request, (0) Exit");
    }

    /**
     * Prints options given after organizer creates a speaker username
     */
    public void organizerSendOptions(){
        System.out.println("Please choose what type of message to send:" +
                " (1) All attendees, (1b) All VIPs, (2) All speakers, (3) All organizers, (4) All users, (5) General messaging");
    }

    /**
     * Prints prompt for asking for message when sending one
     */
    public void askForMessage(){
        System.out.println("What is your message?");
    }

    /**
     * Prints usernames of contacts, other people attending the same events,
     * and other people speaking at the same events you attend
     *
     * @param username Username of current User
     */
    public void printAttendeeRecipients(String username){
        System.out.println("Contacts:");
        System.out.println(userManager.getUserContactList(username));
        System.out.println("Other people attending the same events as you:");
        System.out.println(attendeeGetAttendingRecipients(username));
        System.out.println("Other people speaking at events you are attending:");
        System.out.println(attendeeGetSpeakerRecipients(username));
    }

    /**
     * Prints usernames of contacts, other people attending the same events you speak at
     *
     * @param username Username of current User
     */
    public void printSpeakerRecipients(String username){
        System.out.println("Contacts:");
        System.out.println(userManager.getUserContactList(username));
        System.out.println("Other people attending events you are speaking at:");
        System.out.println(speakerGetAttendeeRecipients(username));
    }

    /**
     * Prints all usernames as Organizers can send a message to anyone
     */
    public void printOrganizerRecipients(){
        System.out.println(userManager.getUserMap().keySet());
    }

    /**
     * Print's messages in a User's Inbox
     *
     * @param username Username of current logged in user
     */
    public void viewInbox(String username){
        if(messageManager.getUserInbox(username).isEmpty()){
            System.out.println("Your inbox is empty.");
        }
        else{
            for(Message message : messageManager.getUserInbox(username)){
                System.out.println(message);
            }
        }
    }

    /**
     * Print's messages that a User has sent
     *
     * @param username The username of the user being checked
     */
    public void viewSent(String username){
        if(messageManager.getUserSent(username).isEmpty()){
            System.out.println("You have sent no messages.");
        }
        else{
            for(Message message : messageManager.getUserSent(username)) {
                System.out.println(message);
            }
        }
    }


    /**
     * Helper
     * Returns of list of all Attendees attending the same events
     *
     * @param senderUsername      The username of the sender
     * @return ArrayList          List of possible recipients
     */
    private ArrayList<String> attendeeGetAttendingRecipients(String senderUsername)
    {
        ArrayList<String> recipients = new ArrayList<>();
        //add existing contacts
        ArrayList<String> eventIDs = new ArrayList<>(userManager.getAttendingSchedule(senderUsername).values());
        //get all events that this sender is attending
        for(String eventID: eventIDs)// loop though each event
        {
            for (String username: eventManager.getAttendees(eventID))//add all attendees to possible recipients
            {
                if (!recipients.contains(username))
                {
                    recipients.add(username);
                }
            }
        }
        return recipients;
    }

    /**
     * Helper
     * Returns of list of all Speakers speaking at events that this user is attending
     *
     * @param senderUsername      The username of the sender
     * @return ArrayList          List of possible recipients
     */
    private ArrayList<String> attendeeGetSpeakerRecipients(String senderUsername)
    {
        ArrayList<String> recipients = new ArrayList<>();
        //add existing contacts
        ArrayList<String> eventIDs = new ArrayList<>(userManager.getAttendingSchedule(senderUsername).values());
        //get all events that this sender is attending
        for(String eventID: eventIDs)// loop though each event
        {
            for (String username: eventManager.getSpeakers(eventID))//add all attendees to possible recipients
            {
                if (!recipients.contains(username))
                {
                    recipients.add(username);
                }
            }
        }
        return recipients;
    }

    /**
     * Helper
     * Returns of list of all Attendees speaking at events that this Speaker is speaking at
     * Speaker exclusive
     *
     * @param senderUsername      The username of the sender
     * @return ArrayList          List of possible recipients
     */
    private ArrayList<String> speakerGetAttendeeRecipients(String senderUsername)
    {
        ArrayList<String> recipients = new ArrayList<>();
        //add existing contacts
        ArrayList<String> eventIDs = new ArrayList<>(speakerManager.getSpeakingSchedule(senderUsername).values());
        //get all events that this sender is attending
        for(String eventID: eventIDs)// loop though each event
        {
            for (String username: eventManager.getAttendees(eventID))//add all attendees to possible recipients
            {
                if (!recipients.contains(username))
                {
                    recipients.add(username);
                }
            }
        }
        return recipients;
    }

    /**
     * Prints prompt to ask for usernames
     */
    public void askForUserName(){
        System.out.println("Enter usernames and then hit enter. Enter nothing twice to stop");
    }

    /**
     * Prints successful message sent prompt
     *
     * @param numUsers The number of the user that has been sent
     */
    public void messageSuccess(int numUsers) {
        System.out.println("Message successfully sent to " + numUsers + " users.");
    }

    /**
     * Prints a request
     *
     * @param messageID an integer referring to a request
     */
    public void viewRequest(Integer messageID){
        System.out.println(messageManager.getMessage(messageID));
    }
}
