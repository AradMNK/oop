package Objects;

import java.util.ArrayList;

public class DirectMessenger {
    private SaveHandle directID;
    private User user, recipient;

    public DirectMessenger(User user, User recipient){
        this.user = user;
        this.recipient = recipient;
    }

    public DirectMessenger(){}

    private final ArrayList<Message> ShownMessages = new ArrayList<>();

    public SaveHandle getDirectID() {return directID;}
    public void setDirectID(SaveHandle directID) {this.directID = directID;}
    public void setDirectID(int directID) {this.directID = new SaveHandle(directID);}

    public User getRecipient() {return recipient;}
    public void setRecipient(User recipient) {this.recipient = recipient;}

    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}

    public ArrayList<Message> getShownMessages() {return ShownMessages;}
}
