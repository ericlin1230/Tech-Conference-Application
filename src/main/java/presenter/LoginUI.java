package presenter;
import interfaces.IPresenter;

/**
 * The LoginUI provides the prompts for login services
 *
 * @author Ryan Wang, Kieran Atgerberg
 * @version 1.1
 */
public class LoginUI implements IPresenter {

    /**
     * Constructor for LoginUI
     */
    public LoginUI() {
        ;
    }

    /**
     * Print menu options available depending on string type input
     *
     * @param type String representing the type of User that accesses
     */
    public void menuOptions(String type) {
        System.out.println("\n" + "To select an option, type what's inside the parenthesis.");
        System.out.println("While in a sub-menu -> (0) Back out of a sub-menu. \n");
        System.out.println("Main Menu");
        if (type.equals("Attendee") || type.equals("VIP")) {
            System.out.println("(1a) Message, (2a) Contacts, (3a) Signup, (4a) See Attendee Schedule");
            System.out.println("(5a) Get Event Details, (6a) View Conference Schedule, "+
                    " (p) Export the conference schedule to a PDF");
        } else if (type.equals("Organizer")) {
            System.out.println("(1o) Message, (2o) Contacts, (3o) View Conference Schedule");
            System.out.println("(4o) Modify Events, (5o) Add room, (6o) Create a User account"+
                    " (p) Export the conference schedule to a PDF");
            System.out.println("(7o) View Current Rooms, (8o) Get Event Details");
        } else if (type.equals("Speaker")) {
            System.out.println("(1s) Message, (2s) Contacts, (3s) See Speaker Schedule");
            System.out.println("(4s) Get Event Details, (5s) View Conference Schedule"+
                    " (p) Export the conference schedule to a PDF");
        }
        System.out.println("Type Quit to logout");
    }

    /**
     * Print status of login
     *
     * @param success boolean that represents if the login will succeed
     */
    public void loginStatus(Boolean success) {
        if (success.equals(true))
            System.out.println("Login Successful");
        else {
            System.out.println("Login Failed -> Username or password may be incorrect");
        }
    }

    /**
     * Prints messages for the signUpForEvent method in UserController
     *
     * @param type integer determining which statement to print
     */
    public void signUpForEventReporter(int type) {
        switch (type) {
            case 1:
                System.out.println("Here are the available Events");
                break;
            case 2:
                System.out.println("Please select one of the available eventID or print '0' to exit");
                break;
            case 3:
                System.out.println("The signup is cancelled");
                break;
            case 4:
                System.out.println("Event Detail");
                break;
        }

    }


    /**
     * Prompts for the event type
     */
    public void askForType() {
        System.out.println("What type of event will this be?");
        System.out.println("1) Normal 2) Multi-Speaker 3) VIP");
    }


    /**
     * Prints a general prompt asking what the user would like to do.
     */
    public void generalPrompt() {
        System.out.println("What would you like to do?");
    }

    /**
     * Prints different prompts based on type of User info
     *
     * @param type A string that determines the print statement
     */
    public void askForUserInfo(String type) {
        switch (type) {
            case "Username":
                System.out.println("Enter username: ");
                break;
            case "Password":
                System.out.println("Enter password: ");
                break;
            case "Name":
                System.out.println("Enter Name: ");
                break;
            case "Type":
                System.out.println("Select the user type:");
                System.out.println("1. Organizer");
                System.out.println("2. Attendee");
                System.out.println("3. VIP");
                break;
        }
    }


    /**
     * Prints different prompts based on type of User
     *
     * @param type A string that determines the print statement
     */
    public void successfulCreation(String type) {
        switch (type) {
            case "Attendee":
                System.out.println("Attendee Created");
                break;
            case "Organizer":
                System.out.println("Organizer Created");
                break;
            case "Speaker":
                System.out.println("Speaker Created");
                break;
            case "VIP":
                System.out.println("VIP Created");
                break;
        }
    }

    /**
     * Prints prompts for the register/login menu
     */
    public void registerLoginMenu() {
        System.out.println("Do you want to Login or Register?");
        System.out.println("(Enter the number without parenthesis to choose an option)");
        System.out.println("Enter (1) Login, (2) Register");
    }


    /**
     * Prints options after accessing schedule menu options
     */
    public void scheduleOptions() {
        System.out.println("Would you like to (1) Add an Event, (2) Remove an Event, (3) Exit this menu");
    }

    /**
     * Prints  after accessing schedule menu option 2
     */
    public void time() {
        System.out.println("Enter the time you wish to free up");
    }

    /**
     * Prints prompt for asking for event ID in making a new event
     */
    public void askForEventID() {
        System.out.println("What is the event ID?");
    }


    /**
     * Prints prompt for asking for creating a user through organizers
     */
    public void askForUserType() {
        System.out.println("What type of User would you like to make? (Type Attendee, Speaker, VIP, or Organizer)");
    }

    /**
     * Prints prompt for successful action
     */
    public void actionSuccessful() {
        System.out.println("Action performed successfully!");
    }

    /**
     * Provides options at the start of the program
     */
    public void programStart() {
        System.out.println("Type 0 to end the program, type 1 to go to Login/Register.");
        System.out.println("Type RESET to complete erase and reset the database.");
    }

    /**
     * Prompt for a file path
     */
    public void directoryPathPrompt() {
        System.out.println("Enter a file path to export the PDF of the schedule to.");
    }

    /**
     * Asks for input for speaker username
     */
    public void askForSpeaker(){
        System.out.println("Enter the speaker username");
    }

}
