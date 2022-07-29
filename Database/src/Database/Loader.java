package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class Loader {
    public static boolean loginMatch(String username, String hashPass){
        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT * FROM users WHERE username = '" + username
                                                        + "' AND hashPass = '" + hashPass + "';").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()) {
                return true;
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return false;
    }

    public static boolean usernameExists(String username){
        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT * FROM users WHERE username = '" + username + "';").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()) {
                return true;
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return false;
    }

    public static boolean userHasCommentFeed(String username) {
        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT * FROM feed WHERE username = '" + username
                                                        +"' AND type = 'comment'").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()) {
                return true;
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return false;
    }

    public static boolean userHasLikeFeed(String username) {
        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT * FROM feed WHERE username = '" + username
                                                        +"' AND type = 'like'").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()) {
                return true;
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return false;
    }

    public static boolean userHasPostFeed(String username) {
        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT * FROM feed WHERE username = '" + username
                                                        +"' AND type = 'post'").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()) {
                return true;
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return false;
    }

    public static boolean postIdExists(int postID) {
        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT * FROM posts WHERE postID = " + postID + ";").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()) {
                return true;
            }
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
            resultSet = connection.prepareStatement("SELECT COUNT(postID) FROM likes WHERE postID = " + postID
                                                        + ";").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()) {
                resultSet.next();
                numberOfLikes = Integer.parseInt(resultSet.getString(1));
            }
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
            resultSet = connection.prepareStatement("SELECT COUNT(postID) FROM likes WHERE postID = " + postID
                                                        + ":").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                //gets the number of likes
                resultSet.next();
                numberOfLikes = resultSet.getInt(1);

                if (numberOfLikes != 0) {
                    //declares the array and gets the usernames
                    likerUsernames = new String[numberOfLikes];

                    //gets the likes
                    resultSet = connection.prepareStatement("SELECT username FROM likes WHERE postID = " + postID
                                                                + ";").executeQuery();
                    resultSet.next();
                    for (int i = 0; i < numberOfLikes; i++) {
                        likerUsernames[i] = resultSet.getString(1);
                    }
                }
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return likerUsernames;
    }

    public static String getUserName (String username){
        //declares the security question number
        String name = null;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT name FROM users WHERE username = '" + username
                                                        + "';").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                name = resultSet.getString(1);
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return name;
    }

    public static String[] getUserDetails (String username){
        //declares a string array to store the details
        String[] details = new String[5];

        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT name, bio, subtitle, date, type FROM users WHERE username = '"
                                                        + username + "';").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                resultSet.next();
                for (int i = 0; i < 5; i++){
                    details[i] = resultSet.getString(i+1);
                }
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return details;
    }

    public static boolean postIsAd(int postID) {
        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT * FROM posts WHERE postID = " + postID
                                                        + " AND type = 'business';").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                return true;
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return false;
    }

    public static int getViews(int postID) {
        //declares the view count
        int viewCount = 0;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT COUNT(DISTINCT username) FROM views WHERE postID = " + postID
                                                        +";").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                viewCount = resultSet.getInt(1);
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return viewCount;
    }

    public static int getNumberOfLikeStats(int postID) {
        //declares the view count
        int likeCount = 0;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT COUNT(DISTINCT username) FROM likestat WHERE postID = " + postID
                                                        +";").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                likeCount = resultSet.getInt(1);
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return likeCount;
    }

    public static boolean usersHaveDm(String username1, String username2) {
        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            //finds the messages username1 has sent to username2
            resultSet = connection.prepareStatement("SELECT * FROM directmessages WHERE sender = '" + username1
                                                        + "' AND receiver = '" + username2 + "';").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                return true;
            }

            //finds the messages username2 has sent to username1
            resultSet = connection.prepareStatement("SELECT * FROM directmessages WHERE sender = '" + username2
                                                        + "' AND receiver = '" + username1 + "';").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                return true;
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return false;
    }

    public static String getDirectMessageContent (int handle){
        //declares the message found in the result set
        String message;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT message FROM directs WHERE directID = '"
                                                        + handle + "';").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                resultSet.next();
                message = resultSet.getString(1);
                return message;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connector.connector.disconnect();
        }
        return "message was deleted";
    }

    public static boolean isUserBlocked(String blocker, String blocked) {
        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT * FROM block WHERE blocker = '" + blocker
                                                        + "' and blocked ='" + blocked + "';").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                return true;
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return false;
    }

    public static boolean isPostLiked(int postID, String username) {
        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT * FROM likes WHERE postID = " + postID
                                                        + " AND username = '" + username + "';").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                return true;
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}

        return false;
    }

    public static boolean userFollows(String follower, String followed) {
        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT * FROM follow WHERE follower = '" + follower
                                                        + "' AND followed = '" + followed + "';").executeQuery();

            //checks if the resultSet is empty
            resultSet.next();
            if (resultSet.next()){
                return true;
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return false;
    }

    public static int getTotalViews(String username) {
        //declares the view count
        int viewCount = 0;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT COUNT(DISTINCT view.username) FROM posts INNER JOIN views"
                                                        + " WHERE posts.postID = views.postID AND"
                                                        + " posts.username = '" + username + "';").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                resultSet.next();
                viewCount = resultSet.getInt(1);
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return viewCount;
    }

    public static int getTotalLikes(String username) {
        //declares the view count
        int likeCount = 0;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT COUNT(DISTINCT likestat.username) FROM posts INNER JOIN likestat"
                                                        + " WHERE posts.postID = likestat.postID AND"
                                                        + " posts.username = '" + username + "';").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                resultSet.next();
                likeCount = resultSet.getInt(1);
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return likeCount;
    }

    public static String getGroupMessageContent(int handle) {
        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT message FROM directmessages WHERE messageID = "
                                                        + handle +";").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                resultSet.next();
                return resultSet.getString(1);
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return "";
    }

    public static boolean groupExists(int groupID) {
        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT * FROM groups WHERE groupIS = " + groupID + ";").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                return true;
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return false;
    }

    public static boolean groupJoinerExists(String groupJoiner) {
        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT * FROM groups WHERE joinID = " + groupJoiner + ";").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                return true;
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return false;
    }

    public static String getPostPoster(int postID) {
        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT username FROM posts WHERE postID = " + postID + ";").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                resultSet.next();
                return resultSet.getString(1);
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return "";
    }

    public static HashSet<Integer> getLikedAds(String username) {
        //declares a HashSet to store the ads
        HashSet<Integer> likedAds = new HashSet<>();

        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT DISTINCT postID FROM likestat WHERE username = '" + username
                                                        + "';").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                resultSet.next();
                while (resultSet.next()){
                    likedAds.add(resultSet.getInt(1));
                    resultSet.next();
                }
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return likedAds;
    }

    public static int getGroupID(String joiner) {
        //declares the groupID
        int groupID = 0;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT groupID FROM groups WHERE joinID = '"
                                                        + joiner + "';").executeQuery();

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

    public static int getSecurityQuestionNumber(String username) {
        //declares the security question number
        int securityQuestionNumber = 0;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT questionID FROM users WHERE username = '"
                                                        + username + "';").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                securityQuestionNumber = resultSet.getInt(1);
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return securityQuestionNumber;
    }

    public static boolean doesSecurityQuestionAnswerMatch(String username, String answer) {
        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT * FROM users WHERE username = '" + username
                                                        + "' AND answer = '" + answer + "';").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()) {
                return true;
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return false;
    }

    public static String[] getUnreadUsers (String username){
        //declares the empty array
        String[] unreadUsers = new String[0];

        //declares the number of users
        int numberOfUsers;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT COUNT(DISTINCT username) FROM unreadusers WHERE forUsername = "
                                                        + username + ";").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                //gets the number of likes
                resultSet.next();
                numberOfUsers = resultSet.getInt(1);

                if (numberOfUsers != 0) {
                    //declares the array and gets the usernames
                    unreadUsers = new String[numberOfUsers];

                    //gets the likes
                    resultSet = connection.prepareStatement("SELECT DISTINCT username FROM unreadusers WHERE forUsername = "
                                                                + username + ";").executeQuery();
                    resultSet.next();
                    for (int i = 0; i < numberOfUsers; i++) {
                        unreadUsers[i] = resultSet.getString(1);
                    }
                }
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return unreadUsers;
    }

    public static int[] getUnreadGroups (String username){
        //declares the empty array
        int[] unreadGroups = new int[0];

        //declares the number of groups
        int numberOfGroups;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT COUNT(DISTINCT groupID) FROM unreadgroups WHERE forUsername = "
                                                        + username + ";").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                //gets the number of likes
                resultSet.next();
                numberOfGroups = resultSet.getInt(1);

                if (numberOfGroups != 0) {
                    //declares the array and gets the usernames
                    unreadGroups = new int[numberOfGroups];

                    //gets the likes
                    resultSet = connection.prepareStatement("SELECT DISTINCT groupID FROM unreadgroups WHERE forUsername = "
                                                                + username + ";").executeQuery();
                    resultSet.next();
                    for (int i = 0; i < numberOfGroups; i++) {
                        unreadGroups[i] = resultSet.getInt(1);
                    }
                }
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return unreadGroups;
    }

    public static int getUnreadCountForUsername(String forUsername, String username) {
        //declares the message count
        int count = 0;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT count FROM unreadusers WHERE forUsername = '"
                                                        + forUsername + "' AND username = '" + username + "';").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                resultSet.next();
                count = resultSet.getInt(1);
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return count;
    }

    public static int getUnreadCountForGroupID(String forUsername, int groupID) {
        //declares the message count
        int count = 0;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT count FROM unreadgroups WHERE forUsername = '"
                                                        + forUsername + "' AND groupID = " + groupID + ";").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                resultSet.next();
                count = resultSet.getInt(1);
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return count;
    }

    public static String[] getBlocklist(String username) {
        //declares the empty array
        String[] blockList = new String[0];

        //declares the number of blocked users
        int blockedCount;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT COUNT(blocked) FROM block WHERE blocker = "
                                                        + username + ";").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                //gets the number of blocked users
                resultSet.next();
                blockedCount = resultSet.getInt(1);

                if (blockedCount != 0) {
                    //declares the array
                    blockList = new String[blockedCount];

                    //gets the usernames
                    resultSet = null;
                    resultSet = connection.prepareStatement("SELECT blocked FROM block WHERE blocker = "
                                                                + username + ";").executeQuery();
                    resultSet.next();
                    for (int i = 0; i < blockedCount; i++) {
                        blockList[i] = resultSet.getString(1);
                    }
                }
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return blockList;
    }

    public static String[] getFollowers(String username) {
        //declares the empty array
        String[] followers = new String[0];

        //declares the number of followers
        int followerCount;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT COUNT(follower) FROM follow WHERE followed = "
                                                        + username + ";").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                //gets the number of followers
                resultSet.next();
                followerCount = resultSet.getInt(1);

                if (followerCount != 0) {
                    //declares the array
                    followers = new String[followerCount];

                    //gets the usernames
                    resultSet = null;
                    resultSet = connection.prepareStatement("SELECT follower FROM follow WHERE followed = "
                                                                + username + ";").executeQuery();
                    resultSet.next();
                    for (int i = 0; i < followerCount; i++) {
                        followers[i] = resultSet.getString(1);
                    }
                }
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return followers;
    }

    public static String[] getFollowings(String username) {
        //declares the empty array
        String[] followings = new String[0];

        //declares the number of followings
        int followingCount;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT COUNT(followed) FROM follow WHERE follower = "
                                                        + username + ";").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                //gets the number of followings
                resultSet.next();
                followingCount = resultSet.getInt(1);

                if(followingCount != 0) {
                    //declares the array
                    followings = new String[followingCount];

                    //gets the usernames
                    resultSet = null;
                    resultSet = connection.prepareStatement("SELECT followed FROM follow WHERE follower = "
                                                                 + username + ";").executeQuery();
                    resultSet.next();
                    for (int i = 0; i < followingCount; i++) {
                        followings[i] = resultSet.getString(1);
                    }
                }
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return followings;
    }

    public static int[] getUserPosts(String username) {
        //declares the empty array
        int[] posts = new int[0];

        //declares the number of posts
        int postCount;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT COUNT(postID) FROM posts WHERE username = "
                                                        + username + ";").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                //gets the number of posts
                resultSet.next();
                postCount = resultSet.getInt(1);

                if (postCount != 0) {
                    //declares the array
                    posts = new int[postCount];

                    //gets the postIDs
                    resultSet = null;
                    resultSet = connection.prepareStatement("SELECT postID FROM posts WHERE username = "
                                                                + username + ";").executeQuery();
                    resultSet.next();
                    for (int i = 0; i < postCount; i++) {
                        posts[i] = resultSet.getInt(1);
                    }
                }
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return posts;
    }

    public static int[] getGroupsOfUser(String username) {
        //declares the empty array
        int[] groups = new int[0];

        //declares the number of groups
        int groupCount;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT COUNT(groupID) FROM groups WHERE members LIKE '"
                                                        + username + ",%' OR '%," + username + "' OR '%,"
                                                        + username + ",%'" + "OR '" + username + "';").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                //gets the number of groups
                resultSet.next();
                groupCount = resultSet.getInt(1);

                if (groupCount != 0) {
                    //declares the array
                    groups = new int[groupCount];

                    //gets the groupIDs
                    resultSet = null;
                    resultSet = connection.prepareStatement("SELECT COUNT(groupID) FROM groups WHERE members LIKE '"
                                                                + username + ",%' OR '%," + username + "' OR '%,"
                                                                + username + ",%'" + "OR '" + username + "';").executeQuery();
                    resultSet.next();
                    for (int i = 0; i < groupCount; i++) {
                        groups[i] = resultSet.getInt(1);
                    }
                }
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return groups;
    }

    public static String[] getCommentDetails(int commentID) {
        //declares a string array to store the details
        String[] details = new String[4];

        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT username, postID, comment, date FROM comments WHERE commentID = '"
                                                        + commentID + "';").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                resultSet.next();
                for (int i = 0; i < 4; i++){
                    details[i] = resultSet.getString(i+1);
                }
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return details;
    }

    public static String[] getPostDetails(int postID) {
        //declares a string array to store the details
        String[] details = new String[4];

        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT username, description, date, type FROM posts WHERE postID = '"
                                                        + postID + "';").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                resultSet.next();
                for (int i = 0; i < 4; i++){
                    details[i] = resultSet.getString(i+1);
                }
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
        return details;
    }
}