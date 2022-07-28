package Database;

import java.time.LocalDate;

public class Saver {
    public static void saveLogin(String username, String hashPass, String name, LocalDate dateJoined, String userType){
        //FIXME save stuff but set every other thing in user label to null
        // remember that userType=="business" users need to have more tables created. You will see in the other functions
    }

    public static void setUserName(String username, String value) {
        //FIXME whenever I write set"User"Something it means it is guaranteed that the user exists
        // if you do everything else correctly
    }

    public static void setUserBio(String username, String value) {

    }

    public static void setUserSubtitle(String username, String value) {

    }


    public static int addToPosts(String username, String name, LocalDate now, String description,
                                  String postType) {
        //FIXME create a table UNIQUE to every username
        // when creating a new account and name it something related to that and use that table now
        return 0; //return the handle
    }

    public static int addToComments(String username, String name, LocalDate now, int postID, String msg) {
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

    public static void addToMessages(int directID, String sender, String originalSender, LocalDate now, String line, int replyMsgID) {

    }
}
