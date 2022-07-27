package Objects;

import java.util.ArrayList;

public class DirectMessenger {
    private SaveHandle directID;
    private User recipient;

    private final ArrayList<DirectMessage> ShownMessages = new ArrayList<>();

    public SaveHandle getDirectID() {return directID;}
    public void setDirectID(SaveHandle directID) {this.directID = directID;}

    public User getRecipient() {return recipient;}
    public void setRecipient(User recipient) {this.recipient = recipient;}



}
