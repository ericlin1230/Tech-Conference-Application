package user;
import interfaces.IManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;
/**
 * @author Haoying Shen
 * @version 1.0
 */
public class UserManager implements Serializable, IManager
{
    private HashMap<String, User> userMap;
    private HashMap<String, Organizer> organizerMap;
    private HashMap<String, Attendee> attendeeMap;
    private HashMap<String, VIP> VIPMap;

    /**
     * Constructor for the UserManager class, initializes an UserManager.
     *
     * @param UserHash      A hashmap of Users from Gateway
     * @param OrganizerHash      A hashmap of Organizers from Gateway
     * @param AttendeeHash      A hashmap of Attendees from Gateway
     * @param VIPHash      A hashmap of VIPs from Gateway
     */
    public UserManager(HashMap<String,User> UserHash,
                       HashMap<String, Organizer> OrganizerHash,
                       HashMap<String, Attendee> AttendeeHash,
                       HashMap<String, VIP> VIPHash)
    {
        userMap = UserHash;
        organizerMap = OrganizerHash;
        attendeeMap = AttendeeHash;
        VIPMap = VIPHash;
    }

    /**
     * Creates an Attendee entity
     * This assumes unique username
     *
     * @param name       The desired display name of the Attendee
     * @param username   The desired username of the Attendee
     * @param password   The desired password of the Attendee
     */
    public void newAttendee(String name, String username, String password) {
        Attendee newA = new Attendee(name, username, password);
        userMap.put(username, newA);
        attendeeMap.put(username, newA);
    }

    /**
     * Creates a VIP entity
     * This assumes unique username
     *
     * @param name       The desired display name of the VIP
     * @param username   The desired username of the VIP
     * @param password   The desired password of the VIP
     */
    public void newVIP(String name, String username, String password) {
        VIP newV = new VIP(name, username, password);
        userMap.put(username, newV);
        VIPMap.put(username, newV);
    }


    /**
     * Creates an Organizer entity
     * This assumes unique username
     *
     * @param name       The desired display name of the Organizer
     * @param username   The desired username of the Organizer
     * @param password   The desired password of the Organizer
     */
    public void newOrganizer(String name, String username, String password) {
        Organizer newO = new Organizer(name, username, password);
        userMap.put(username, newO);
        organizerMap.put(username, newO);
    }

    /**
     * Gets the hashmap of all Users
     *
     * @return A Hashmap of username keys tied to User values.
     */
    public HashMap<String,User> getUserMap() {
        return userMap;
    }

    /**
     * Gets the hashmap of all Attendees
     *
     * @return AttendeeMap A Hashmap of username keys tied to Attendee values.
     */
    public HashMap<String, Attendee> getAttendeeMap(){
        return attendeeMap;
    }


    /**
     * Gets the hashmap of all Organizers
     *
     * @return OrganizerMap A Hashmap of username keys tied to Organizer values
     */
    public HashMap<String, Organizer> getOrganizerMap() {
        return organizerMap;
    }

    /**
     * Gets the hashmap of all VIPs
     *
     * @return VIPMap A Hashmap of username keys tied to Vip values
     */
    public HashMap<String, VIP> getVIPMap() {
        return VIPMap;
    }

    /**
     * Gets a password for the associated input username
     *
     * @param username  The username of the desired user entity
     * @return password The User's password
     */
    public String getUserPassword(String username){
        return userMap.get(username).getPassword();
    }

    /**
     * Gets the display name for the associated input username
     *
     * @param username  The username of the desired user entity
     * @return name     The User's display name
     */
    public String getUserDisplayName(String username) {
        return userMap.get(username).getName();
    }

    /**
     * Gets the list of contacts for the associated input username
     *
     * @param username  The username of the desired user entity
     * @return contacts The User's list of contacts
     */
    public ArrayList<String> getUserContactList(String username){
        return userMap.get(username).getContacts();
    }

    /**
     * Add 2 users to each other's contact list
     * Must not already be contacts
     *
     * @param username1  The username of the desired user entity
     * @param username2  The username of the desired user entity
     */
    public void addUserContacts(String username1, String username2){
        if (userMap.get(username1).getContacts().contains(username2)){
            System.out.println("User is already in contact.");
        }
        else {
            userMap.get(username1).addContact(username2);
            userMap.get(username2).addContact(username1);
        }
    }

    /**
     * Remove 2 users from each other's contact list
     * Must already be contacts
     *
     * @param username1  The username of the desired user entity
     * @param username2  The username of the desired user entity
     */
    public void removeUserContacts(String username1, String username2){
        if (userMap.get(username1).getContacts().contains(username2)){
            userMap.get(username1).removeContact(username2);
            userMap.get(username2).removeContact(username1);
        }
        else{
            System.out.println("The user you are trying to remove does not exist in your contacts");
        }

    }

    /**
     * Adds an event to the attendance schedule for any User
     * This must not already exist
     *
     * @param username  The username of the desired user entity
     * @param time      The time of the event
     * @param eventID   The ID of the event
     */
    public void addUserEvent(String username, String time, String eventID){
        userMap.get(username).addEvent(time, eventID);
    }

    /**
     * Removes an event from the attendance schedule for any User
     * this event must exist in the schedule
     *
     * @param username  The username of the desired user entity
     * @param time      The time of the event
     */
    public void removeUserEvent(String username, String time){
        userMap.get(username).removeEvent(time);
    }


    /**
     * Returns the attending schedule for any User entity
     *
     * @param username  The username of the desired User entity
     * @return schedule The hashmap of events to attend
     */
    public HashMap<String,String> getAttendingSchedule(String username) {
        return userMap.get(username).getEntireSchedule();
    }

    /**
     * Returns a boolean for any Attendee or Organizer entity
     * Will return true if the User is available at the given time
     * Will return false if the User is not available at the given time
     *
     * @param username   The username of the desired User entity
     * @param time       The time key for schedules
     * @return boolean   If the user is, or is not available
     */
    public boolean checkIfUserAvail(String username, String time){
        return !userMap.get(username).getEntireSchedule().containsKey(time);
    }


    /**
     * Returns a string for the type of the User
     * This assumes that the user exists
     *
     * @param username   The username of a User
     * @return String    String for the type of User
     */
    public String checkType(String username)
    {
        if(userMap.get(username) instanceof Attendee){
            return "Attendee";
        }
        else if(userMap.get(username) instanceof Organizer)
        {
            return "Organizer";
        }
        else if(userMap.get(username) instanceof VIP)
        {
            return "VIP";
        }
        else
            return "Speaker";
    }
}
