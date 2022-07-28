package Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        // possible if there's a date for views
        Connector.queryWithoutResult("INSERT INTO views (username, postID) VALUES ('" + username + "', " + postID + ");");
    }
    public static void addLikeStat(int postID, String username) {
        Connector.queryWithoutResult("INSERT INTO likestat (username, postID) VALUES ('" + username + "', " + postID + ");");
    }

    public static void editMessage(int messageID, String line) {
        Connector.queryWithoutResult("UPDATE directmessages SET message = '" + line + "' WHERE messageID = " + messageID + ";");
    }

    public static void deleteMessage(int handle) {
        Connector.queryWithoutResult("DELETE FROM directmessages WHERE messageID = " + handle + ";");
    }

    public static void editGroupMessage(int messageID, String line) {
        Connector.queryWithoutResult("UPDATE groupmessages SET message = '" + line + "' WHERE messageID = " + messageID + ";");
    }

    public static void deleteGroupMessage(int handle) {
        Connector.queryWithoutResult("DELETE FROM directmessages WHERE messageID = " + handle + ";");
    }

    public static void removeFromBlockList(String blocker, String blocked) {
        //FIXME
    }

    public static void removeFromFollowers(String follower, String followed) {
        Connector.queryWithoutResult("DELETE FROM follow WHERE follower = '" + follower + "' AND followed = '" + followed + "';");
    }

    public static void removeLike(int postID, String username) {
        Connector.queryWithoutResult("DELETE FROM likes WHERE username = '" + username + "' AND postID = " + postID + ";");
    }

    public static void removeGroup(int groupID) {
        Connector.queryWithoutResult("DELETE FROM groups WHERE groupID = " + groupID + ";");
    }

    public static void removeFromGroup(String username, int groupID) {
        //declares a string for the members
        String members;

        //finds the members
        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT members FROM groups WHERE groupID = " + groupID + ";").executeQuery();
            resultSet.next();
            members = resultSet.getString(1);

            //removes the user
            members.replaceAll(username, "");

            //checks for ,
            if (members.charAt(0) == ','){
                members = members.substring(1);
            }
            if (members.charAt(members.length()-1) == ','){
                members = members.substring(members.length()-1);
            }
            members.replaceAll(",,", ",");
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
    }
}
