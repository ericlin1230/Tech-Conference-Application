package event;

import interfaces.IManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Eric Lin
 * @version 1.0
 */
public class EventManager implements Serializable, IManager {

    private HashMap<String, Room> eventSchedule;
    private HashMap<String, Event> eventList;
    private int eventCount;
    private int roomCount;

    /**
     * Constructor of the EventManager class, initialization.
     *
     * @param schedule  The pre-existing event schedule from gateway.
     * @param eventList The pre-existing event list from gateway.
     */
    public EventManager(HashMap<String, Room> schedule, HashMap<String, Event> eventList) {
        this.eventList = new HashMap<>();
        this.eventList = eventList;
        this.eventSchedule = new HashMap<>();
        this.eventSchedule = schedule;
        this.eventCount = eventList.size();
        this.roomCount = eventSchedule.size();
    }

    /**
     * Adds an event with the specific start time, end time, and room ID.
     *
     * @param roomID    The room ID the new event is added to.
     * @param startTime The start time of the new event.
     * @param endTime   The end time of the new event.
     * @param eventName The event name of the Event
     * @param capacity  The capacity of the event
     * @return Event ID
     */
    public String addEvent(String roomID, String startTime, String endTime,
                           String eventName, Integer capacity) {
        int checkResult = checkAvailability("-1", roomID, startTime, endTime);
        String eCount = "-1";
        switch (checkResult) {
            case 0:
                return "-2";
            case 1:
                this.eventCount++;
                Room aRoom = this.eventSchedule.get(roomID);
                eCount = Integer.toString(this.eventCount);
                int duration = Integer.parseInt(endTime) - Integer.parseInt(startTime);
                if (aRoom.addEvent(Integer.parseInt(startTime), eCount, duration)) {
                    Event newEvent = new Event(eCount, roomID, startTime, endTime, eventName, capacity);
                    this.eventList.put(eCount, newEvent);
//                    this.eventSchedule.get(roomID).put(startTime, eCount);
                } else {
                    this.eventCount--;
                }
                break;
            case 3:
                return "-3";
            case 4:
                return "-2";
        }
        return eCount;
    }

    /**
     * Set the type of the event, 1 for normal, 2 for unlimited speaker, 3 for vip
     *
     * @param eventID The event ID of the event
     * @param type The type of the event
     * @return Boolean of if the method ran successfully
     */
    public boolean setType(String eventID, Integer type) {
        int checkResult = checkAvailability(eventID, "-1",
                "-1", "-1");
        if (checkResult == 5) {
            Event oldEvent = eventList.get(eventID);
            oldEvent.setType(type);
            return true;
        }
        return false;
    }

    /**
     * Set the capacity of an event
     *
     * @param eventID  The event ID of the event
     * @param capacity the new capacity of the event
     * @return if the process was successful
     */
    public boolean setCapacity(String eventID, Integer capacity) {
        int checkResult = checkAvailability(eventID, "-1",
                "-1", "-1");
        if (checkResult == 5) {
            Event oldEvent = eventList.get(eventID);
            Room room1 = eventSchedule.get(oldEvent.getRoomID());
            int cap = room1.getRoomCapacity();
            if (capacity <= cap) {
                oldEvent.setCapacity(capacity);
                return true;
            }
        }
        return false;
    }

    // Private helper function
    // Checks the availability of time and the validity of inputted values
    // 0 The room is not available at given time
    // 1 The room is available at given time
    // 2 The event ID is invalid
    // 3 the room ID is invalid
    // 4 The start time is invalid
    // 5 The event is in the list
    private int checkAvailability(String eventID, String roomID,
                                  String startTime, String endTime) {
        int start2 = Integer.parseInt(startTime);
        int duration = Integer.parseInt(endTime) - Integer.parseInt(startTime);
        if (eventList.containsKey(eventID) && startTime.equals("-1") &&
                roomID.equals("-1")) {
            return 5;
        }
        if (!eventList.containsKey(eventID) && !eventID.equals("-1")) {
            return 2;
        }
        if (!eventSchedule.containsKey(roomID)) {
            return 3;
        }
        if (!eventSchedule.get(roomID).getTimeSlot().containsKey(start2)) {
            return 4;
        }
        if (!endTime.equals("-1") && eventSchedule.get(roomID).checkAvailability(start2, duration)) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Clear an event from the availability.
     * Sets the start and end time to 0, room ID to 0.
     *
     * @param eventID The event ID that is to be deleted.
     * @return String The string to indicate the errors of the method
     */
    public String delEvent(String eventID) {
        int checkResult = checkAvailability(eventID, "-1",
                "-1", "-1");
        if (checkResult == 5) {
            // Retrieve the event entity via ID
            Event oldEvent = eventList.get(eventID);
            String oldRoom = oldEvent.getRoomID();
            this.eventSchedule.get(oldRoom).removeEvent(eventID); // remove the
            // old event time from the old event room

            // Update the information in the event entity.
            oldEvent.setEndTime("0");
            oldEvent.setStartTime("0");
            oldEvent.setRoomID("0");
            this.eventList.remove(eventID);
            return "0";
        } else {
            return "-1";
        }
    }

    /**
     * Returns a hashmap of event with ID, this includes all
     * events (i.e. deleted).
     *
     * @return A hashmap of full event list.
     */
    public HashMap<String, Event> getEventList() {
        return eventList;
    }

    /**
     * Returns the room an event is taking place in.
     *
     * @param eventID The event ID to look for the room.
     * @return The room ID the event taking place in.
     */
    public String getRoomID(String eventID) {
        int checkResult = checkAvailability(eventID, "-1",
                "-1", "-1");
        if (checkResult == 5) {
            // Retrieve the event entity via ID
            Event oldEvent = eventList.get(eventID);
            return (oldEvent.getRoomID());
        } else {
            return "0";
        }
    }

    /**
     * Get the current number of rooms in the event
     *
     * @return The number of rooms in the event.
     */
    public int getRoomNum() {
        return eventSchedule.size();
    }

    /**
     * Returns the full event schedule with each rooms
     *
     * @return The full event schedule with the rooms.
     */
    public HashMap<String, Room> getEventSchedule() {
        return eventSchedule;
    }

    /**
     * Gets the start time of an event based on inputted event ID.
     *
     * @param eventID Th event ID wished to be used to look up.
     * @return String of event start time of the provided event ID.
     */
    public String getStartTime(String eventID) {
        int checkResult = checkAvailability(eventID, "-1",
                "-1", "-1");
        if (checkResult == 5) {
            // Retrieve the event entity via ID
            Event oldEvent = eventList.get(eventID);
            return (oldEvent.getStartTime());
        } else {
            return "0";
        }
    }

    /**
     * Gets the end time of an event based on inputted event ID.
     *
     * @param eventID Th event ID wished to be used to look up.
     * @return String of event start time of the provided event ID.
     */
    public String getEndTime(String eventID) {
        int checkResult = checkAvailability(eventID, "-1",
                "-1", "-1");
        if (checkResult == 5) {
            // Retrieve the event entity via ID
            Event oldEvent = eventList.get(eventID);
            return (oldEvent.getEndTime());
        } else {
            return "0";
        }
    }

    /**
     * Returns a boolean and tell if the user is already in the inputted event.
     *
     * @param eventID The event ID to be checked.
     * @param userID  The user ID to be checked.
     * @return boolean to tell if the user is already in the event.
     */
    public boolean hasAttendee(String eventID, String userID) {
        int checkResult = checkAvailability(eventID, "-1",
                "-1", "-1");
        if (checkResult == 5) {
            // Retrieve the event entity via ID
            Event oldEvent = eventList.get(eventID);
            return (oldEvent.hasAttendee(userID));
        } else {
            return false;
        }
    }


    /**
     * Returns a boolean and tell if the speaker is already in the
     * inputted event.
     *
     * @param eventID The event ID to be checked.
     * @param userID  The user ID to be checked.
     * @return boolean to tell if the speaker is already in the event. Returns
     * false if the event ID is invalid
     */
    public boolean hasSpeaker(String eventID, String userID) {
        int checkResult = checkAvailability(eventID, "-1",
                "-1", "-1");
        if (checkResult == 5) {
            // Retrieve the event entity via ID
            Event oldEvent = eventList.get(eventID);
            return (oldEvent.hasSpeaker(userID));
        } else {
            return false;
        }
    }

    /**
     * Gets the list of attendee IDs from the specified event ID.
     *
     * @param eventID The event ID to look up the list of attendees
     * @return The list of attendees in the provided event. Returns null if
     * the event ID is invalid.
     */
    public ArrayList<String> getAttendees(String eventID) {
        int checkResult = checkAvailability(eventID, "-1",
                "-1", "-1");
        if (checkResult == 5) {
            // Retrieve the event entity via ID
            Event oldEvent = eventList.get(eventID);
            return (oldEvent.getAttendees());
        } else {
            return null;
        }
    }

    /**
     * Returns the name of the event with the event ID inputted.
     *
     * @param eventID The event ID to look for the event name.
     * @return The event name of the event, returns null if invalid event ID.
     */
    public String getEventName(String eventID) {
        int checkResult = checkAvailability(eventID, "-1",
                "-1", "-1");
        if (checkResult == 5) {
            // Retrieve the event entity via ID
            Event oldEvent = eventList.get(eventID);
            return (oldEvent.getEventName());
        } else {
            return null;
        }
    }

    /**
     * Gets the list of the speaker of the inputted event ID.
     *
     * @param eventID The event that user wants to get the speaker list.
     * @return ArrayList of the list of speakers from the event. Returns null
     * if invalid event ID inputted.
     */
    public ArrayList<String> getSpeakers(String eventID) {
        int checkResult = checkAvailability(eventID, "-1",
                "-1", "-1");
        if (checkResult == 5) {
            // Retrieve the event entity via ID
            Event oldEvent = eventList.get(eventID);
            return (oldEvent.getSpeakers());
        } else {
            return null;
        }
    }

    public int getRoomCap(String roomID) {
        if (eventSchedule.containsKey(roomID)) {
            Room temp = eventSchedule.get(roomID);
            return temp.getRoomCapacity();
        }
        return -1;
    }

    /**
     * Adds an attendee to the event based on the user ID and the event ID
     * provided.
     * The method will assume user has available time at when the event takes
     * place, the availability should be checked by the controller.
     *
     * @param userID  The user ID of the user that wishes to join the new event.
     * @param eventID The event ID of the event that the user wishes to join.
     * @return Boolean indicating whether the addition was successful.
     */
    public boolean addAttendee(String userID, String eventID) {
        int checkResult = checkAvailability(eventID, "-1",
                "-1", "-1");
        if (checkResult == 5) {
            // Retrieve the event entity via ID
            if (hasAttendee(eventID, userID)) {
                return false;
            }
            Event oldEvent = eventList.get(eventID);
            if (oldEvent.isFull()) {
                return false;
            } else {
                oldEvent.addAttendee(userID);
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * Adds speaker to the event.
     * The method assumes the speaker has no other events at the time of the
     * inputted event ID.
     *
     * @param userID  The user ID of the speaker that wishes to join the event.
     * @param eventID The event ID of the event that the speaker wishes to join.
     * @return Boolean indicating whether the addition was successful.
     */
    public boolean addSpeaker(String userID, String eventID) {
        int checkResult = checkAvailability(eventID, "-1",
                "-1", "-1");
        if (checkResult == 5) {
            // Retrieve the event entity via ID
            if (hasSpeaker(eventID, userID)) {
                return false;
            }
            Event oldEvent = eventList.get(eventID);
            if (oldEvent.getSpeakerCount() >= 1 && oldEvent.getType() != 2) { // limited to 1 speaker: phase1
                return false;
            } else {
                oldEvent.addSpeaker(userID);
                return true;
            }
        } else {
            return false;
        }
    }

    public int getType(String eventID) {
        int checkResult = checkAvailability(eventID, "-1",
                "-1", "-1");
        if (checkResult == 5) {
            Event oldEvent = eventList.get(eventID);
            return oldEvent.getType();
        }
        return -1;
    }

    /**
     * Remove the specific user from the specific event.
     *
     * @param userID  The user ID of the user that wishes to be removed from
     *                the event.
     * @param eventID The event ID that the user wishes to leave.
     */
    public void removeAttendee(String userID, String eventID) {
        int checkResult = checkAvailability(eventID, "-1",
                "-1", "-1");
        if (checkResult == 5) {
            // Retrieve the event entity via ID
            Event oldEvent = eventList.get(eventID);
            if (oldEvent.hasAttendee(userID)) {
                oldEvent.removeAttendee(userID);
            }
        }
    }

    /**
     * Creates a new room for the event schedule.
     * Assumes there is already a pre-existing event loaded from gateway.
     * @param capacity The capacity of the new room
     * @return String the RoomID of the new room
     */
    public String addRoom(int capacity) {
        this.roomCount++;
        String roomStr = Integer.toString(roomCount);
        Room temp = new Room(roomStr, capacity);
        eventSchedule.put(roomStr, temp);
        return roomStr;
    }

    /**
     * Check if an inputted event is full
     *
     * @param eventID The event ID of the event to be checked
     * @return boolean indicating if event is full
     */
    public boolean isFull(String eventID) {
        int checkResult = checkAvailability(eventID, "-1",
                "-1", "-1");
        if (checkResult == 5) {
            // Retrieve the event entity via ID
            Event oldEvent = eventList.get(eventID);
            return (oldEvent.isFull());
        } else {
            return false;
        }
    }

}
