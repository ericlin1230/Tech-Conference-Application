package user;
import java.io.Serializable;
import java.util.HashMap;
/**
 * @author Haoying Shen
 * @version 1.0
 */
public class Speaker extends User implements Serializable
{
    private HashMap<String,String> eventsToHost;
    /**
     * Constructor for the Speaker class, initializes an Speaker entity, subclass of User.
     *
     * @param name        The name provided by the UserManager.
     * @param username    The username provided by the UserManager.
     * @param password    The password provided by the UserManager.
     */

    public Speaker(String name, String username, String password) {
        super(name, username, password);
        this.eventsToHost= new HashMap<String, String>();
    }


    /**
     * Adds an event that this speaker will speak at
     *
     * @param time        The string representing the time of the event
     * @param eventID     The id representing the event
     */
    public void addEventHost(String time, String eventID) {
        eventsToHost.put(time, eventID);
    }

    /**
     * Removes an event that this speaker will speak at
     *
     * @param time        The string representing the time of the event
     */
    public void removeEventHost(String time){
        eventsToHost.remove(time);
    }

    /**
     * Returns the hashmap of all events that this speaker will present at
     *
     * @return eventsToHost   The Hashmap representing all events to speak at
     */
    public HashMap<String,String> getEventsToHost(){
        return eventsToHost;
    }
}
