package Database;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Saver {
    public static void saveLogin(String username, String hashPass, String name, LocalDate dateJoined, String userType){
        Connector.queryWithoutResult
                ("INSERT INTO users (username, hashPass, name, date, type) VALUES ('"
                        + username +"', '" + hashPass + "', '" + name + "', '" + dateJoined + "', '" + userType + "');");
    }

    public static void setUserName(String username, String value) {
        Connector.queryWithoutResult
                ("UPDATE users SET name = '" + value + "' WHERE username = '" + username + "';");
    }

    public static void setUserBio(String username, String value) {
        Connector.queryWithoutResult
                ("UPDATE users SET bio = '" + value + "' WHERE username = '" + username + "';");
    }

    public static void setUserSubtitle(String username, String value) {
        Connector.queryWithoutResult
                ("UPDATE users SET subtitle = '" + value + "' WHERE username = '" + username + "';");
    }


    public static int addToPosts(String username, String name, LocalDateTime now, String description,
                                 String postType) {
        //declares the postID
        int postID = 0;

        //formats date and time
        DateTimeFormatter formatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = now.format(formatObj);

        //adds the post to the posts
        Connector.queryWithoutResult
                ("INSERT INTO posts (username, date, description, type) VALUES ('"
                        + username +"', '" + formattedDate + "', '" + description + "', '" + postType + "');");

        //gets the handle
        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement
                    ("SELECT postID FROM posts ORDER BY postID DESC;").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                resultSet.next();
                postID = resultSet.getInt(1);
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return postID;
    }

    public static int addToComments(String username, String name, LocalDateTime now, int postID, String msg) {
        //declares the commentID
        int commentID = 0;

        //formats date and time
        DateTimeFormatter formatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = now.format(formatObj);

        //adds the comment to the comments
        Connector.queryWithoutResult
                ("INSERT INTO comments (postID, username, comment, date) VALUES ("
                        + postID + ", '" + username + "', '" + msg + "', '" + formattedDate + "');");

        //gets the handle
        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT commentID FROM comments ORDER BY commentID DESC;").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                resultSet.next();
                commentID = resultSet.getInt(1);
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return commentID;
    }

    public static void addToFollowers(String usernameFollower, String usernameFollowed) {
        Connector.queryWithoutResult
                ("INSERT INTO follow (follower, followed) VALUES ('" + usernameFollower + "', '" + usernameFollowed + "');");
    }

    public static void addToLikes(int postID, String username) {
        Connector.queryWithoutResult
                ("INSERT INTO likes (postID, username) VALUES (" + postID + ", '" + username + "');");
    }

    public static void updateFeedsFromLike(String username, int ID) {
        Connector.queryWithoutResult
                ("INSERT INTO feed (username, ID, type) VALUES ('" + username + "', " + ID + ", like);");
    }


    public static void updateFeedsFromPost(String username, int ID) {
        Connector.queryWithoutResult
                ("INSERT INTO feed (username, ID, type) VALUES ('" + username + "', " + ID + ", post);");
    }

    public static void updateFeedsFromComment(String username, int ID) {
        Connector.queryWithoutResult
                ("INSERT INTO feed (username, ID, type) VALUES ('" + username + "', " + ID + ", comment);");
    }

    public static void addToMessages(String sender, String receiver, String originalSender,
                                     LocalDateTime now, String line, int replyMsgID) {
        //formats date and time
        DateTimeFormatter formatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = now.format(formatObj);

        Connector.queryWithoutResult("INSERT INTO directmessages (sender, receiver, message, date"
                + ", replyMessageID, originalSender) VALUES '" + sender + "', '"
                + receiver + "', '" + line + "', '" + formattedDate + "', "
                + replyMsgID + ", '" + originalSender + "';");
    }

    public static int newMessageAndID(String sender, String receiver, LocalDateTime now, String line, int replyMsgID) {
        //FIXME
        return 0; //the new ID for all direct messages between the two
    }

    public static void addToBlocklist(String blocker, String blocked) {
        Connector.queryWithoutResult("INSERT INTO block (blocker, blocked) VALUES '"
                                            + blocker + "', '" + blocked + "';");
    }

    public static int createGroup(String ownerUsername, String name, String joiner) {
        //declares the groupID
        int groupID = 0;
        Connector.queryWithoutResult("INSERT INTO groups (name, admin, joinID) VALUES '"
                                            + name + "', '" + ownerUsername + "', '" + joiner + "';");

        //gets the handle
        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT groupID FROM groups ORDER BY groupID DESC;").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                resultSet.next();
                groupID = resultSet.getInt(1);
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return groupID;
    }

    public static void addToGroupMessages(int handle, String sender, String originalSender,
                                          LocalDateTime now, String content, int notReplyID) {
        //formats date and time
        DateTimeFormatter formatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = now.format(formatObj);

        Connector.queryWithoutResult("INSERT INTO groupmessages (groupID, sender, message, date"
                + ", replyMessageID, originalSender) VALUES '" + handle + "', '"
                + sender + "', '" + content + "', '" + formattedDate + "', "
                + notReplyID + ", '" + originalSender + "';");
    }
}
