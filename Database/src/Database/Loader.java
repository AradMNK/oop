package Database;

public class Loader {
    public static boolean loginMatch(String username, String hashPass){
        //FIXME (this is checking if login credentials match)

        return true; //FIXME change return values accordingly
    }

    public static boolean usernameExists(String username){
        //FIXME this checks if the username exists in the "login" table

        return true; //FIXME change return values accordingly
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
        //FIXME
        return true;
    }

    public static int getNumberOfLikes(String postID) {
        return 0;
    }

    public static String[] getLikerUsernames(String postID) {
        return new String[0];
    }

    public static String getUserName(String username) {
        //FIXME a user's name is different to its username. username is like @aradmnk but my name is Arad Mahdinejad
        return "";
    }

    public static boolean postIsAd(String postID) {
        //FIXME check the postID, see if postType is "business" or "normal"
        return true;
    }

    public static int getViews(String username) {
        //FIXME i have already checked that the username is a business user. just go and check
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
