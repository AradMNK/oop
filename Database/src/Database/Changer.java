package Database;

public class Changer {
    public static void removePostFromFeed(String username, int postID) {
        Connector.queryWithoutResult("DELETE FROM feed WHERE username = '" + username + "' AND postID = " + postID + " AND type = post;");
    }

    public static void removeCommentFromFeed(String username, int commentID) {
        Connector.queryWithoutResult("DELETE FROM feed WHERE username = '" + username + "' AND postID = " + commentID + " AND type = cpmment;");
    }

    public static void removeLikeFromFeed(String username, int handle) {
        Connector.queryWithoutResult("DELETE FROM feed WHERE username = '" + username + "' AND postID = " + handle + " AND type = like;");
    }

    public static void addViewForUser(int postID, String username) {
        //FIXME if possible, make the database remove the contents every day. idk if it can. and if it couldn't, just forget it
        Connector.queryWithoutResult("INSERT INTO views (username, postID) VALUES ('" + username + "', " + postID + ");");
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
}
