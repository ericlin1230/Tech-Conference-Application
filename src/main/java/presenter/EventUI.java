package presenter;

import event.EventManager;
import interfaces.IPresenter;

/**
 * The EventUI provides the prompts for Event services
 *
 * @author Zihao Sheng, Ryan Wang
 * @version 1.0
 */
public class EventUI implements IPresenter {
    EventManager eventManager;

    /**
     * Constructor of EventUI, initializes EventManager
     *
     * @param eventManager EventManager instance
     */
    public EventUI(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * A smart print method to create a single slot in the from
     *
     * @param title   The type of the message in the slot.
     * @param message The content in the slot.
     */
    public void smartPrint(String title, String message) {
        int max = title.length() + 12;
        int len = message.length();
        if (max > len) {
            System.out.print(message);
            for (int i = 1; i <= (max - len); i++) {
                System.out.print(" ");
            }
        } else if (max == len) {
            System.out.print(message);
        } else if (max < len) {
            for (int j = 0; j <= (max - 4); j++) {
                System.out.print(message.charAt(j));
            }
            System.out.print("...");
        }
        System.out.print("|");
    }


    /**
     * Smart prints all the info about the event with eventID
     */
    private void printLine(String eventID) {
        eventManager.getEventList().get(eventID);
        System.out.print("|");
        String tempSpeaker = "";
        try {
            tempSpeaker = eventManager.getSpeakers(eventID).get(0);
        } catch (Exception e) {
            ;
        }
        smartPrint("EventName", eventManager.getEventName(eventID));
        smartPrint("eventID", eventID);
        smartPrint("StartTime", eventManager.getStartTime(eventID));
        smartPrint("endTime", eventManager.getEndTime(eventID));
        smartPrint("Speakers", tempSpeaker);
        smartPrint("RoomID", eventManager.getRoomID(eventID));
        if (eventManager.isFull(eventID)) {
            smartPrint("Availability", "No");
        } else {
            smartPrint("Availability", "Yes");
        }
        System.out.print("\n");
    }

    /**
     * Smart prints all the info about the event with eventID
     */
    private void printLine2(String roomID) {
        System.out.print("|");
        String Cap = Integer.toString(eventManager.getRoomCap(roomID));
        smartPrint("RoomID", roomID);
        smartPrint("Capacity", Cap);
        System.out.print("\n");
    }


    /**
     * Prints the outline of all the info necessary to describe an event
     */

    private void printTitle() {
        System.out.print("|      EventName      |");
        System.out.print("      EventID      |");
        System.out.print("      StartTime      |");
        System.out.print("      EndTime      |");
        System.out.print("      Speaker(s)    |");
        System.out.print("      RoomID      |");
        System.out.print("      Availability      |");
    }

    /**
     * Prints the outline of all the info necessary to describe a room
     */

    private void printRoomTitle() {
        System.out.print("|      RoomID      |");
        System.out.print("      Capacity      |");
    }

    /**
     * Prints the detail of an event
     *
     * @param e the event ID of the event
     */
    public void printDetail(String e) {
        System.out.println("Event Name: " + eventManager.getEventName(e));
        System.out.println("EventID: " + e);
        System.out.println("Start Time: " + eventManager.getStartTime(e));
        System.out.println("End Time: " + eventManager.getEndTime(e));
        System.out.println("Speakers: " + eventManager.getSpeakers(e));
        System.out.println("RoomID: " + eventManager.getRoomID(e));
        System.out.print("Availability: ");
        if (eventManager.isFull(e)) {
            System.out.println("No");
        }
        if (!eventManager.isFull(e)) {
            System.out.println("Yes");
        }
    }

    /**
     * Prints prompt for room capacity
     */
    public void askForCapacity() {
        System.out.println("Input the capacity");
    }

    /**
     * printRooms method, prints all the rooms in the program
     */
    public void printRooms() {
        printRoomTitle();
        System.out.print("\n");
        for (String i : eventManager.getEventSchedule().keySet()) {
            printLine2(i);
        }
    }

    /**
     * printEvent method, which prints all the available events into a list.
     */
    public void printEvents() {
        printTitle();
        System.out.print("\n");
        for (String i : eventManager.getEventList().keySet()) {
            if (!eventManager.getRoomID(i).equals("0") && !eventManager.getStartTime(i).equals("0")) {
                printLine(i);
            }
        }
    }

    /**
     * printEvent method, which prints all the event the specific speaker is in.
     *
     * @param speaker The speaker username
     */
    public void printSpeakerSchedule(String speaker) {
        printTitle();
        System.out.print("\n");
        for (String i : eventManager.getEventList().keySet()) {
            if (!eventManager.getRoomID(i).equals("0") && !eventManager.getStartTime(i).equals("0")) {
                if (eventManager.getSpeakers(i).contains(speaker)) {
                    printLine(i);
                }
            }
        }
    }

    /**
     * printEvent method, which prints all the event the specific user is in.
     *
     * @param user The speaker username
     */
    public void printUserSchedule(String user) {
        printTitle();
        System.out.print("\n");
        for (String i : eventManager.getEventList().keySet()) {
            if (!eventManager.getRoomID(i).equals("0") && !eventManager.getStartTime(i).equals("0")) {
                if (eventManager.getAttendees(i).contains(user)) {
                    printLine(i);
                }
            }
        }
    }

    /**
     * Prints prompt for asking for eventID
     */
    public void askForEventID() {
        System.out.println("Please enter the event ID, type exit to exit");
    }

    /**
     * Prints prompt for asking for organizer event options
     */
    public void askEventOptions() {
        System.out.println("Would you like to (1) Delete an event, (2) Add an event, (3) Add speaker to event, (4) Change Capacity");
    }

    /**
     * Prints prompt for asking for eventID to delete event
     */
    public void enterDeleteEvent() {
        System.out.println("What event would you like to delete (Enter Event Id)");
    }

    /**
     * Prints prompt asking for a User type to be entered
     *
     * @param type String representing User type (Attendee, Organizer, etc.)
     */
    public void enter(String type) {
        System.out.println("Enter " + type);
    }

    /**
     * Prints prompt for room add successful
     *
     * @param roomID String representing Room ID
     */
    public void addRoomSuccess(String roomID) {
        System.out.println("Added Room with Room ID: " + roomID);
    }

    /**
     * Prints prompt for successful eventID added
     * @param eventID The ID of the event that has been added
     */
    public void addEventSuccess(String eventID) {
        System.out.println("Event with ID " + eventID + " has been added");
    }

    /**
     * Shows the user the min capacity and max capacity
     * @param minCap The minimum capacity for the room
     * @param maxCap The maximum capacity for the room
     */
    public void minMaxCap(int minCap, int maxCap) {
        System.out.println("Minimum capacity for this event is " + minCap);
        System.out.println("Maximum capacity for this event is " + maxCap);
    }

}
