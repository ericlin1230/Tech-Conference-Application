package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import user.SpeakerManager;
import event.EventManager;
import interfaces.IManager;
import interfaces.IPresenter;
import message.MessageManager;
import presenter.MessageUI;
import presenter.ErrorUI;
import presenter.LoginUI;
import presenter.RequestUI;
import user.UserManager;

/**
 * The MessageController class controls what messaging options a User has depending on the type of User
 *
 * @author Ryan Wang
 * @version 1.0
 */
public class MessageController {
    private MessageUI messageUI;
    private ErrorUI errorUI;
    private LoginUI loginUI;
    private RequestUI requestUI;
    private UserManager userManager;
    private SpeakerManager speakerManager;
    private MessageManager messageManager;
    private EventManager eventManager;
    String username;
    String userType;

    /**
     * Constructor for MessageController, initializes an MessageController object
     *
     * @param managers HashMap of String keys that map to IManager objects (Managers)
     * @param presenters HashMap of String keys that map to IPresenter objects (Presenters)
     * @param username String representing the User's username
     * @param userType String representing the User's type (Attendee, Organizer, etc.)
     */
    public MessageController(
            HashMap<String, IManager> managers,
            HashMap<String, IPresenter> presenters,
            String username, String userType
    ) {
        this.messageUI = (MessageUI) presenters.get("messageUI");
        this.errorUI = (ErrorUI) presenters.get("errorUI");
        this.loginUI = (LoginUI) presenters.get("loginUI");
        this.requestUI = (RequestUI) presenters.get("requestUI");
        this.userManager = (UserManager) managers.get("userManager");
        this.speakerManager = (SpeakerManager) managers.get("speakerManager");
        this.messageManager = (MessageManager) managers.get("messageManager");
        this.eventManager = (EventManager) managers.get("eventManager");
        this.userType = userType;
        this.username = username;
    }


    /**
     * Returns a List of recipients' usernames based on user input
     *
     * @return ArrayList    A list of strings that represent recipients
     */
    public ArrayList<String> enterUsernames() {
        Scanner reader = new Scanner(System.in);
        //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        messageUI.askForUserName();

        ArrayList<String> inputs = new ArrayList<>();
        int count = 0;
        String input = "w";
        while (!input.equals("")) {
            count++;
            input = reader.nextLine();
            if (!input.equals("")) {
                inputs.add(input);
            }
        }
        return inputs;
    }

    /**
     * Returns a String of user input
     *
     * @return String   A string that represents a message body
     */
    public String enterMessage() {
        Scanner reader = new Scanner(System.in);
        return reader.nextLine();
    }

    /**
     * Returns a Integer of user input
     *
     * @return The integer that the user inputed
     */

    private Integer enterID() {
        Scanner reader = new Scanner(System.in);
        return reader.nextInt();
    }


    /**
     * Reads the user input for options
     * 0 to exit
     * 1 to send messages
     * 2 to view inbox
     * 3 to view sent messages
     */
    public void messageOptions() {
        boolean passed = true;
        while (passed) {
            messageUI.messageMenuOptions();
            String chosen = enterMessage();
            switch (chosen) {
                case "0":
                    passed = false;
                    break;
                case "1": // send messages
                    sendMessageChoice();
                    break;
                case "2": // view inbox
                    messageUI.viewInbox(username);
                    break;
                case "3":
                    messageUI.viewSent(username);
                    break;
                default:
                    errorUI.invalidInput();
                    break;
            }
        }
    }


    /**
     * Messaging specific to Attendees, can message those attending the same event, those speaking at the event
     * and contacts
     */
    private void attendeeSend() {
        boolean repeat = true;
        while (repeat) {
            messageUI.attendeeSend();
            String chosen = enterMessage();
            if (chosen.equals("1")) { //view possible recipients
                messageUI.printAttendeeRecipients(username);
                repeat = false;
            }
            else if (chosen.equals("2")) { // view outgoing requests
                requestUI.viewUserRequests(username);
            } else if (chosen.equals("3")) { // send a msg
                ArrayList<String> recipientsList = enterUsernames();
                messageUI.askForMessage();
                String message = enterMessage();
                msgSend(username, recipientsList, message, false);
                repeat = false;
            } else if (chosen.equals("0")) {
                repeat = false;
            } else {
                errorUI.invalidInput();
            }
        }
    }


    /**
     * Messaging specific to Speakers, can message attendees of events they are speaking at, as well as their contacts
     */
    private void speakerSend() {
        boolean repeat = true;
        while (repeat) {
            messageUI.speakerSend();
            String chosen1 = enterMessage();
            if (chosen1.equals("1")) { //view possible recipients
                messageUI.printSpeakerRecipients(username);
            } else if (chosen1.equals("2")) { // send a msg
                messageUI.speakerSendOptions();
                String chosenOpt = enterMessage();
                if (chosenOpt.equals("1")) {
                    messageUI.speakerAttendeeSend();
                    String chosenOpt2 = enterMessage();
                    if (chosenOpt2.equals("1")) {
                        // get all attendees of this speaker's event
                        messageUI.askForMessage();
                        String message = enterMessage();
                        sendMessageToAttendeesAllEvent(username, message);
                        repeat = false;
                    } else if (chosenOpt2.equals("2")) {
                        loginUI.askForEventID();
                        String eventID = enterMessage();
                        messageUI.askForMessage();
                        String message = enterMessage();
                        sendMessageToAttendee(username, eventID, message);
                        repeat = false;
                    } else {
                        errorUI.invalidInput();
                    }
                } else if (chosenOpt.equals("2")) { // General messaging
                    ArrayList<String> recipientsList = enterUsernames();
                    messageUI.askForMessage();
                    String message = enterMessage();
                    msgSend(username, recipientsList, message, false);
                    repeat = false;
                } else {
                    errorUI.invalidInput();
                }
            } else if (chosen1.equals("0")) {
                repeat = false;
            } else {
                errorUI.invalidInput();
            }
        }
    }

    /**
     * Messaging specific to Organizers, can mass message all attendees, all speakers, all organizers or everyone
     * can also send specific messages without restrictions
     */
    private void organizerSend() {
        boolean repeat = true;
        while (repeat) {
            messageUI.organizerSend();
            String chosen2 = enterMessage();
            if (chosen2.equals("1")) { //view possible recipients
                messageUI.printOrganizerRecipients();
            }
            else if (chosen2.equals("2")){ // view incoming requests
                requestUI.viewRequests();
                messageUI.requestOptions();
                String option = enterMessage();
                switch(option){
                    case "0":
                        repeat = false;
                        break;
                    case "1": // respond to request
                        requestUI.askForID();
                        Integer messageID = enterID();
                        if (!messageManager.getActiveRequests().contains(messageID)){
                            errorUI.invalidMessageID();
                            break;
                        }
                        if (!messageManager.isRequest(messageID)){
                            errorUI.invalidMessageID();
                            break;
                    }
                        else {
                            updateRequest(username, messageID);
                            messageUI.viewRequest(messageID);
                            break;
                        }
                    }
                }
            else if (chosen2.equals("3")) { // send a msg
                messageUI.organizerSendOptions();
                String chosenOpt = enterMessage();
                messageUI.askForMessage();
                String message = enterMessage();
                switch (chosenOpt) {
                    case "0":
                        repeat = false;
                        break;
                    case "1":
                        massMessage(username, "Attendee", message);
                        repeat = false;
                        break;
                    case "1b":
                        massMessage(username, "VIP", message);
                        break;
                    case "2":
                        massMessage(username, "Speaker", message);
                        repeat = false;
                        break;
                    case "3":
                        massMessage(username, "Organizer", message);
                        repeat = false;
                        break;
                    case "4":
                        massMessage(username, "Everyone", message);
                        repeat = false;
                        break;
                    case "5":
                        ArrayList<String> recipientsList = enterUsernames();
                        msgSend(username, recipientsList, message, false);
                        repeat = false;
                        break;
                    default:
                        errorUI.invalidInput();
                        break;
                }

            } else if (chosen2.equals("0")) {
                repeat = false;
            } else {
                errorUI.invalidInput();
            }
        }
    }


    /**
     * Changes type of send message based on user type
     */
    private void sendMessageChoice() {
        switch (userType) {
            case "Attendee":
            case "VIP":
                attendeeSend();
                break;
            case "Speaker":
                speakerSend();
                break;
            case "Organizer":
                organizerSend();
                break;
        }
    }


    /**
     * Sends a direct message to a desired list of users
     * The recipients must be contacted with the sender
     *
     * @param senderUsername     The username of the sender.
     * @param recipientUsernames An Arraylist of recipient usernames.
     * @param message            The message body.
     * @param isRequest  Boolean that tells if the message is a request
     */
    public void msgSend(String senderUsername, ArrayList<String> recipientUsernames, String message, boolean isRequest) {
        int error = 0;
        int valid = 0;
        if (!recipientUsernames.isEmpty()) {
            ArrayList<String> recipients = new ArrayList<>();
            for (String username : recipientUsernames)
            //loops through all recipients of input list to see if they can be messaged
            {
                if (userManager.getUserMap().containsKey(username) && !recipients.contains(username)) {
                    recipients.add(username);
                    valid += 1;
                } else {
                    error += 1;
                }
            }
            messageManager.sendMessage(senderUsername, recipients, message, isRequest);
            {
                messageUI.messageSuccess(valid);
            }
            if (error > 0) {
                errorUI.numInvalidUsers(error);
            }
        } else
            errorUI.emptyRecipients();
    }

    /**
     * Sends a message to the Attendees of a event that the Speaker is hosting
     * The event must have an Attendee(s) and the Speaker must be speaking at that event
     * Speaker exclusive
     *
     * @param speakerUsername The username of the Speaker sending the message.
     * @param eventID         The eventID that contains attendees to message.
     * @param message         The message body.
     */
    public void sendMessageToAttendee(String speakerUsername, String eventID, String message) {
        if (speakerManager.getSpeakingSchedule(speakerUsername).containsValue(eventID)) {
            if (!eventManager.getAttendees(eventID).isEmpty()) {
                ArrayList<String> Attendees = new ArrayList<>();
                Attendees.addAll(eventManager.getAttendees(eventID));
                messageManager.sendMessage(speakerUsername, Attendees, message, false);
            } else {
                errorUI.noAttendees();
            }
        } else {
            errorUI.notSpeaking();
        }
    }

    /**
     * Sends a message to the Attendees of all events that the Speaker is hosting
     * Speaker exclusive
     *
     * @param speakerUsername The username of the Speaker sending the message.
     * @param message         The message body.
     */
    public void sendMessageToAttendeesAllEvent(String speakerUsername, String message) {
        ArrayList<String> Attendees = new ArrayList<>();
        for (String username : speakerGetAttendeeRecipients(speakerUsername)) {
            Attendees.add(username);
        }
        messageManager.sendMessage(speakerUsername, Attendees, message, false);
    }


    /**
     * Sends a mass message to all Attendees, all Speakers, all Organizers, or Everyone
     * Speaker exclusive
     *
     * @param organizerUsername The username of the Organizer sending the message.
     * @param type              The type of user to message, takes: "Attendee", "Speaker", "Organizer", "Everyone".
     * @param message           The message body.
     */
    public void massMessage(String organizerUsername, String type, String message) {
        if (userManager.getUserMap().containsKey(organizerUsername)) {
            switch (type) {
                case "Everyone":
                    ArrayList<String> Everyone = new ArrayList<>();
                    Everyone.addAll(userManager.getUserMap().keySet());
                    messageManager.sendMessage(organizerUsername, Everyone, message, false);
                    break;
                case "Attendee":
                    ArrayList<String> Attendees = new ArrayList<>();
                    Attendees.addAll(userManager.getAttendeeMap().keySet());
                    Attendees.addAll(userManager.getVIPMap().keySet());
                    messageManager.sendMessage(organizerUsername, Attendees, message, false);
                    break;
                case "Speaker":
                    ArrayList<String> Speakers = new ArrayList<>();
                    Speakers.addAll(speakerManager.getSpeakerMap().keySet());
                    messageManager.sendMessage(organizerUsername, Speakers, message, false);
                    break;
                case "Organizer":
                    ArrayList<String> Organizers = new ArrayList<>();
                    Organizers.addAll(userManager.getOrganizerMap().keySet());
                    messageManager.sendMessage(organizerUsername, Organizers, message, false);
                    break;
                case "VIP":
                    ArrayList<String> VIPs = new ArrayList<>();
                    VIPs.addAll(userManager.getVIPMap().keySet());
                    messageManager.sendMessage(organizerUsername, VIPs, message, false);
                    break;
            }
        } else {
            errorUI.noOrganizer();
        }
    }

    /**
     * Helper
     * Returns of list of all Attendees speaking at events that this Speaker is speaking at
     * Speaker exclusive
     *
     * @param senderUsername The username of the sender
     * @return ArrayList          List of possible recipients
     */
    private ArrayList<String> speakerGetAttendeeRecipients(String senderUsername) {
        ArrayList<String> recipients = new ArrayList<>();
        //add existing contacts
        ArrayList<String> eventIDs = new ArrayList<>(speakerManager.getSpeakingSchedule(senderUsername).values());
        //get all events that this sender is attending
        for (String eventID : eventIDs)// loop though each event
        {
            for (String username : eventManager.getAttendees(eventID))//add all attendees to possible recipients
            {
                if (!recipients.contains(username)) {
                    recipients.add(username);
                }
            }
        }
        return recipients;
    }

    /**
     * Takes in a username, checks to see if it is a userManager, and if it is, accepts the new request
     *
     * @param username takes in the username of an organizer
     * @param requestID id for new request
     */

    private void updateRequest(String username, Integer requestID) { // organizer restricted
        if (userManager.checkType(username).equals("Organizer")) {
            messageManager.acceptRequest(requestID);
        }
    }
}
