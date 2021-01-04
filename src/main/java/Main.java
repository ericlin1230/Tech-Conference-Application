import controller.LoginController;
import event.EventManager;
import gateway.ResetAll;
import gateway.SerializeData;
import interfaces.IManager;
import interfaces.IPresenter;
import message.MessageManager;
import presenter.ErrorUI;
import presenter.EventUI;
import presenter.LoginUI;
import presenter.MessageUI;
import presenter.RequestUI;
import user.SpeakerManager;
import user.UserManager;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Eric Lin
 * @version 1.0
 */
public class Main {

    /**
     * The main method of the program to run the system
     *
     * @param args args
     */
    public static void main(String[] args) {
        runConference();
    }

    /**
     * The method to run the conference system
     */
    public static void runConference() {


        SerializeData serializer = new SerializeData();


        boolean continueRun = true;
        Scanner reader = new Scanner(System.in);

        while (continueRun) {
            LoginUI lUI = new LoginUI();
            lUI.programStart();
            String choice = reader.nextLine();
            if (choice.equals("0")) {
                continueRun = false;
            } else if (choice.equals("RESET")) {
                ResetAll r = new ResetAll();
                r.reset();
            } else if (choice.equals("1")) {
                EventManager eventManager = serializer.deserializeEvent("src/main/java/gateway/eventManager.ser");
                UserManager userManager = serializer.deserializeUser("src/main/java/gateway/userManager.ser");
                MessageManager messageManager = serializer.deserializeMessage("src/main/java/gateway/messageManager.ser");
                SpeakerManager speakerManager = serializer.deserializeSpeaker("src/main/java/gateway/speakerManager.ser");
                LoginController lController = new LoginController();


                HashMap<String, IManager> imanager = new HashMap<>();
                imanager.put("userManager", userManager);
                imanager.put("speakerManager", speakerManager);
                imanager.put("messageManager", messageManager);
                imanager.put("eventManager", eventManager);

                MessageUI mUI = new MessageUI(imanager);
                ErrorUI eUI = new ErrorUI();
                EventUI eUI2 = new EventUI(eventManager);
                RequestUI rUI = new RequestUI(imanager);
                HashMap<String, IPresenter> ip = new HashMap<>();
                ip.put("messageUI", mUI);
                ip.put("errorUI", eUI);
                ip.put("loginUI", lUI);
                ip.put("eventUI", eUI2);
                ip.put("requestUI", rUI);


                lController.run(imanager, ip);


                userManager = lController.getUserManager();
                messageManager = lController.getMessageManager();
                eventManager = lController.getEventManager();
                speakerManager = lController.getSpeakerManager();
                fileSave(userManager, messageManager, eventManager, speakerManager);
            }
        }
    }

    /**
     * The method to save the managers and event files.
     *
     * @param uManager The usermanager from login controller
     * @param mManager The messagemanager from login controller
     * @param eManager The eventmanager from login controller
     * @param sManager The speakermanager from login controller
     */
    public static void fileSave(UserManager uManager, MessageManager mManager,
                                EventManager eManager, SpeakerManager sManager) {
        SerializeData serializer = new SerializeData();
        serializer.serializeEvent("src/main/java/gateway/eventManager.ser", eManager);
        serializer.serializeUser("src/main/java/gateway/userManager.ser", uManager);
        serializer.serializeMessage("src/main/java/gateway/messageManager.ser", mManager);
        serializer.serializeSpeaker("src/main/java/gateway/speakerManager.ser", sManager);
    }

}
