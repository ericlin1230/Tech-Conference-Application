package gateway;


import event.Event;
import event.EventManager;
import event.Room;
import message.Message;
import message.MessageManager;
import user.User;
import user.Speaker;
import user.UserManager;
import user.VIP;
import user.Attendee;
import user.Organizer;
import user.SpeakerManager;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The ResetAll class provides methods to reset the data
 *
 * @author Eric Lin
 * @version 1.0
 */
public class ResetAll {

    /**
     * Constructor of the ResetAll class
     */
    public void ResetAll() {

    }

    /**
     * Resets the current database of the program, erase all the users/messages
     * Reset the schedules and events to default settings
     */
    public void reset() {
        HashMap<String, User> userList = new HashMap<>();
        HashMap<String, Speaker> speakerMap = new HashMap<>();
        HashMap<String, ArrayList<Integer>> messageSenders = new HashMap<>();
        HashMap<String, ArrayList<Integer>> messageRecipients = new HashMap<>();
        HashMap<Integer, Message> messageIDs = new HashMap<>();
        HashMap<String, Attendee> attendeemap = new HashMap<>();
        HashMap<String, Organizer> organizerMap = new HashMap<>();
        HashMap<String, VIP> VIPHash = new HashMap<>();
        UserManager U = new UserManager(userList, organizerMap, attendeemap, VIPHash);
        SpeakerManager S = new SpeakerManager(speakerMap);
        MessageManager M = new MessageManager(messageSenders, messageRecipients, messageIDs);
        HashMap<String, Room> eventSchedule =
                new HashMap<>();
        HashMap<String, Event> eventlist = new HashMap<>();
        Room room1 = new Room("1", 20);
        eventSchedule.put("1", room1);
        EventManager E = new EventManager(eventSchedule, eventlist);

        SerializeData serializer = new SerializeData();
        serializer.serializeUser("src/main/java/gateway/userManager.ser",U);
        serializer.serializeEvent("src/main/java/gateway/eventManager.ser",E);
        serializer.serializeMessage("src/main/java/gateway/messageManager.ser",M);
        serializer.serializeSpeaker("src/main/java/gateway/speakerManager.ser",S);
    }
}
