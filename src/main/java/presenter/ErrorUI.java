package presenter;

import interfaces.IPresenter;

/**
 * The ErrorUI provides the prompts for error messages
 *
 * @author Ryan Wang, Kieran Agterberg
 * @version 1.0
 */
public class ErrorUI implements IPresenter {

    /**
     * Constructor for ErrorIU, initializes an ErrorUI object
     */
    public ErrorUI() {
    }

    /**
     * Each method is a print statement
     */

    public void invalidInput() {
        System.out.println("Invalid input, try again!");
    }


    /**
     * Prints if a username is taken
     */
    public void usernameTaken() {
        System.out.println("Username already exists");
    }

    /**
     * Prints if a username is invalid
     */
    public void invalidUsername() {
        System.out.println("Usernames must be of length greater than 5 and contain no spaces");
    }

    /**
     * Prints prompt if room is full
     */
    public void maxroomreach() {
        System.out.println("The room is full");
    }

    /**
     * Prints prompt if User is not VIP and wants to join VIP-only event
     */
    public void notVIP() {
        System.out.println("This is a VIP only event and you are not one! Consider purchasing the VIP pass.");
    }

    /**
     * Prints prompt if eventID or Speaker name are invalid
     */
    public void invalidSpeakerOrEventID() {
        System.out.println("That is not a valid eventID or Speaker");
    }

    /**
     * Prints prompt if speaker is not available to join event at that time
     */
    public void speakerNotAvailableorFullEvent() {
        System.out.println("Speaker is not available at the time or the event is full");
    }

    /**
     * Prints prompt if time slot is not available
     */
    public void invalidTime() {
        System.out.println("The selected time slot in the selected " +
                "room is not available.");
    }

    /**
     * Prints prompt if eventID does not exist or is invalid
     */
    public void invalidEventID() {
        System.out.println("This is not a valid Event ID.");
    }

    /**
     * Prints prompt if speaker does not exist or is invalid
     */
    public void invalidSpeaker() {
        System.out.println("This not a valid Speaker.");
    }

    /**
     * Prints prompt if messageID does not exist or if it is not a valid request
     */
    public void invalidMessageID(){
        System.out.println("This is not a valid request ID.");
    }

    /**
     * Prints prompt for user who is busy during the timeslot
     */
    public void unavailableUser() {
        System.out.println("This user is not available at the time");
    }

    /**
     * Prints prompt if User is invalid
     */
    public void invalidUser() {
        System.out.println("This not a valid User.");
    }

    /**
     * Print prompt for invalid User message recipients
     * @param num The number of the users that was invalid
     */
    public void numInvalidUsers(int num) {
        System.out.println("There were " + num + " users that are not valid recipients.(Duplicates Omitted)");
    }

    /**
     * Prints prompt for empty message recipient list
     */
    public void emptyRecipients() {
        System.out.println("The recipients is empty");
    }

    /**
     * Prints prompt for empty Event
     */
    public void noAttendees() {
        System.out.println("This event currently has no Attendees");
    }

    /**
     * Prints prompt for if a speaker is not speaking at a certain event
     */
    public void notSpeaking() {
        System.out.println("You are not speaking at this event");
    }

    /**
     * Prints prompt if Organizer does not exist
     */
    public void noOrganizer() {
        System.out.println("This organizer does not exist");
    }

    /**
     * Prints prompt if room does not exist or if ID is invalid
     */
    public void invalidRoom() {
        System.out.println("Incorrect input of room ID.");
    }

    /**
     * Prints prompt if event size is invalid (doesn't match room)
     */
    public void invalidEventSize(){
        System.out.println("The event capacity is invalid, check room capacity");
    }

    /**
     * Prints error to show that the pdf file is in use and cannot create a new table
     */
    public void fileInUse(){
        System.out.println("Close the pdf file in use or double check destination");
    }

}
