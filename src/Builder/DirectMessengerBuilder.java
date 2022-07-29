package Builder;

import Objects.DirectMessenger;
import Objects.User;

public class DirectMessengerBuilder {
    public static DirectMessenger getDirectMessengerFromDatabase(User user, String recipientUsername){
        DirectMessenger directMessenger = new DirectMessenger(user, UserBuilder.getUserFromDatabase(recipientUsername));
        fillDirectMessengerWithMessages(directMessenger);
        return directMessenger;
    }

    public static DirectMessenger getDirectMessengerFromDatabase(User user, User recipient){
        DirectMessenger directMessenger = new DirectMessenger(user, recipient);
        fillDirectMessengerWithMessages(directMessenger);
        return directMessenger;
    }

    private static void fillDirectMessengerWithMessages(DirectMessenger dm){
        //FIXME
    }
}
