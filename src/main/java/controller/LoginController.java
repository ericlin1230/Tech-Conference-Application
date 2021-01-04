package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import event.EventManager;
import user.SpeakerManager;
import interfaces.IManager;
import interfaces.IPresenter;
import presenter.LoginUI;
import presenter.ErrorUI;
import user.UserManager;
import message.MessageManager;
import gateway.ConvertToPDF;

/**
 * @author Eric Lin, Kieran Agterberg
 * @version 1.0
 */
public class LoginController {

    private boolean loggedIn;
    private LoginUI currentUI;
    private UserController userController;
    private EventController eventController;
    private MessageController messageController;
    private ErrorUI errorUI;
    private String username;
    private String password;
    private String name;
    private boolean quit;
    private String usertype;
    private ConvertToPDF convertToPDF;


    /**
     * Constructor of the LoginController class, initialization.
     */
    public LoginController() {
        loggedIn = false;
        quit = false;
        username = "";
        password = "";
        name = "";
    }


    /**
     * This function allows for user input for either register or Login
     * Allows for Login when 1 is entered
     * Allows for registration when 2 is entered
     */
    public void enterUserInfo() {
        while (!loggedIn) {
            int register = registerLogin();
            switch (register) {
                case 1:
                    ArrayList<String> info = askForInfo();
                    loggedIn = isValidLogin(info.get(0), info.get(1));
                    if (this.loggedIn) {
                        username = info.get(0);
                        password = info.get(1);
                        name = userController.getUserManager().getUserDisplayName(username);
                        currentUI.loginStatus(true);
                    } else {
                        currentUI.loginStatus(false);
                    }
                    break;
                case 2:
                    ArrayList<String> registerInfo = registerInfo();
                    String tempUser = registerInfo.get(0);
                    String tempPW = registerInfo.get(1);
                    String tempName = registerInfo.get(2);
                    String tempType = registerInfo.get(3);
                    if (userController.getUserManager().getUserMap().containsKey(tempUser)) {
                        errorUI.usernameTaken();
                    } else {
                        userController.addUser(tempType, tempName, tempUser, tempPW);
                        loggedIn = isValidLogin(tempUser, tempPW);
                        if (loggedIn) {
                            username = tempUser;
                            password = tempPW;
                            name = tempName;
                            currentUI.loginStatus(true);
                        } else {
                            errorUI.invalidInput();
                        }
                    }
                    break;
                case 0:
                    loggedIn = true;
                    quit = true;
                    break;
                default:
                    errorUI.invalidInput();
                    break;
            }
        }
    }


    /**
     * This function reads the user input for which type of menu to open up
     * <p>
     * 3a is Attendee signup for event
     * 1(a,o,s) is messaging for attendee, organizer, speaker
     * 4o is to open the organizer menu for modifying events
     * 5o allows the organizer to add rooms
     * 3s shows the speaker schedule
     * 4a shows the attendee schedule
     * 2(a,o,s) shows the contact menu for attendee, organizer, speaker
     * 6a, 5s, 3o shows the entire conference schedule
     * 6o to create a speaker
     * 7o the view the number of rooms
     * 5a,8o,4s to show the event details to attendee, organizer, speaker
     * Quit to exit this program
     */
    public void menuEntry() {
        currentUI.menuOptions(userController.getUserManager().checkType(username));
        String choice = input();
        switch (choice) {
            case "3a": // sign up
                userController.modifySchedule();
                break;
            case "1a": // messaging
            case "1o":
            case "1s":
                messageController.messageOptions();
                break;
            case "4o": // modify events
                eventController.modifyEvents();
                break;
            case "5o": // add room
                eventController.addRoom();
                break;
            case "3s": // see speaker schedule
                userController.seeSpeakerSchedule();
                break;
            case "4a": // see attendee schedule
                userController.seeAttendeeSchedule();
                break;
            case "2a": // add contact
            case "2s":
            case "2o":
                userController.modifyContacts();
                break;
            case "6a"://view conference schedule
            case "5s":
            case "3o":
                eventController.viewconf();
                break;
            case "6o": //create any User from Organizer
                userController.createUser();
                break;
            case "7o": // view current rooms
                eventController.currRoom();
                break;
            case "5a": // get event details
            case "8o":
            case "4s":
                eventController.getEventDetails();
                break;
            case "p":
                String path = inputDirectory();
                try {
                    convertToPDF.createPdf(path);
                } catch (Exception e) {
                    errorUI.fileInUse();
                }
                break;
            case "Quit":
                quit = true;
                break;
            default:
                errorUI.invalidInput();
                break;
        }
    }

    private String inputDirectory() {
        Scanner reader = new Scanner(System.in);
        currentUI.directoryPathPrompt();
        return reader.nextLine();
    }

    /**
     * Returns an ArrayList with a name, username and password based on user input
     * used when making an Attendee or Organizer
     *
     * @return ArrayList   List containing name, username, password
     */
    public ArrayList<String> askForInfo() {
        boolean usernameOK = false;
        Scanner reader = new Scanner(System.in);
        ArrayList<String> info = new ArrayList<>();
        while (!usernameOK) {

            currentUI.askForUserInfo("Username");
            String userName = reader.nextLine();
            if (!(userName.length() > 5) || userName.contains(" ")) {
                errorUI.invalidUsername();
            } else {
                info.add(userName);
                usernameOK = true;
            }
        }
        currentUI.askForUserInfo("Password");
        String password = reader.nextLine();
        info.add(password);
        return info;
    }

    /**
     * Returns an ArrayList with strings Attendee or Organizer based on user input
     *
     * @return ArrayList   List containing Attendee or Speaker
     */
    public ArrayList<String> registerInfo() {
        Scanner reader = new Scanner(System.in);
        boolean correctInput = false;
        ArrayList<String> info = askForInfo();
        currentUI.askForUserInfo("Name");
        String name = reader.nextLine();
        info.add(name);
        while (!correctInput) {
            currentUI.askForUserInfo("Type");
            String selection = reader.nextLine();
            switch (selection) {
                case "1":
                    info.add("Organizer");
                    correctInput = true;
                    break;
                case "2":
                    info.add("Attendee");
                    correctInput = true;
                    break;
                case "3":
                    info.add("VIP");
                    correctInput = true;
                    break;
            }
        }
        return info;
    }

    /**
     * Returns an integer that determines menu options based on user input
     * 1 to login
     * 2 to register
     *
     * @return integer    An integer that determines next menu
     */
    public int registerLogin() {
        Scanner reader = new Scanner(System.in);
        currentUI.registerLoginMenu();
        String answer = reader.nextLine();
        if (answer.equals("1")) {
            return 1;
        } else if (answer.equals("2")) {
            return 2;
        } else if (answer.equals("0")) {
            return 0;
        } else {
            return -69;
        }
    }

    /**
     * Returns a String of user input
     * Helper
     *
     * @return String   A string that relates to some menu option
     */
    private String input() {
        Scanner reader = new Scanner(System.in);
        currentUI.generalPrompt();
        return reader.nextLine();
    }

    /**
     * Returns a boolean of whether the username and password match
     * Helper
     *
     * @return boolean  a boolean that checks validity of username and password
     */
    private boolean isValidLogin(String username, String password) {
        if (!userController.getUserManager().getUserMap().containsKey(username)) {
            return false;
        }
        if (!userController.getUserManager().getUserPassword(username).equals(password)) {
            return false;
        }
        return true;
    }


    /**
     * runs the LoginController
     * @param managers manager hashamp
     * @param presenters The Presenter hashmap
     */
    public void run(HashMap<String, IManager> managers,
                    HashMap<String, IPresenter> presenters) {
        eventController = new EventController(managers, presenters);
        currentUI = (LoginUI) presenters.get("loginUI");
        errorUI = (ErrorUI) presenters.get("errorUI");
        userController = new UserController(managers, presenters);
        convertToPDF = new ConvertToPDF();
        enterUserInfo();
        userController.setUser(username);
        if (!quit) {
            usertype = userController.getUserManager().checkType(username);


            messageController = new MessageController(managers, presenters, username, usertype);
        }
        while (!quit) {
            menuEntry();
        }
    }

    /**
     * Returns the UserManager stored in this controller
     *
     * @return UserManager   The UserManager stored in this controller
     */
    public UserManager getUserManager() {
        return userController.getUserManager();
    }

    /**
     * Returns the SpeakerManager stored in this controller
     *
     * @return SpeakerManager   The SpeakerManager stored in this controller
     */
    public SpeakerManager getSpeakerManager() {
        return userController.getSpeakerManager();
    }

    /**
     * Returns the EventManager stored in this controller
     *
     * @return EventManager   The EventManager stored in this controller
     */
    public EventManager getEventManager() {
        return eventController.getEventManager();
    }

    /**
     * Returns the MessageManager stored in this controller
     *
     * @return MessageManager   The MessageManager stored in this controller
     */
    public MessageManager getMessageManager() {
        return userController.getMessageManager();
    }
}

