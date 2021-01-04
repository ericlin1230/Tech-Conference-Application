package user;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;

/**
 * @author Haoying Shen
 * @version 1.0
 */
public class User implements Serializable
{
    private String username;
    private String password;
    private ArrayList<String> contacts;
    private String name;
    private HashMap<String,String> schedule;

    /**
     * Constructor for the User class, initializes an User entity.
     * This should never be called on its own.
     *
     * @param name        The name provided by the UserManager.
     * @param username    The username provided by the UserManager.
     * @param password    The password provided by the UserManager.
     */
    public User(String name, String username, String password) {
        this.setName(name);
        this.setUsername(username);
        this.setPassword(password);
        contacts = new ArrayList<>();
        contacts.add(username);
        schedule = new HashMap<>();
    }

    /**
     * Returns the name of this User entity.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the username of this User entity.
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password of this User entity.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns an Hashmap with the Events this User entity will attend.
     *
     * @return eventIDList
     */
    public HashMap<String,String> getEntireSchedule() {
        return schedule;
    }

    /**
     * Returns an Arraylist with the contacts of this User entity.
     *
     * @return contacts
     */
    public ArrayList<String> getContacts() {
        return contacts;
    }

    /**
     * Sets the name of this User entity
     * Can be used to modify existing username
     *
     * @param username  The desired username
     */
    public void setName(String username) {
        this.username = username;
    }

    /**
     * Sets the username of this User entity
     * Can be used to modify existing name
     *
     * @param username  The desired username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the password of this User entity
     * Can be used to modify existing name
     *
     * @param password  The desired password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the Contacts of this User entity
     * Can be used to modify existing list of contacts
     *
     * @param cont  The desired list of contacts to replace with
     */
    public void setContacts(ArrayList<String> cont){
        contacts = cont;
    }

    /**
     * Sets the hashmap for events of this User entity
     * Can be used to modify existing events
     *
     * @param sch  The desired hashmap of events to replace with
     */
    public void setSchedule(HashMap<String,String> sch){
        schedule = sch;
    }

    /**
     * Adds a new contact to the Arraylist of existing contacts
     *
     * @param username  The username of the user to add to the list of contacts
     */
    public void addContact(String username) {
        if (contacts.contains(username)){
            System.out.println("Contact already exists");
        }
        else
            contacts.add(username);
    }
    /**
     * Adds a new event to the Arraylist of existing events to be attended
     * @param time The time of the event
     * @param eventID  The eventID to add to the list of events to be attended
     */
    public void addEvent(String time, String eventID) {
        schedule.put(time, eventID);
    }

    /**
     * Removes a contact to the Arraylist of existing contacts
     *
     * @param username  The name of the user to remove to the list of contacts
     */
    public void removeContact(String username) {
        if (contacts.contains(username)){
            contacts.remove(username);
        }
        else
            System.out.println("Contact does not exist");
    }

    /**
     * Removes an event from the Hashmap of existing events to be attended
     *
     * @param time  The time of one of the events in the hashmap
     */
    public void removeEvent(String time){
        schedule.remove(time);
    }
}
