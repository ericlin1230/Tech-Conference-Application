package user;
import java.io.Serializable;
/**
 * @author Haoying Shen
 * @version 1.0
 */
public class Attendee extends User implements Serializable {
    /**
     * Constructor for the Attendee class, initializes an Attendee entity, subclass of User.
     *
     * @param name        The name provided by the UserManager.
     * @param username    The username provided by the UserManager.
     * @param password    The password provided by the UserManager.
     */
    public Attendee(String name, String username, String password) {
        super(name, username, password);
    }
}
