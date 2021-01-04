package user;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;
/**
 * @author Haoying Shen
 * @version 1.0
 */
public class VIP extends User implements Serializable {
    private ArrayList<String> contacts;
    private HashMap<String,String> schedule;
    /**
     * Constructor for the Attendee class, initializes an Attendee entity, subclass of User.
     *
     * @param name        The name provided by the UserManager.
     * @param username    The username provided by the UserManager.
     * @param password    The password provided by the UserManager.
     */
    public VIP(String name, String username, String password) {
        super(name, username, password);
    }
}
