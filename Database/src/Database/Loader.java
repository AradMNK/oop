package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Loader {
    public static boolean loginMatch(String username, String hashPass){
        //(this is checking if login credentials match)
        //declares username and password found in the results
        String loginUsername;
        String loginPassword;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT username, hashPass FROM users WHERE username = '" + username + "';").executeQuery();
            resultSet.next();
            loginUsername = resultSet.getString(1);
            loginPassword = resultSet.getString(2);

            //checks if the password is the same
            if (username.equals(loginUsername))
                if (hashPass.equals(loginPassword))
                    return true;
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return false;
    }

    public static boolean usernameExists(String username){//this checks if the username exists in the "login" table
        //declares the username found in the results
        String existUsername;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT username FROM users WHERE username = '" + username + "';").executeQuery();
            resultSet.next();
            existUsername = resultSet.getString(1);

            //checks if the username exists
            if (username.equals(existUsername)){
                return true;
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return false;
    }

    public static boolean userHasCommentFeed(String username) {
        //declares the username found in the result set
        String foundUsername;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT username FROM feed WHERE username = '" + username +"' AND type = 'comment'").executeQuery();
            resultSet.next();
            foundUsername = resultSet.getString(1);

            //checks if there are results
            if (username.equals(foundUsername)){
                return true;
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return false;
    }

    public static boolean userHasLikeFeed(String username) {
        //declares the username found in the result set
        String foundUsername;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT username FROM feed WHERE username = '" + username +"' AND type = 'like'").executeQuery();
            resultSet.next();
            foundUsername = resultSet.getString(1);

            //checks if there are results
            if (username.equals(foundUsername)){
                return true;
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return false;
    }

    public static boolean userHasPostFeed(String username) {
        //declares the username found in the result set
        String foundUsername;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT username FROM feed WHERE username = '" + username +"' AND type = 'post'").executeQuery();
            resultSet.next();
            foundUsername = resultSet.getString(1);

            //checks if there are results
            if (username.equals(foundUsername)){
                return true;
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return false;
    }

    public static boolean postIdExists(int postID) {
        //declares the postID found in the results
        int existPostID;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT postID FROM posts WHERE postID = " + postID + ";").executeQuery();
            resultSet.next();
            existPostID = resultSet.getInt(1);

            //checks if the postID exists
            if (postID == existPostID)
                return true;
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return false;
    }

    public static int getNumberOfLikes(int postID) {
        //declares the number of likes
        int numberOfLikes = 0;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT COUNT(postID) FROM likes WHERE postID = " + postID + ";").executeQuery();
            resultSet.next();

            numberOfLikes = Integer.parseInt(resultSet.getString(1));
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return numberOfLikes;
    }

    public static String[] getLikerUsernames(int postID) {
        //declares the empty array
        String[] likerUsernames = new String[0];

        //declares the number of likes
        int numberOfLikes;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            //gets the number of likes
            resultSet = connection.prepareStatement("SELECT COUNT(postID) FROM likes WHERE postID = " + postID + ":").executeQuery();
            resultSet.next();
            numberOfLikes = Integer.parseInt(resultSet.getString(1));
            resultSet = null;

            //declares the array and gets the usernames
            likerUsernames = new String[numberOfLikes];
            resultSet = connection.prepareStatement("SELECT username FROM likes WHERE postID = " + postID + ":").executeQuery();
            resultSet.next();
            for (int i = 0; i < numberOfLikes; i++){
                likerUsernames[i] = resultSet.getString(1);
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return likerUsernames;
    }

    public static String getUserName(String username) {
        //declares the name found in the results
        String name = null;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT name FROM users WHERE username = '" + username + "';").executeQuery();
            resultSet.next();
            name = resultSet.getString(1);
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return name;
    }

    public static boolean postIsAd(int postID) {
        //declares the type
        String typeOfPost;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT type FROM posts WHERE postID = " + postID + ";").executeQuery();
            resultSet.next();
            typeOfPost = resultSet.getString(1);
            if (typeOfPost.equals("business"))
                return true;
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return false;
    }

    public static int getViews(int postID) {
        //I have already checked that the username is a business user. just go and check
        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT DISTINCT username FROM views WHERE postID = " + postID +";").executeQuery();
            resultSet.next();

            //counts the rows of the
            if (resultSet.last()) {
                return resultSet.getRow();
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return 0;
    }

    public static int getNumberOfLikeStats(int postID) {
        //I have checked that the user is business. you don't have to.
        //Also, this is the likestat; not the normal likes on a post (this is today's number of likes)
        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT DISTINCT username FROM likestat WHERE postID = " + postID +";").executeQuery();
            resultSet.next();

            //counts the rows of the
            if (resultSet.last()) {
                return resultSet.getRow();
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return 0;
    }

    public static boolean usersHaveDm(String username1, String username2) {

        return true;
    }

    public static int getDirectID(String username1, String username2) {

        return 0;
    }

    public static String getDirectMessageContent (int handle){
        //FIXME if theres no handle return "reply was deleted"
        return "reply was deleted";
    }

    public static boolean isUserBlocked(String blocker, String blocked) {
        return false;
    }

    public static boolean isPostLiked(int postID, String username) {
        return true;
    }

    public static boolean userFollows(String follower, String followed) {
        return true;
    }

    public static String getTotalViews(String username) {
        return "";
    }

    public static String getTotalLikes(String username) {
        return "";
    }

    public static String getGroupMessageContent(int handle) {
        return "";
    }

    public static boolean groupExists(int groupID) {
        return true;
    }
}