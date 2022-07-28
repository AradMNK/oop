package TextController;

import Builder.DirectMessengerBuilder;
import Builder.UserBuilder;
import Login.LoginState;
import Login.Loginner;
import Objects.DirectMessage;
import Objects.DirectMessenger;

import java.time.LocalDate;

public class DmController {
    final static int replyShowNum = 10;
    final static String inReplyTo = "in reply to: ", ellipsis = "...";
    public static DirectMessenger dm;

    public static void attemptEntrance(String username) {
        if (Loginner.loginState == LoginState.SIGN_OUT){
            TextController.println("Please login before trying to message anyone.");
            return;
        }
        if (!Database.Loader.usernameExists(username)){
           TextController.println("Could not find match for username [@" + username + "]!");
           return;
        }

        dm = (Database.Loader.usersHaveDm(Loginner.loginnedUser.getUsername(), username)) ?
            DirectMessengerBuilder.getDirectMessengerFromDatabase(Loginner.loginnedUser, username)
        : new DirectMessenger(Loginner.loginnedUser, UserBuilder.getUserFromDatabase(username));
        //if users have dm load else create

        enterChatMode();

        dm = null;
    }

    private static void enterChatMode() {
        String line;
        TextController.println("You can leave with +" + DmCommand.LEAVE + ".");
        while (true){
            line = TextController.getLine();
            if (actOnCommand(line)) break;
            else {
                Database.Saver.addToMessages(dm.getDirectID().getHandle(),
                        dm.getUser().getUsername(), dm.getUser().getUsername(), LocalDate.now(), line);
            }
        }
    }

    private static boolean actOnCommand(String line) {
        String[] split = line.split("\\s*");
        DmCommand dmCommand = DmCommand.toDmCommand(split[0]);
        try {
            switch (dmCommand) {
                case NONE -> TextController.println("SYSTEM: Why are you typing nothing? What is your problem?");

                case EDIT -> {try {edit(Integer.parseInt(split[1]));} catch (NumberFormatException e) {e.printStackTrace();}}
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
        final int size = dm.getShownMessages().size();
        if (num > size){
            TextController.println("The number entered exceeds the total messages. (" + size + ")");
            return;
        }
        if (!Database.Loader.usernameExists(username)){
            TextController.println("The username [@" + username + "] does not exist.");
            return;
        }

        Database.Saver.addToMessages(Database.Loader.getDirectID(Loginner.loginnedUser.getUsername(), username),
                Loginner.loginnedUser.getUsername(), dm.getShownMessages().get(num).getOriginalUsername(), LocalDate.now(),
                dm.getShownMessages().get(num).getContent());
        //FIXME
    }

    private static void refresh() {dm = DirectMessengerBuilder.getDirectMessengerFromDatabase(dm.getUser(), dm.getRecipient());}

    private static void reply(int num) {
        final int size = dm.getShownMessages().size();
        if (num > size){
            TextController.println("The number entered exceeds the total messages. (" + size + ")");
            return;
        }


        //FIXME
    }

    private static void edit(int num) {
        final int size = dm.getShownMessages().size();
        if (num > size){
            TextController.println("The number entered exceeds the total messages. (" + size + ")");
            return;
        }
        if (!dm.getShownMessages().get(num).getOriginalUsername().equals(dm.getUser().getUsername())){
            TextController.println("You cannot edit another person's message!");
            return;
        }

        DirectMessage directMessage = dm.getShownMessages().get(num);
        Database.Changer.editMessage(directMessage.getID().getHandle(), TextController.getLine());
        TextController.println("Successfully edited your message.");
        //FIXME
    }
}

enum DmCommand{
    REPLY("\\reply"),
    EDIT("\\edit"),
    REFRESH("\\ref"),
    FORWARD("\\forward"),
    LEAVE("/leave"),

    NONE("");

    private final String name;

    @Override
    public String toString(){return name;}

    DmCommand(String name){this.name = name;}

    static DmCommand toDmCommand(String string){
        for (DmCommand commandType: DmCommand.values())
            if (commandType.name.equals(string)) return commandType;
        return DmCommand.NONE;
    }
}
