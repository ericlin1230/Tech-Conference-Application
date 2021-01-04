package user;

import java.io.Serializable;
/**
 * @author Haoying Shen
 * @version 1.0
 */
public class Organizer extends User implements Serializable
{
    /**
     * Constructor for the Organizer class, initializes an Organizer entity, subclass of User.
     *
     * @param name        The name provided by the UserManager.
     * @param username    The username provided by the UserManager.
     * @param password    The password provided by the UserManager.
     */
    public Organizer(String name, String username, String password)
    {
        super(name, username, password);
    }
}
