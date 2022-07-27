package Builder;

import Objects.DirectMessenger;
import Objects.SaveHandle;
import Objects.User;

public class DirectMessengerBuilder {
    public static DirectMessenger getDirectMessengerFromDatabase(User user, String recipientUsername){
        DirectMessenger directMessenger = new DirectMessenger(user, UserBuilder.getUserFromDatabase(recipientUsername));
        directMessenger.setDirectID(/*FIXME*/ new SaveHandle("put id here from database"));
        fillDirectMessengerWithMessages(directMessenger);
        return directMessenger;
    }

    public static DirectMessenger getDirectMessengerFromDatabase(User user, User recipient){
        DirectMessenger directMessenger = new DirectMessenger(user, recipient);
        directMessenger.setDirectID(/*FIXME*/ new SaveHandle("put id here from database"));
        fillDirectMessengerWithMessages(directMessenger);
        return directMessenger;
    }

    private static void fillDirectMessengerWithMessages(DirectMessenger dm){
        //FIXME
    }
}
