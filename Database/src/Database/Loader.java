package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Loader {
    public static boolean loginMatch(String username, String hashPass){
        //(this is checking if login credentials match)
        //declares username and password found in the results
        String loginUsername = null;
        String loginPassword = null;

        //declares a boolean for match, it's false by default in case no results is found or the password isn't true
        boolean match = false;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet = null;
        try {
            resultSet = connection.prepareStatement("SELECT username, hashPass FROM users WHERE username = " + username + ";").executeQuery();
            resultSet.next();
            loginUsername = resultSet.getString(1);
            loginPassword = resultSet.getString(2);

            //checks if the password is the same
            if (username.equals(loginUsername)){
                if (hashPass.equals(loginPassword)){
                    match = true;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connector.connector.disconnect();
        }
        return match;
    }

    public static boolean usernameExists(String username){//this checks if the username exists in the "login" table
        //declares the username found in the results
        String existUsername = null;

        //declares a boolean for existence, it's false by default in case no results is found or the password isn't true
        boolean exists = false;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet = null;
        try {
            resultSet = connection.prepareStatement("SELECT username, hashPass FROM users WHERE username = " + username + ";").executeQuery();
            resultSet.next();
            existUsername = resultSet.getString(1);

            //checks if the username exists
            if (username.equals(existUsername)){
                exists = true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connector.connector.disconnect();
        }
        return exists;
    }


    public static boolean userHasFeed(String username) {
        //FIXME
        return true;
    }

    public static boolean userHasCommentFeed(String username) {
        //FIXME
        return true;
    }

    public static boolean userHasPostFeed(String username) {
        //FIXME
        return true;
    }

    public static boolean postIdExists(String postID) {

        //declares the postID found in the results
        String existPostID = null;

        //declares a boolean for existence, it's false by default in case no results is found or the password isn't true
        boolean exists = false;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet = null;
        try {
            resultSet = connection.prepareStatement("SELECT postID FROM posts WHERE postID = " + postID + ";").executeQuery();
            resultSet.next();
            existPostID = resultSet.getString(1);

            //checks if the postID exists
            if (postID.equals(existPostID)){
                exists = true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connector.connector.disconnect();
        }
        return exists;
    }

    public static int getNumberOfLikes(String postID) {
        //declares the number of likes
        int numberOfLikes = 0;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet = null;
        try {
            resultSet = connection.prepareStatement("SELECT COUNT(postID) FROM likes WHERE postID = " + postID + ";").executeQuery();
            resultSet.next();

            numberOfLikes = Integer.parseInt(resultSet.getString(1));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connector.connector.disconnect();
        }
        return numberOfLikes;
    }

    public static String[] getLikerUsernames(String postID) {
        //declares the empty array
        String[] likerUsernames = new String[0];

        //declares the number of likes
        int numberOfLikes = 0;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet = null;
        try {
            //gets the number of likes
            resultSet = connection.prepareStatement("SELECT COUNT(postID) FROM likes WHERE postID = " + postID + ":").executeQuery();
            resultSet.next();
            numberOfLikes = Integer.parseInt(resultSet.getString(1));
            resultSet = null;

            //declares the array and gets the usernames
            likerUsernames = new String[numberOfLikes];
            resultSet = connection.prepareStatement("SELECT likerUsername FROM likes WHERE postID = " + postID + ":").executeQuery();
            resultSet.next();
            for (int i = 0; i < numberOfLikes; i++){
                likerUsernames[i] = resultSet.getString(1);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connector.connector.disconnect();
        }
        return likerUsernames;
    }

    public static String getUserName(String username) {
        //declares the name found in the results
        String name = null;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet = null;
        try {
            resultSet = connection.prepareStatement("SELECT name FROM users WHERE username = " + username + ";").executeQuery();
            resultSet.next();
            name = resultSet.getString(1);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connector.connector.disconnect();
        }
        //a user's name is different to its username. username is like @aradmnk but my name is Arad Mahdinejad
        return name;
    }

    public static boolean postIsAd(String postID) {
        //declares the type
        String typeOfPost = null;

        //declares a boolean
        boolean ad = false;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet = null;
        try {
            resultSet = connection.prepareStatement("SELECT type FROM posts WHERE postID = " + postID + ";").executeQuery();
            resultSet.next();
            typeOfPost = resultSet.getString(1);
            if (typeOfPost.equals("business")){
                ad = true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connector.connector.disconnect();
        }
        return ad;
    }

    public static int getViews(String username) {
        //FIXME i have already checked that the username is a business user. just go and check
        //declares the number of views
        int views = 0;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet = null;
        try {
            resultSet = connection.prepareStatement("").executeQuery();
            resultSet.next();


        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connector.connector.disconnect();
        }
        return 0;
    }

    public static int getNumberOfLikeStats(String postID) {
        //FIXME i have checked that the user is business. you don't have to.
        // Also, this is the likestat; not the normal likes on a post (this is today's number of likes)
        return 0;
    }

    public static boolean usersHaveDm(String username1, String username2) {

        return true;
    }
}
