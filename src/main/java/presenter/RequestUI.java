package presenter;

import interfaces.IManager;
import interfaces.IPresenter;
import message.Message;
import message.MessageManager;

import java.util.HashMap;

/**
 * The RequestUI provides the prompts for User request services
 *
 * @author Ryan Wang
 * @version 1.0
 */
public class RequestUI implements IPresenter {
    private MessageManager messageManager;

    /**
     * Constructor for RequestUI, initializes a RequestUI object
     *
     * @param managers HashMap of String keys that map to IManager objects (Managers)
     */
    public RequestUI(HashMap<String, IManager> managers){
        this.messageManager = (MessageManager) managers.get("messageManager");
    }


    /**
     * Prints prompts for open requests and accepted requests
     */
    public void viewRequests(){
        System.out.println("Currently Active Requests: (Message ID in [])");
        if(messageManager.getActiveRequests().isEmpty()){
            System.out.println("There are no active requests.");
        }
        else{
            for(Integer messageID : messageManager.getActiveRequests()){
                System.out.println("[" + messageID + "]" + messageManager.getMessage(messageID));
            }
        }
        System.out.println("Accepted Requests:");
        if(messageManager.getAcceptedRequests().isEmpty()){
            System.out.println("There are no accepted requests.");
        }
        else{
            for(Message message : messageManager.getAcceptedRequests()){
                System.out.println(message);
            }
        }
    }

    /**
     * Prints current User requests
     *
     * @param username The user name of the user that is checking the request
     */
    public void viewUserRequests(String username){
        System.out.println("Current pending requests:");
        if(messageManager.getUserPendingRequests(username).isEmpty()){
            System.out.println("There are no active requests.");
        }
        else{
            for(Integer messageID : messageManager.getUserPendingRequests(username)){
                System.out.println(messageManager.getMessage(messageID));
            }
        }
        System.out.println("Accepted requests:");
        if(messageManager.getUserAcceptedRequests(username).isEmpty()){
            System.out.println("There are no accepted requests.");
        }
        else{
            for(Message request : messageManager.getUserAcceptedRequests(username)){
                System.out.println(request);
            }
        }
    }

    /**
     * Prints prompt for asking request ID
     */
    public void askForID(){
        System.out.println("What request ID would you like to respond to? (Please enter an integer)");
    }


    /**
     * Prints prompt to ask for a request to send
     */
    public void requestMessage(){
        System.out.println("What request would you like to make?");
    }

    /**
     * Prints prompt to ask for specific requests/accomodations
     */
    public void requestPrompt(){
        System.out.println("Do you have specific requests or accommodations?");
        System.out.println("1) Yes 2) No");
    }
}
