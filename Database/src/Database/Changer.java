package Database;

import Objects.SaveHandle;

public class Changer {
    public static void removePostFromFeed(String username, SaveHandle postID) {
        //FIXME
    }

    public static void removeCommentFromFeed(String username, SaveHandle commentID) {
        //FIXME
    }

    public static void addToFollowers(String usernameFollower, String usernameFollowing) {
        //FIXME
    }

    public static void addToLikes(String postID, String username) {
        //FIXME create a new table for a postID's likes and store usernames in it. THAT'S IT. nothing more nothing less
        // and it has to be upon creation. Here u can change it.
    }


    public static void addViewForUser(String username) {
        //FIXME for business users.
        // Create a new  table for every single business user and name it something and store views in it
        // if possible, make the database remove the contents every day. idk if it can. and if it couldn't, just forget it
    }
    public static void addLikeStat(String postID, String username) {
        //FIXME same here
    }
}
