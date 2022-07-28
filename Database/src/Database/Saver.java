package Database;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Saver {
    public static void saveLogin(String username, String hashPass, String name, LocalDate dateJoined, String userType){
        //save stuff but set every other thing in user label to null
        //remember that userType=="business" users need to have more tables created. You will see in the other functions
        Connector.queryWithoutResult("INSERT INTO users (username, hashPass, name, date, type) VALUES ('" + username +"', '" + hashPass + "', '" + name + "', '" + dateJoined + "', '" + userType + "');");
    }

    public static void setUserName(String username, String value) {
        //FIXME whenever I write set"User"Something it means it is guaranteed that the user exists
        // if you do everything else correctly
        Connector.queryWithoutResult("UPDATE users SET name = '" + value + "' WHERE username = '" + username + "';");
    }

    public static void setUserBio(String username, String value) {
        Connector.queryWithoutResult("UPDATE users SET bio = '" + value + "' WHERE username = '" + username + "';");
    }

    public static void setUserSubtitle(String username, String value) {
        Connector.queryWithoutResult("UPDATE users SET subtitle = '" + value + "' WHERE username = '" + username + "';");
    }


    public static int addToPosts(String username, String name, LocalDateTime now, String description,
                                 String postType) {
        //FIXME create a table UNIQUE to every username
        // when creating a new account and name it something related to that and use that table now
        //adds the post to the posts
        Connector.queryWithoutResult("INSERT INTO posts (username, ");
        return 0; //return the handle
    }

    public static int addToComments(String username, String name, LocalDateTime now, int postID, String msg) {
        //FIXME same as above
        return 0; // return the handle
    }

    public static void addToFollowers(String usernameFollower, String usernameFollowed) {
        //FIXME
    }

    public static int addToLikes(int postID, String username) {
        //FIXME create a new table for a postID's likes and store usernames in it. THAT'S IT. nothing more nothing less
        // and it has to be upon creation. Here u can change it.
        return 0; // the AI handle
    }

    public static void updateFeedsFromLike(String username, int ID) {

    }


    public static void updateFeedsFromPost(String username, int ID) {

    }

    public static void updateFeedsFromComment(String username, int ID) {

    }

    public static void addToMessages(int directID, String sender, String originalSender, LocalDateTime now, String line, int replyMsgID) {

    }

    public static void addToBlocklist(String blocker, String blocked) {

    }
}
