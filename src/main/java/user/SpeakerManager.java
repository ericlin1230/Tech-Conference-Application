package user;
import interfaces.IManager;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Haoying Shen
 * @version 1.0
 */
public class SpeakerManager implements Serializable, IManager {
    private HashMap<String, Speaker> speakerMap;
    /**
     * Constructor for the SpeakerManager class, initializes a SpeakerManager.
     *
     * @param SpeakerHash   A hashmap of Speakers from Gateway
     */
    public SpeakerManager(HashMap<String,Speaker> SpeakerHash)
    {
        speakerMap = SpeakerHash;
    }
    /**
     * Gets the hashmap of all Speakers
     *
     * @return speakerMap A Hashmap of username keys tied to Speaker values.
     */
    public HashMap<String, Speaker> getSpeakerMap(){
        return speakerMap;
    }

    /**
     * Creates an Speaker entity
     * This assumes unique username
     *
     * @param userManager       The UserManager to update
     * @param name              The desired display name of the Speaker
     * @param username          The desired username of the Speaker
     * @param password          The desired password of the Speaker
     */
    public void newSpeaker(UserManager userManager,
                               String name,
                               String username,
                               String password)
    {
        Speaker newS = new Speaker(name, username, password);
        speakerMap.put(newS.getUsername(), newS);
        userManager.getUserMap().put(username, newS);
    }

    /**
     * Returns a boolean for any Speaker entity
     * Will return true if the User is available at the given time
     * Will return false if the User is not available at the given time
     * Attendees and Organizers should use checkIfUserAvail instead
     *
     * @param username   The username of the desired User entity
     * @param time       The time key for schedules
     * @return boolean   If the user is, or is not available
     */
    public boolean checkIfSpeakerAvail(String username, String time)
    {
        if (speakerMap.get(username).getEntireSchedule().containsKey(time)) {
            return false;
        }
        if (speakerMap.get(username).getEventsToHost().containsKey(time)){
            return false;
        }
        else
            return true;
    }

    /**
     * Adds an event to the Speaking schedule
     * This event must not already exist, and must be available at the time
     *
     * @param username  The username of the desired Speaker entity
     * @param time      The time of the event
     * @param eventID   The ID of the event
     */
    public void addSpeakerEvent(String username, String time, String eventID)
    {
        speakerMap.get(username).addEventHost(time, eventID);
    }

    /**
     * Removes an event to the Speaking schedule
     * This event to remove must exist in the speaking schedule
     *
     * @param username  The username of the desired Speaker entity
     * @param time      The time of the event
     */
    public void removeSpeakerEvent(String username, String time)
    {
        speakerMap.get(username).removeEventHost(time);
    }

    /**
     * Returns the speaking schedule for any Speaker entity
     *
     * @param username       The username of the desired Speaker entity
     * @return eventsToHost  The hashmap of events to speak at
     */
    public HashMap<String,String> getSpeakingSchedule(String username){
        return speakerMap.get(username).getEventsToHost();
    }
}
