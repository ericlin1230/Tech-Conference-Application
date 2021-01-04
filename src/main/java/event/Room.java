package event;


import java.io.Serializable;
import java.util.HashMap;

/**
 * The Room class contains features of the Room entity
 *
 * @author Zihao Sheng
 * @version 1.1
 */
public class Room implements Serializable {
    private String roomID;
    private int roomCapacity;
    private HashMap<Integer, String> timeSlot;


    /**
     * Constructor for room class
     *
     * @param roomID       String roomID for the room.
     * @param roomCapacity the initial capacity of the room.
     */
    public Room(String roomID, int roomCapacity) {
        this.roomID = roomID;
        this.roomCapacity = roomCapacity;
        this.timeSlot = new HashMap<>();
        clearTimeSlot();
    }

    /**
     * Getter for the ID of the room.
     *
     * @return String roomID.
     */
    public String getRoomID() {
        return roomID;
    }

    /**
     * Getter for the capacity of the room.
     *
     * @return int roomCapacity.
     */
    public int getRoomCapacity() {
        return roomCapacity;
    }

    /**
     * Getter for the timeslot of the room.
     *
     * @return int roomCapacity.
     */
    public HashMap<Integer, String> getTimeSlot() {
        return timeSlot;
    }

    /**
     * Setter for the ID of the room.
     * @param roomID The room ID
     */
    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }



    /**
     * Setter for the event in a certain time slot.
     * Just a simple method, shouldn't be used directly.
     * Please use the addEvent and removeEvent method.
     *
     * @param time    The time to set the event.
     * @param eventID The ID of the event.
     * @return A boolean which determines the event is set of not.
     */
    public boolean setEvent(int time, String eventID) {
        if (timeSlot.containsKey(time)) {
            timeSlot.put(time, eventID);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Add a event to the timeslot. Will return false if the end time is larger than 17 or the time slot is already
     * occupied.
     *
     * @param time        The beginning time of the event.
     * @param eventID     The ID of the event.
     * @param lastingTime The lasting time of the event.
     * @return A boolean which determines whether the event is added of not.
     */
    public boolean addEvent(int time, String eventID, int lastingTime) {
        if ((time + lastingTime - 1) > 17) {
            return false;
        } else {
            for (int i = 0; i < lastingTime; i++) {
                if (!timeSlot.get(i + time).equals("0")) {
                    return false;
                }
            }
            for (int i = 0; i < lastingTime; i++) {
                setEvent(i + time, eventID);
            }
            return true;
        }
    }

    /**
     * Remove an event from the whole time slot.
     *
     * @param eventID The ID of the event to remove
     * @return return true if the event is removed, return false if there is no such event in the time slot.
     */
    public boolean removeEvent(String eventID) {
        boolean s = false;
        for (int i = 9; i <= 17; i++) {
            if (timeSlot.get(i).equals(eventID)) {
                setEvent(i, "0");
                s = true;
            }
        }
        return s;
    }


    /**
     * Clear's timeslot by mapping it to 0
     */
    public void clearTimeSlot() {
        for (int i = 9; i <= 17; i++) {
            timeSlot.put(i, "0");
        }
    }



    /**
     * Sees if a certain timeslot is available
     *
     * @param time: what time it might be available at
     * @param lastingTime: how long the timeslot is
     *
     * @return true if and only if that time slot is available
     */
    public boolean checkAvailability(int time, int lastingTime) {
        for (int i = 0; i < lastingTime; i++) {
            if (!timeSlot.get(i + time).equals("0")) {
                return false;
            }
        }
        return true;
    }
}

