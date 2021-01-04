package controller;

import user.SpeakerManager;
import user.UserManager;
import event.Event;
import event.EventManager;
import interfaces.IManager;
import interfaces.IPresenter;
import presenter.ErrorUI;
import presenter.EventUI;
import presenter.LoginUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * The EventController class controls what User's options are for Event-related options
 *
 * @author Eric Lin
 * @version 1.0
 */
public class EventController {
    private EventUI EventUI;
    private ErrorUI errorUI;
    private LoginUI currentUI;
    private UserManager userManager;
    private SpeakerManager speakerManager;
    private EventManager eventManager;
    String username;
    String userType;

    /**
     * Constructor for EventController, initializes an EventController object
     *
     * @param managers   HashMap of String keys that map to IManager objects (Managers)
     * @param presenters HashMap of String keys that map to IPresenter objects (Presenters)
     */
    public EventController(
            HashMap<String, IManager> managers,
            HashMap<String, IPresenter> presenters
    ) {
        this.EventUI = (EventUI) presenters.get("eventUI");
        this.errorUI = (ErrorUI) presenters.get("errorUI");
        this.currentUI = (LoginUI) presenters.get("loginUI");
        this.userManager = (UserManager) managers.get("userManager");
        this.speakerManager = (SpeakerManager) managers.get("speakerManager");
        this.eventManager = (EventManager) managers.get("eventManager");
        this.userType = userType;
        this.username = username;
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
     * Obtains the event details based on user input of a event
     */
    public void getEventDetails() {
        currentUI.askForEventID();
        String choice = enterMessage();
        if (eventManager.getEventList().containsKey(choice)) {
            EventUI.printDetail(choice);
        } else {
            errorUI.invalidInput();
        }
    }

    /**
     * Prints the number of rooms that currently exists
     */
    public void currRoom() {
        EventUI.printRooms();
    }

    /**
     * prints all of the events to the UI
     */
    public void viewconf() {
        EventUI.printEvents();
    }

    /**
     * Creates a new room
     */
    public void addRoom() {
        EventUI.askForCapacity();
        Scanner reader = new Scanner(System.in);
        String capacity = reader.nextLine();
        int cap = -20;
        try {
            cap = Integer.parseInt(capacity);
        } catch (Exception e) {
            errorUI.invalidInput();
        }
        if (cap >= 2) {
            String roomID = eventManager.addRoom(cap);
            EventUI.addRoomSuccess(roomID);
        } else {
            errorUI.invalidInput();
        }
    }

    /**
     * Creates a new events based on parameters
     * Organizer exclusive
     *
     * @param roomID    The Id of the room.
     * @param startTime The time that this event starts.
     * @param endTime   The time that this event ends.
     * @param eventName The name of the event.
     * @param capacity  The capacity of the event
     * @return String of event ID
     */
    public String addEventToSchedule(String roomID, String startTime, String endTime, String eventName, Integer capacity) {
        String eventID = eventManager.addEvent(roomID, startTime, endTime, eventName, capacity);
        switch (eventID) {
            case "-2": //invalid time
                errorUI.invalidTime();
                break;
            case "-3": //invalid room
                errorUI.invalidRoom();
                break;
            default:
                EventUI.addEventSuccess(eventID);
                break;
        }
        return eventID;
    }

    /**
     * Performs different event modifications based on user input
     * 1 deletes events
     * 2 adds events
     * 3 adds speaker to events
     */
    public void modifyEvents() {
        String choice = modifyEventsOptions();
        if (choice.equals("1")) {
            String option = deleteEvent();
            if (!eventManager.getEventList().containsKey(option)) {
                errorUI.invalidEventID();
                return;
            }
            for (String speaker : eventManager.getSpeakers(option)) {
                speakerManager.removeSpeakerEvent(speaker,
                        eventManager.getStartTime(option));
            }
            for (String attendee : eventManager.getAttendees(option)) {
                userManager.removeUserEvent(attendee,
                        eventManager.getStartTime(option));
            }
            String result = eventManager.delEvent(option);
            if (result.equals("0")) {
                currentUI.actionSuccessful();
            } else {
                errorUI.invalidEventID();
            }
        } else if (choice.equals("2")) {
            ArrayList<String> info = addEvent();
            EventUI.askForCapacity();
            Scanner reader = new Scanner(System.in);
            String capacity = reader.nextLine();
            int cap = 0;
            try {
                cap = Integer.parseInt(capacity);
            } catch (Exception e) {
                errorUI.invalidInput();
                return;
            }
            if (cap >= 2) {
                int roomcap = eventManager.getRoomCap(info.get(0));
                if (cap <= roomcap) {
                    String eventID = addEventToSchedule(info.get(0), info.get(1), info.get(2), info.get(3), cap);
                    if (!eventID.equals("-2") && !eventID.equals("-3")) {
                        while (true) {
                            currentUI.askForType();
                            String type = reader.nextLine();
                            int typeInt = 0;
                            try {
                                typeInt = Integer.parseInt(type);
                            } catch (Exception e) {
                                errorUI.invalidInput();
                            }
                            if (typeInt == 1) {
                                break;
                            } else if (typeInt == 2) {
                                eventManager.setType(eventID, 2);
                                break;
                            } else if (typeInt == 3) {
                                eventManager.setType(eventID, 3);
                                break;
                            }
                        }
                    }
                } else {
                    errorUI.invalidEventSize();
                }
            } else {
                errorUI.invalidInput();
            }
        } else if (choice.equals("3")) {
            boolean boolOK = false;
            while (!boolOK) {
                String eventID = enterEventID();
                if (eventID.equals("exit")) {
                    break;
                }
                currentUI.askForSpeaker();
                String speaker = enterMessage();
                if (speaker.equals("exit")) {
                    break;
                }
                if (eventManager.getEventList().containsKey(eventID)
                        && userManager.getUserMap().containsKey(speaker)) {
                    boolOK = true;
                    addSpeakerToEvent(speaker, eventID);
                } else {
                    errorUI.invalidSpeakerOrEventID();
                }
            }
        } else if (choice.equals("4")) {
            setCapacity();
        } else {
            errorUI.invalidInput();
        }
    }

    /**
     * Sets event capacity and returns new capacity
     *
     * @return int  The new event capacity
     */
    public int setCapacity() {
        Scanner reader = new Scanner(System.in);
        currentUI.askForEventID();
        String eventID = reader.nextLine();

        if (!eventManager.getEventList().containsKey(eventID)) {
            errorUI.invalidEventID();
            return 0;
        }
        int minCap = eventManager.getAttendees(eventID).size() + eventManager.getSpeakers(eventID).size();
        int maxCap = eventManager.getRoomCap(eventManager.getRoomID(eventID));
        if (minCap <= 2) {
            minCap = 2;
        }
        EventUI.minMaxCap(minCap, maxCap);
        EventUI.askForCapacity();

        String capacity = reader.nextLine();
        int cap = 0;

        try {
            cap = Integer.parseInt(capacity);
        } catch (Exception e) {
            errorUI.invalidInput();
            return 0;
        }

        if (cap >= minCap && cap<= maxCap) {
            eventManager.addRoom(cap);
        } else {
            errorUI.invalidInput();
            return 0;
        }
        eventManager.setCapacity(eventID, cap);
        currentUI.actionSuccessful();
        return cap;
    }


    /**
     * Adds a speaker to the event
     * Organizer exclusive
     *
     * @param speakerUsername The username of the speaker.
     * @param eventID         The eventID to add the speaker to.
     * @return boolean if the addition has passed
     */
    public boolean addSpeakerToEvent(String speakerUsername, String eventID) {
        if (speakerManager.getSpeakerMap().containsKey(speakerUsername)) {
            if (eventManager.getEventList().containsKey(eventID)) {
                String time = eventManager.getStartTime(eventID);
                boolean capPass = false;
                if (eventManager.getSpeakers(eventID).size() == 0 || eventManager.getType(eventID) == 2) {
                    capPass = true;
                }
                if (speakerManager.checkIfSpeakerAvail(speakerUsername, time) && capPass) {
                    eventManager.addSpeaker(speakerUsername, eventID);
                    speakerManager.addSpeakerEvent(speakerUsername, time, eventID);
                    currentUI.actionSuccessful();
                    return true;
                } else {
                    errorUI.speakerNotAvailableorFullEvent();
                }
            } else {
                errorUI.invalidEventID();
            }
        } else {
            errorUI.invalidSpeaker();
        }
        return false;
    }

    /**
     * Returns the EventManager that is stored in this Controller
     *
     * @return EventManager      The EventManager that is stored under this Controller
     */
    public EventManager getEventManager() {
        return this.eventManager;
    }

    /**
     * Returns a string based on user input representing eventID
     *
     * @return String  a String based on user input
     */
    public String enterEventID() {
        EventUI.askForEventID();
        Scanner reader = new Scanner(System.in);
        return reader.nextLine();
    }

    /**
     * Returns a string based on user input representing the menu options for modifying events
     *
     * @return String  a String based on user input
     */
    public String modifyEventsOptions() {
        EventUI.askEventOptions();

        Scanner reader = new Scanner(System.in);

        return reader.nextLine();

    }


    /**
     * Returns the event list stored in this controller
     *
     * @return HashMap   The event list stored in this controller
     */
    public HashMap<String, Event> getEventList() {
        return eventManager.getEventList();
    }


    /**
     * Returns a Arraylist of string based on user input representing the event name, room ID, start time, end time
     *
     * @return Arraylist   an Arraylist of strings based on inputs
     */
    public ArrayList<String> addEvent() {

        ArrayList<String> info = new ArrayList<>();
        Scanner reader = new Scanner(System.in);


        EventUI.enter("Name");
        String eventName = reader.nextLine();
        EventUI.enter("Room ID");
        String roomID = reader.nextLine();
        EventUI.enter("Start Time");
        String startTime = reader.nextLine();
        EventUI.enter("End Time");
        String endTime = reader.nextLine();

        info.add(roomID);
        info.add(startTime);
        info.add(endTime);
        info.add(eventName);
        return info;
    }


    /**
     * Returns a string based on user input representing the eventID to delete
     *
     * @return String  a String based on user input
     */
    public String deleteEvent() {
        viewconf();
        EventUI.enterDeleteEvent();
        Scanner reader = new Scanner(System.in);

        return reader.nextLine();

    }

}
