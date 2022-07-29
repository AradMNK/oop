package Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Changer {
    public static void removePostFromFeed(String username, int postID) {
        Connector.queryWithoutResult("DELETE FROM feed WHERE username = '" + username + "' AND postID = " + postID + " AND type = post;");
    }

    public static void removeCommentFromFeed(String username, int commentID) {
        Connector.queryWithoutResult("DELETE FROM feed WHERE username = '" + username
                                            + "' AND postID = " + commentID + " AND type = cpmment;");
    }

    public static void removeLikeFromFeed(String username, int handle) {
        Connector.queryWithoutResult("DELETE FROM feed WHERE username = '" + username
                                            + "' AND postID = " + handle + " AND type = like;");
    }

    public static void addViewForUser(int postID, String username) {
        Connector.queryWithoutResult("INSERT INTO views (username, postID) VALUES ('"
                                            + username + "', " + postID + ");");
    }
    public static void addLikeStat(int postID, String username) {
        Connector.queryWithoutResult("INSERT INTO likestat (username, postID) VALUES ('"
                                            + username + "', " + postID + ");");
    }

    public static void editMessage(int messageID, String line) {
        Connector.queryWithoutResult("UPDATE directmessages SET message = '" + line
                                            + "' WHERE messageID = " + messageID + ";");
    }

    public static void deleteMessage(int handle) {
        Connector.queryWithoutResult("DELETE FROM directmessages WHERE messageID = " + handle + ";");
    }

    public static void editGroupMessage(int messageID, String line) {
        Connector.queryWithoutResult("UPDATE groupmessages SET message = '" + line
                                            + "' WHERE messageID = " + messageID + ";");
    }

    public static void deleteGroupMessage(int handle) {
        Connector.queryWithoutResult("DELETE FROM directmessages WHERE messageID = " + handle + ";");
    }

    public static void removeFromBlockList(String blocker, String blocked) {
        Connector.queryWithoutResult("DELETE FROM blocks WHERE blocker = '" + blocker
                                            +"' AND blocked = '" + blocked + "';");
    }

    public static void removeFromFollowers(String follower, String followed) {
        Connector.queryWithoutResult("DELETE FROM follow WHERE follower = '" + follower
                                            + "' AND followed = '" + followed + "';");
    }

    public static void removeLike(int postID, String username) {
        Connector.queryWithoutResult("DELETE FROM likes WHERE username = '"
                                            + username + "' AND postID = " + postID + ";");
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

            //checks if the resultSet is empty
            if (resultSet.next()){
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

                //removes the member from group
                Connector.queryWithoutResult("UPDATE groups SET members = '" + members
                                                    + "' WHERE groupID = " + groupID + ";");
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
    }

    public static void changeGroupJoiner(/*int handle, */String newID) {
        //Connector.queryWithoutResult("UPDATE groups SET joinID = '" + newID +"' WHERE groupID = " + handle + ";");
        //FIXME
    }

    public static void addUserToGroup(String username, int handle) {
        //declares group members
        String members = null;

        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT members FROM groups WHERE groupID = " + handle + ";").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                resultSet.next();
                members = resultSet.getString(1);

                //adds the member to the group
                members = (members + "," + username);
                Connector.queryWithoutResult("UPDATE groups SET members = '" + members
                                                    +"' WHERE groupID = " + handle + ";");
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
    }

    public static void removeParticipant(int handle, String username) {
        //declares a string for the members
        String members;

        //finds the members
        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT members FROM groups WHERE groupID = " + handle + ";").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
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

                //removes the member from group
                Connector.queryWithoutResult("UPDATE groups SET members = '" + members
                        + "' WHERE groupID = " + handle + ";");
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
    }

    public static void removeFromBanList(int handle, String username) {
        //declares a string for the ban list
        String banned;

        //finds the members
        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT banList FROM groups WHERE groupID = " + handle + ";").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                resultSet.next();
                banned = resultSet.getString(1);

                //removes the user
                banned.replaceAll(username, "");

                //checks for ,
                if (banned.charAt(0) == ','){
                    banned = banned.substring(1);
                }
                if (banned.charAt(banned.length()-1) == ','){
                    banned = banned.substring(banned.length()-1);
                }
                banned.replaceAll(",,", ",");

                //removes the member from group
                Connector.queryWithoutResult("UPDATE groups SET banList = '" + banned
                        + "' WHERE groupID = " + handle + ";");
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
    }

    public static void removeFromGroups(String username, int handle) {
        //checks if there's such group with this admin
        Connection connection = Connector.connector.connect();
        ResultSet resultSet;
        try {
            resultSet = connection.prepareStatement("SELECT * FROM groups WHERE groupID = " + handle
                                                        + " AND admin = '" + username + "';").executeQuery();

            //checks if the resultSet is empty
            if (resultSet.next()){
                Connector.queryWithoutResult("DELETE FROM groups WHERE groupID = " + handle
                                                    + " AND admin = '" + username + "';");
            }
        }
        catch (SQLException e) {e.printStackTrace();}
        finally {Connector.connector.disconnect();}
    }
}
