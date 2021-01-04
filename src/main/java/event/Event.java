package event;


import java.io.Serializable;
import java.util.ArrayList;


/**
 * @author Eric Lin
 * @version 1.0
 */
public class Event implements Serializable {

    private ArrayList<String> attendeeList;
    private ArrayList<String> speakerList;
    private String startTime;
    private String endTime;
    private String eventID;
    private String roomID;
    private int capacity;
    private String eventName;
    private int type;

    /**
     * Constructor of the Event class, initializes the Event entity.
     *
     * @param eventID   The Event ID provided by the EventManager.
     * @param roomID    The Room ID provided by the EventManager.
     * @param startTime The Start Time of the Event.
     * @param endTime   The End Time of the Event.
     * @param eventName The event name of the Event
     * @param capacity  The capacity of the event
     */
    public Event(String eventID, String roomID, String startTime, String endTime,
                 String eventName, Integer capacity) {
        this.eventID = eventID;
        this.roomID = roomID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.attendeeList = new ArrayList<>();
        this.speakerList = new ArrayList<>();
        this.capacity = capacity;
        this.eventName = eventName;
        this.type = 1; // normal event
    }

    /**
     * Sets the start time of the Event.
     * The method can also be used to modify the start time of the Event.
     *
     * @param startTime start time of the Event, accepted as integer in the
     *                  format of YearMonthDateHour (i.e. 2020110512).
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * Sets the end time of the Event.
     * The method can also be used to modify the end time of the Event.
     *
     * @param endTime end time of the Event, accepted as integer in the
     *                format of YearMonthDateHour (i.e. 2020110512).
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * Returns the start time of the Event in the startTime format.
     *
     * @return the start time of the event.
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Sets the event type of an event
     *
     * @param type The event type, 1 for normal 1 speaker event, 2 for no limit
     *             on speaker count, 3 for vip
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * Get the type of the event
     *
     * @return the type of event in int
     */
    public Integer getType() {
        return this.type;
    }

    /**
     * Returns the end time of the Event in the endTime format.
     *
     * @return the end time of the Event.
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Returns the Event ID.
     *
     * @return Event ID.
     */
    public String getEventID() {
        return eventID;
    }

    /**
     * Returns the RoomID of the Event.
     *
     * @return Room ID of the Event.
     */
    public String getRoomID() {
        return roomID;
    }

    /**
     * Sets the roomID of the event
     *
     * @param roomID the new room ID the event takes place in.
     */
    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    /**
     * Adds a Speaker to the speakerList of the Event.
     *
     * @param speakerID The Speaker userID.
     */
    public void addSpeaker(String speakerID) {
        speakerList.add(speakerID);
    }

    /**
     * Adds an Attendee to the attendeeList of the Event.
     *
     * @param userID The Attendee userID.
     */
    public void addAttendee(String userID) {
        attendeeList.add(userID);
    }

    /**
     * Return the current availability of the Event. Returns true if the Event
     * is at full capacity, returns false if the Event is not at full capacity.
     *
     * @return A boolean to indicate if the Event is full.
     */
    public boolean isFull() {
        return attendeeList.size() == capacity;
    }

    /**
     * Returns the list of attendees (IDs).
     *
     * @return List of attendees' ID
     */
    public ArrayList<String> getAttendees() {
        return attendeeList;
    }

    /**
     * Returns the list of speakers (IDs).
     *
     * @return List of speakers' ID
     */
    public ArrayList<String> getSpeakers() {
        return speakerList;
    }

    /**
     * Checks if the user is in the attendee list.
     *
     * @param userID user ID provided to perform the check.
     * @return boolean to represent if they user is in attendee list.
     */
    public boolean hasAttendee(String userID) {
        return (attendeeList.contains(userID));
    }

    /**
     * Checks if the user is in the speaker list.
     *
     * @param userID user ID provided to perform the check.
     * @return boolean to represent if they user is in speaker list.
     */
    public boolean hasSpeaker(String userID) {
        return (speakerList.contains(userID));
    }

    /**
     * Removes the user from the attendee list.
     *
     * @param userID user ID provided to perform the removal
     */
    public void removeAttendee(String userID) {
        attendeeList.remove(userID);
    }


    /**
     * Sets the capacity of the event.
     *
     * @param capacity The new capacity of the event
     */
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the event name.
     *
     * @return String that contains the event name.
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Gets the amount of the speaker in the event.
     *
     * @return Integer that contains the speaker count.
     */
    public int getSpeakerCount() {
        return speakerList.size();
    }
}
