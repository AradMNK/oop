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


    public static void addToPosts(String username, String name, LocalDate now, String description,
                                  String saveHandle, String postType) {
        //FIXME create a table UNIQUE to every username
        // when creating a new account and name it something related to that and use that table now
    }

    public static void addToComments(String username, String name, LocalDate now, String postID, String msg,
                                     String saveHandle) {
        //FIXME same as above
    }


    public static void updateFeedsFromPost(String username, String saveHandle) {
        //FIXME create a table UNIQUE to every username
        // when creating a new account and name it something related to that and use that table now.
        // You want to find the user X's followings (via username given in the function) and update their feeds.
        //FIXME
        // This means you will have to implement three tables:
        // [username]_followers
        // [username]_followings
        // [username]_feed
        // and I want you to store only the saveHandles and types of the post in THAT (feed) table. The type
        // is determined by the name of the function. Right now it's a "post"
    }

    public static void updateFeedsFromComment(String username, String saveHandle) {
        //FIXME create a table UNIQUE to every username
        // when creating a new account and name it something related to that and use that table now.
        // You want to find the user X's followings (via username given in the function) and update their feeds.
        //FIXME
        // This means you will have to implement three tables:
        // [username]_followers
        // [username]_followings
        // [username]_feed
        // and I want you to store only the saveHandles and types of the post in THAT (feed) table. The type
        // is determined by the name of the function. Right now it's a "post"
    }
}
