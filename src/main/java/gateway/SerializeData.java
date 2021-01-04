package gateway;
import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import event.EventManager;
import user.UserManager;
import user.SpeakerManager;
import message.MessageManager;

/**
 * The SerializeData Class serializes and deserializes data to and from .ser files.
 *
 * @author Temilade Adeleye
 * @version 1.0
 */
public class SerializeData implements Serializable{

    /**
     * Constructor of the SerializeData Class.
     */
    public SerializeData() {}

    /**
     * Serializes UserManager object and writes to a file.
     *
     * @param filename the file to write to
     * @param data the UserManager data to be serialized
     */
    public void serializeUser(String filename, UserManager data) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
            out.writeObject(data);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserializes data in a file and stores the data in a UserManager object.
     *
     * @param filename the file to read from
     * @return the deserialized UserManager object from the file
     */
    public UserManager deserializeUser(String filename)  {
        UserManager data = null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
            data = (UserManager) in.readObject();
            in.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Serializes MessageManager object and writes to file.
     *
     * @param filename the file to write to
     * @param data the MessageManager data to be serialized
     */
    public void serializeMessage(String filename, MessageManager data) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
            out.writeObject(data);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserializes data in a file and stores the data in a MessageManager object.
     *
     * @param filename the file to read from
     * @return the deserialized MessageManager object from the file
     */
    public MessageManager deserializeMessage(String filename)  {
        MessageManager data = null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
            data = (MessageManager) in.readObject();
            in.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Serializes event list data and writes to file.
     *
     * @param filename the file to write to
     * @param data the HashMap containing event list data to be serialized
     */
    public void serializeEvent(String filename, EventManager data) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
            out.writeObject(data);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserializes data in a file and stores the event list data in a HashMap.
     *
     * @param filename the file to read from
     * @return the deserialized event list data from the file in a HashMap
     */
    public EventManager deserializeEvent(String filename)  {
        EventManager data = null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
            data = (EventManager) in.readObject();
            in.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }


    /**
     * Serializes Speaker list data and writes to file.
     *
     * @param filename the file to write to
     * @param data the HashMap containing Speaker list data to be serialized
     */
    public void serializeSpeaker(String filename, SpeakerManager data) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
            out.writeObject(data);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserializes data in a file and stores the Speaker list data in a HashMap.
     *
     * @param filename the file to read from
     * @return the deserialized Speaker list data from the file in a HashMap
     */
    public SpeakerManager deserializeSpeaker(String filename)  {
        SpeakerManager data = null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
            data = (SpeakerManager) in.readObject();
            in.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

}
