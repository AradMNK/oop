package Database;

public class Changer {
    public static void removePostFromFeed(String username, int postID) {
        Connector.queryWithoutResult("DELETE FROM feed WHERE username = '" + username + "' AND postID = " + postID + " AND type = post;");
    }

    public static void removeCommentFromFeed(String username, int commentID) {
        Connector.queryWithoutResult("DELETE FROM feed WHERE username = '" + username + "' AND postID = " + commentID + " AND type = cpmment;");
    }

    public static void removeLikeFromFeed(String username, int postID) {
        //FIXME
        Connector.queryWithoutResult("DELETE FROM feed WHERE username = '" + username + "' AND postID = " + postID + " AND type = like;");
    }

    public static void addViewForUser(String username) {
        //FIXME for business users.
        // Create a new  table for every single business user and name it something and store views in it
        // if possible, make the database remove the contents every day. idk if it can. and if it couldn't, just forget it
    }
    public static void addLikeStat(int postID, String username) {
        //FIXME same here
    }

    public static void editMessage(int messageID, String line) {

    }

    public static void deleteMessage(int handle) {

    }

    public static void removeFromBlockList(String blocker, String blocked) {

    }

    public static void removeFromFollowers(String follower, String followed) {

    }

    public static void removeLike(int postID, String username) {

    }

    public static void removeGroup(int groupID) {

    }

    public static void removeFromGroups (String username, int groupID){

    }
}
