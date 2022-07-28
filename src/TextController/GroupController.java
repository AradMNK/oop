package TextController;

import Builder.DirectMessengerBuilder;
import Builder.GroupBuilder;
import Login.LoginState;
import Login.Loginner;
import Objects.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class GroupController {
    final static int replyShowNum = 10, notReplyID = 0;
    final static String inReplyTo = "In reply to: ", ellipsis = "...";
    public static Group group;
    public static boolean uBlocked, uBlocker;

    public static void attemptEntrance(Group g) {
        group = g;

        showPreviousChats();

        enterChatMode();
    }

    private static void showPreviousChats() {
        for (GroupMessage message : group.getShownMessages()) {
            if (message.getReplyToID().getHandle() != 0) { //it's a replied message
                String repliedGroupMessage = Database.Loader.getGroupMessageContent(message.getReplyToID().getHandle());
                TextController.print("[" + getInReplyTo(repliedGroupMessage) + "] ");
                TextController.println(getMessage(message));
            }
            else if (message.getOriginalUsername().equals(message.getUsername())){ //it's normal message
                TextController.println(getMessage(message));
            } else { //it's a forwarded message
                TextController.print("[Forwarded from @" + message.getOriginalUsername() + "] ");
                TextController.println(getMessage(message));
            }
        }
    }

    private static String getMessage(Message message) {
        return ("(" + message.getDate() + ") " + message.getUserName() + " :" + message.getContent());
    }

    private static void enterChatMode() {
        String line;
        TextController.println("You can leave with +" + GroupCommand.LEAVE + ".");
        while (true){
            line = TextController.getLine();
            if (actOnCommand(line)) break;
            else {
                Database.Saver.addToMessages(group.getDirectID().getHandle(),
                        group.getUser().getUsername(), group.getUser().getUsername(), LocalDateTime.now(), line, notReplyID);
            }
        }
    }

    private static boolean actOnCommand(String line) {
        String[] split = line.split("\\s*");
        GroupCommand groupCommand = GroupCommand.toGroupCommand(split[0]);
        try {
            switch (groupCommand) {
                case NONE -> TextController.println("SYSTEM: Why are you typing nothing? What is your problem?");

                case EDIT -> {try {edit(Integer.parseInt(split[1]));} catch (NumberFormatException e) {e.printStackTrace();}}
                case DELETE -> {try {delete(Integer.parseInt(split[1]));} catch (NumberFormatException e) {e.printStackTrace();}}
                case REPLY -> {try {reply(Integer.parseInt(split[1]));} catch (NumberFormatException e) {e.printStackTrace();}}
                case FORWARD -> {try {forward(Integer.parseInt(split[1]), split[2]);} catch (NumberFormatException e) {e.printStackTrace();}}

                case REFRESH -> refresh();

                case LEAVE -> {return true;}
                default -> {}
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
            if (line.startsWith("\\")) TextController.println("You need to provide an argument for " + line);}
        return false;
    }

    private static void forward(int num, String username) {
        final int size = group.getShownMessages().size();
        if (num > size){
            TextController.println("The number entered exceeds the total messages. (" + size + ")");
            return;
        }
        if (!Database.Loader.usernameExists(username)){
            TextController.println("The username [@" + username + "] does not exist.");
            return;
        }

        if (Database.Loader.isUserBlocked(username, Loginner.loginnedUser.getUsername())){
            TextController.println("You can't forward a message to someone who has blocked you.");
            return;
        } else if (Database.Loader.isUserBlocked(Loginner.loginnedUser.getUsername(), username)){
            TextController.println("You can't forward a message to someone whom you have blocked.");
            return;
        }

        Database.Saver.addToMessages(Database.Loader.getDirectID(Loginner.loginnedUser.getUsername(), username),
                Loginner.loginnedUser.getUsername(), group.getShownMessages().get(num).getOriginalUsername(), LocalDateTime.now(),
                group.getShownMessages().get(num).getContent(),notReplyID);
    }
    private static void reply(int num) {

        final int size = group.getShownMessages().size();
        if (num > size){
            TextController.println("The number entered exceeds the total messages. (" + size + ")");
            return;
        }

        TextController.println("[" + getInReplyTo(num) + "]");
        Database.Saver.addToMessages(group.getDirectID().getHandle(),
                group.getUser().getUsername(), group.getUser().getUsername(), LocalDateTime.now(), TextController.getLine(), notReplyID);
    }
    private static void edit(int num) {
        final int size = group.getShownMessages().size();
        if (num > size){
            TextController.println("The number entered exceeds the total messages. (" + size + ")");
            return;
        }
        if (!group.getShownMessages().get(num).getOriginalUsername().equals(group.getUser().getUsername())){
            TextController.println("You cannot edit another person's message!");
            return;
        }

        Message message = group.getShownMessages().get(num);
        Database.Changer.editMessage(message.getID().getHandle(), TextController.getLine());
        TextController.println("SYSTEM: Successfully edited your message.");
    }
    private static void delete(int num) {
        final int size = group.getShownMessages().size();
        if (num > size){
            TextController.println("The number entered exceeds the total messages. (" + size + ")");
            return;
        }
        if (!group.getShownMessages().get(num).getOriginalUsername().equals(group.getUser().getUsername())){
            TextController.println("You cannot edit another person's message!");
            return;
        }

        Message message = group.getShownMessages().get(num);
        Database.Changer.deleteMessage(message.getID().getHandle());
        TextController.println("SYSTEM: Successfully deleted your message. Reloading chat: ");
        refresh();
    }

    private static void refresh() {
        group = DirectMessengerBuilder.getDirectMessengerFromDatabase(group.getUser(), group.getRecipient());
        showPreviousChats();

        uBlocked = Database.Loader.isUserBlocked(group.getUser().getUsername(), group.getRecipient().getUsername());
        uBlocker = Loginner.loginnedUser.getBlocklist().contains(group.getRecipient().getUsername());
        blockMessage();
    }

    private static String getInReplyTo(int num){
        Message message = group.getShownMessages().get(num);
        String out = (message.getContent().length() > replyShowNum + ellipsis.length()) ?
                message.getContent().substring(0, replyShowNum) + ellipsis : message.getContent();
        return inReplyTo + "[" + out + "]";
    }
    private static String getInReplyTo(String msg){
        String out = (msg.length() > replyShowNum + ellipsis.length()) ?
                msg.substring(0, replyShowNum) + ellipsis : msg;
        return inReplyTo + "[" + out + "]";
    }

    public static void showGroups() {
        if (Loginner.loginState == LoginState.SIGN_OUT){
            TextController.println("Please login before trying to see your group chats.");
            return;
        }
        if (Loginner.loginnedUser.getGroups().size() == 0){
            TextController.println("You have no groups yet. Create a new one using /" + CommandType.NEW_GROUP);
            return;
        }

        ArrayList<Integer> groupIDs = new ArrayList<>();
        for (Group group: Loginner.loginnedUser.getGroups()) {
            TextController.println(group.getName() + " [" + group.getGroupID() + "]");
            groupIDs.add(group.getGroupID().getHandle());
        }

        TextController.println("\nType in the group ID you want to enter in.");

        int parsed;
        try {parsed = Integer.parseInt(TextController.getNext());} catch (NumberFormatException e) {
            TextController.println("Please enter a valid number."); showGroups(); return;}
        if (groupIDs.contains(parsed)) {
            attemptEntrance(GroupBuilder.getGroupFromDatabaseFull(parsed));
        }
    }
}

enum GroupCommand{
    REPLY("\\reply"),
    EDIT("\\edit"),
    REFRESH("\\ref"),
    FORWARD("\\forward"),
    DELETE("\\del"),
    LEAVE("/leave"),

    BLOCK("\\block"),
    UNBLOCK("\\unblock"),

    NONE("");

    private final String name;

    @Override
    public String toString(){return name;}

    GroupCommand(String name){this.name = name;}

    static GroupCommand toGroupCommand(String string){
        for (GroupCommand commandType: GroupCommand.values())
            if (commandType.name.equals(string)) return commandType;
        return GroupCommand.NONE;
    }
}
