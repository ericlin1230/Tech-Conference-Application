package controller;

import interfaces.IManager;
import interfaces.IPresenter;
import user.UserManager;
import user.SpeakerManager;
import message.MessageManager;
import event.EventManager;
import presenter.MessageUI;
import presenter.ErrorUI;
import presenter.LoginUI;
import presenter.EventUI;
import presenter.RequestUI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


/**
 * The UserController class controls what User's can do to other User-related options
 *
 * @author Haoying Shen
 * @version 1.0
 */

public class UserController {
    UserManager userManager;
    SpeakerManager speakerManager;
    MessageManager messageManager;
    EventManager eventManager;
    private MessageUI messageUI;
    private ErrorUI errorUI;
    private LoginUI loginUI;
    private EventUI eventUI;
    private RequestUI requestUI;
    String username;

    /**
     * Constructor for EventController, initializes an UserController object
     *
     * @param managers HashMap of String keys that map to IManager objects (Managers)
     * @param presenters HashMap of String keys that map to IPresenter objects (Presenters)
     */
    public UserController(HashMap<String, IManager> managers,
                          HashMap<String, IPresenter> presenters) {
        this.userManager = (UserManager) managers.get("userManager");
        this.speakerManager = (SpeakerManager) managers.get("speakerManager");
        this.messageManager = (MessageManager) managers.get("messageManager");
        this.eventManager = (EventManager) managers.get("eventManager");
        this.messageUI = (MessageUI) presenters.get("messageUI");
        this.errorUI = (ErrorUI) presenters.get("errorUI");
        this.loginUI = (LoginUI) presenters.get("loginUI");
        this.eventUI = (EventUI) presenters.get("eventUI");
        this.requestUI = (RequestUI) presenters.get("requestUI");
    }

    /**
     * Returns the UserManager that is stored in this Controller
     *
     * @return userManager      The UserManager that is stored under this Controller
     */
    public UserManager getUserManager() {
        return this.userManager;
    }

    /**
     * Returns the SpeakerManager that is stored in this Controller
     *
     * @return SpeakerManager      The SpeakerManager that is stored under this Controller
     */
    public SpeakerManager getSpeakerManager() {
        return this.speakerManager;
    }


    /**
     * Returns the MessageManager that is stored in this Controller
     *
     * @return MessageManager      The MessageManager that is stored under this Controller
     */
    public MessageManager getMessageManager() {
        return this.messageManager;
    }


    /**
     * Creates a User entity of either type Attendee or Organizer
     *
     * @param type     The desired type for the User; can be Attendee or Organizer
     * @param name     The desired name for the User.
     * @param username The desired username for the User.
     * @param password The desired password for the User.
     */
    public void addUser(String type, String name, String username, String password) {
        switch (type) {
            case "Attendee":
                if (!userManager.getUserMap().containsKey(username))
                    userManager.newAttendee(name, username, password);
                else errorUI.usernameTaken();
                break;
            case "Organizer":
                if (!userManager.getUserMap().containsKey(username))
                    userManager.newOrganizer(name, username, password);
                else errorUI.usernameTaken();
                break;
            case "VIP":
                if (!userManager.getUserMap().containsKey(username))
                    userManager.newVIP(name, username, password);
                else errorUI.usernameTaken();
                break;
            default:
                errorUI.invalidInput();
                break;
        }
    }

    /**
     * Set the username during init
     *
     * @param username the username of the user logged in
     */
    public void setUser(String username) {
        this.username = username;
    }


    /**
     * Adds 2 users to each other's contacts
     *
     * @param user1 The username of a user.
     * @param user2 The username of another user.
     */
    public void addContact(String user1, String user2) {
        if (userManager.getUserMap().containsKey(user1) && userManager.getUserMap().containsKey(user2)) {
            if (userManager.getUserContactList(user1).contains(user2)) {
                messageUI.contactReport(1);
            } else {
                userManager.addUserContacts(user1, user2);
            }
        } else {
            messageUI.contactReport(3);
        }
    }

    /**
     * Removes 2 users from each other's contacts
     *
     * @param user1 The username of a user.
     * @param user2 The username of another user.
     */
    public void removeContact(String user1, String user2) {
        if (userManager.getUserMap().containsKey(user1) && userManager.getUserMap().containsKey(user2)) {
            if (!userManager.getUserContactList(user1).contains(user2)) {
                messageUI.contactReport(2);
            } else {
                userManager.removeUserContacts(user1, user2);
            }
        } else {
            messageUI.contactReport(3);
        }
    }

    /**
     * Add an event from the information provided by the user.
     *
     * @param userID The user ID of the user that is adding an event.
     */
    public void signUpForEvent(String userID) {
        boolean passed = false;
        String eventID = "";
        loginUI.signUpForEventReporter(1);
        eventUI.printEvents();
        loginUI.signUpForEventReporter(2);
        Scanner scan = new Scanner(System.in);
        if (scan.hasNextLine()) {
            eventID = scan.nextLine();
        }
        if (eventID.equals("0")) {
            loginUI.signUpForEventReporter(3);
            return;
        }
        String roomid = eventManager.getRoomID(eventID);
        if (roomid.equals("0")) {
            errorUI.invalidEventID();
        }
        int roomcap = eventManager.getRoomCap(roomid);
        int speakercount = 0;
        int attendeecount = 0;
        try {
            speakercount = eventManager.getSpeakers(eventID).size();
            attendeecount = eventManager.getAttendees(eventID).size();
        } catch (Exception e) {
            errorUI.invalidEventID();
            return;
        }
        if (attendeecount + speakercount + 1 > roomcap) {
            errorUI.maxroomreach();
            return;
        }
        if (userManager.checkType(userID).equals("Attendee")
                || userManager.checkType(userID).equals("Organizer")
                || userManager.checkType(userID).equals("VIP")) {
            loginUI.signUpForEventReporter(4);
            eventUI.printDetail(eventID);
            passed = addAttendeeToEvent(userID, eventID);
        }
        if (userManager.checkType(userID).equals("Speaker")) {
            loginUI.signUpForEventReporter(4);
            eventUI.printDetail(eventID);
            passed = eventManager.addSpeaker(userID, eventID);
        }
        if (passed) {
            boolean reqOK = true;
            while (reqOK) {
                requestUI.requestPrompt();
                String reqP = scan.nextLine();
                if (reqP.equals("1")) {
                    requestUI.requestMessage();
                    ArrayList<String> Organizers = new ArrayList<>();
                    Organizers.addAll(userManager.getOrganizerMap().keySet());
                    String reqM = scan.nextLine();
                    String request = "Request for Event ID: " + eventID + " " + reqM;
                    messageManager.sendMessage(username, Organizers, request, true);
                    reqOK = false;
                } else if (reqP.equals("2")) {
                    reqOK = false;
                } else {
                    errorUI.invalidInput();
                }
            }
            loginUI.actionSuccessful();
        }
    }

    /**
     * Adds an Attendee to the event
     * helper
     *
     * @param username The username of the user.
     * @param eventID  The eventID to add the speaker to.
     * @return boolean if the addition was successful
     */
    private boolean addAttendeeToEvent(String username, String eventID) {
        if (userManager.getUserMap().containsKey(username)) {
            if (eventManager.getEventList().containsKey(eventID)) {
                String time = eventManager.getStartTime(eventID);
                if (userManager.checkIfUserAvail(username, time)) {
                    if (eventManager.getType(eventID) == 3) {
                        if (userManager.checkType(username).equals("VIP")) {
                            eventManager.addAttendee(username, eventID);
                            userManager.addUserEvent(username, time, eventID);
                            return true;
                        } else {
                            errorUI.notVIP();
                        }
                    } else {
                        eventManager.addAttendee(username, eventID);
                        userManager.addUserEvent(username, time, eventID);
                        return true;
                    }
                } else {
                    errorUI.unavailableUser();
                }
            } else {
                errorUI.invalidEventID();
            }
        } else {
            errorUI.invalidUser();
        }
        return false;
    }

    /**
     * Creates a user based on user input for name, username, password
     * Helper
     *
     * @param userType The type of user to initialize
     */
    private void initializeUser(String userType) {
        boolean usernameOK = false;
        Scanner reader = new Scanner(System.in);
        ArrayList<String> info = new ArrayList<>();
        loginUI.askForUserInfo("Name");
        String name = reader.nextLine();
        while (!usernameOK) {

            loginUI.askForUserInfo("Username");
            String userName = reader.nextLine();
            if (!(userName.length() > 5) || userName.contains(" ")) {
                errorUI.invalidUsername();
            } else {
                usernameOK = true;
                info.add(userName);
            }
        }
        loginUI.askForUserInfo("Password");
        String password = reader.nextLine();
        info.add(name);
        info.add(password);

        if (userManager.getUserMap().containsKey(info.get(0))) {
            errorUI.usernameTaken();
        } else {
            switch (userType) {
                case "Attendee":
                    userManager.newAttendee(info.get(1), info.get(0), info.get(2));
                    loginUI.successfulCreation("Attendee");
                    break;
                case "Organizer":
                    userManager.newOrganizer(info.get(1), info.get(0), info.get(2));
                    loginUI.successfulCreation("Organizer");
                    break;
                case "Speaker":
                    speakerManager.newSpeaker(userManager, info.get(1), info.get(0), info.get(2));
                    loginUI.successfulCreation("Speaker");//MOVE TO UI
                    break;
                case "VIP":
                    userManager.newVIP(info.get(1), info.get(0), info.get(2));
                    loginUI.successfulCreation("VIP"); //MOVE TO UI
                    break;
            }
        }
    }

    /**
     * Creates a user based on user input for name, username, password
     */
    public void createUser() {
        loginUI.askForUserType();
        Scanner reader = new Scanner(System.in);
        String choice = reader.nextLine();
        if (choice.equals("Attendee")
                || (choice.equals("Organizer"))
                || (choice.equals("Speaker"))
                || (choice.equals("VIP"))) {
            initializeUser(choice);
        } else {
            errorUI.invalidInput();
        }
    }


    /**
     * The contact modifying menu
     * 1 to view contacts
     * 2 to add contacts
     * 3 to remove contacts
     */
    public void modifyContacts() {
        messageUI.contactOptions();
        Scanner reader = new Scanner(System.in);
        loginUI.generalPrompt();

        String choice = reader.nextLine();
        switch (choice) {
            case "1":
                messageUI.printContactList(userManager.getUserContactList(username));
                break;
            case "2":
                addContact();
                break;
            case "3":
                removeContact();
                break;
        }
    }

    /**
     * Adds a contact to this user based on user input
     */
    public void addContact() {
        messageUI.askForContact();
        Scanner reader = new Scanner(System.in);
        String contactAdd = reader.nextLine();
        addContact(username, contactAdd);
    }

    /**
     * Removes a contact from this user based on user input
     */
    public void removeContact() {
        messageUI.askForContactRemove();
        Scanner reader = new Scanner(System.in);
        String contactRemove = reader.nextLine();
        removeContact(username, contactRemove);
    }

    /**
     * The attending schedule modifying menu
     * 1 to add events to schedule
     * 2 to remove events from schedule
     * 3 to leave menu
     */
    public void modifySchedule() {
        loginUI.scheduleOptions();
        Scanner reader = new Scanner(System.in);
        loginUI.generalPrompt();
        String choice = reader.nextLine();
        switch (choice) {
            case "1":
                signUpForEvent(username);
                break;
            case "2":
                loginUI.time();
                seeAttendeeSchedule();
                Scanner timer = new Scanner(System.in);
                String time = timer.nextLine();
                if (userManager.getAttendingSchedule(username).containsKey(time)) {
                    String eventID = userManager.getAttendingSchedule(username).get(time);
                    userManager.removeUserEvent(username, time);
                    eventManager.removeAttendee(username, eventID);
                } else {
                    errorUI.invalidInput();
                }
                break;
            case "3":
                break;
        }
    }

    /**
     * Prints the speaker's schedule
     */
    public void seeSpeakerSchedule() {
        EventUI s = new EventUI(eventManager);
        s.printSpeakerSchedule(username);
    }

    /**
     * Prints the attendee's schedule
     */
    public void seeAttendeeSchedule() {
        EventUI s = new EventUI(eventManager);
        s.printUserSchedule(username);
    }
}
