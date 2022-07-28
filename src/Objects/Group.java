package Objects;

import java.util.HashSet;

public class Group {
    private SaveHandle groupID;
    private User owner;

    private final HashSet<User> participants = new HashSet<>();

    public SaveHandle getGroupID() {return groupID;}
    public void setGroupID(SaveHandle groupID) {this.groupID = groupID;}

    public User getOwner() {return owner;}
    public void setOwner(User owner) {this.owner = owner;}

    public HashSet<User> getParticipants() {return participants;}
    public HashSet<GroupMessage> getMessages() {return messages;}

    private final HashSet<GroupMessage> messages = new HashSet<>();
}
