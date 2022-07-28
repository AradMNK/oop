package Database;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        //create a table UNIQUE to every username
        //when creating a new account and name it something related to that and use that table now
        //formats date and time
        DateTimeFormatter formatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = now.format(formatObj);

        //adds the post to the posts
        Connector.queryWithoutResult("INSERT INTO posts (username, date, description, type) VALUES ('" + username +"', '" + formattedDate + "', '" + description + "', '" + postType + "');");

        //gets the handle
        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT postID FROM posts ORDER BY postID DESC;").executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return 0; //return the handle
    }

    public static int addToComments(String username, String name, LocalDateTime now, int postID, String msg) {
        //same as above
        //formats date and time
        DateTimeFormatter formatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = now.format(formatObj);

        //adds the comment to the comments
        Connector.queryWithoutResult("INSERT INTO comments (postID, username, comment, date) VALUES (" + postID + ", '" + username + "', '" + msg + "', '" + formattedDate + "');");

        //gets the handle
        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT commentID FROM comments ORDER BY commentID DESC;").executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return 0; //return the handle
    }

    public static void addToFollowers(String usernameFollower, String usernameFollowed) {
        Connector.queryWithoutResult("INSERT INTO follow (follower, followed) VALUES ('" + usernameFollower + "', '" + usernameFollowed + "');");
    }

    public static int addToLikes(int postID, String username) {
        //create a new table for a postID's likes and store usernames in it. THAT'S IT. nothing more nothing less
        //and it has to be upon creation. Here u can change it.
        //adds the like to the likes
        Connector.queryWithoutResult("INSERT INTO likes (postID, username) VALUES (" + postID + ", '" + username + "');");

        //gets the handle
        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT likeID FROM likes ORDER BY likeID DESC;").executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return 0; // the AI handle
    }

    public static void updateFeedsFromLike(String username, int ID) {
        Connector.queryWithoutResult("INSERT INTO feed (username, ID, type) VALUES ('" + username + "', " + ID + ", like);");
    }


    public static void updateFeedsFromPost(String username, int ID) {
        Connector.queryWithoutResult("INSERT INTO feed (username, ID, type) VALUES ('" + username + "', " + ID + ", post);");
    }

    public static void updateFeedsFromComment(String username, int ID) {
        Connector.queryWithoutResult("INSERT INTO feed (username, ID, type) VALUES ('" + username + "', " + ID + ", comment);");
    }

    public static void addToMessages(int directID, String sender, String originalSender, LocalDateTime now, String line, int replyMsgID) {

    }

    public static void addToBlocklist(String blocker, String blocked) {

    }
}
