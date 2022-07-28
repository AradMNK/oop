package TextController;

import Login.Creator;
import Login.LoginState;
import Login.Loginner;
import Objects.UserType;

import java.util.Scanner;

public class TextController {
    public static final Scanner scanner = new Scanner(System.in);

    public static String getLine(){return scanner.nextLine();}
    public static String getNext(){return scanner.next();}

    private static void actOnCommand(Command command){
        switch (command.getCommandType()){
            case LOGIN -> Loginner.attemptLogin(command.getArgs());
            case CREATE_ACC -> Creator.attemptCreate(command.getArgs());
            case EDIT_ACC -> UserEditor.edit();
            case SIGNOUT -> Loginner.signout();
            case RELOAD -> Loginner.reload();

            case DETAILS -> Display.accountDetails(command.getArgs());
            case POSTS -> Display.accountPosts(command.getArgs());
            case POST -> PostController.newPost();
            case COMMENT -> CommentController.newComment(command.getArgs()[0]);
            case LIKE -> like(command.getArgs()[0]);
            case LIKES -> likes(command.getArgs()[0]);
            case LIKERS -> likers(command.getArgs()[0]);
            case FEED -> FeedController.show();
            case FOLLOW -> follow(command.getArgs()[0]);
            case FOLLOWERS -> Display.followers(command.getArgs());
            case FOLLOWINGS -> Display.followings(command.getArgs());
            case STATS -> StatsController.show(command.getArgs());
            case DM -> DmController.attemptEntrance(command.getArgs()[0]);
            case BLOCK -> block(command.getArgs()[0]);
            case UNBLOCK -> unblock(command.getArgs()[0]);

            case EXIT -> println("Paradoxical");
            case NONE -> println("You have typed in /" + CommandType.NONE + "! This command does nothing you idiot!");

            default -> println("No match for command /" + command.getCommandType());
        }
    }

    private static void unblock(String username) {
        if (!Loginner.loginnedUser.getBlocklist().contains(username)){
            TextController.println("The user [@" + username + "] was not in your blocklist.");
            return;
        }

        Loginner.loginnedUser.unblock(username);
    }

    private static void block(String username) {
        if (Loginner.loginnedUser.getBlocklist().contains(username)){
            TextController.println("The user [@" + username + "] was already in your blocklist.");
            return;
        }

        if (!Database.Loader.usernameExists(username)){
            TextController.println("The user [@" + username + "] does not exist.");
            return;
        }

        Loginner.loginnedUser.block(username);
    }

    private static void likers(String postIDasString) {
        if (Loginner.loginState == LoginState.SIGN_OUT){
            println("Please login first to like a post.");
            return;
        }

        int postID;
        try {postID = Integer.parseInt(postIDasString);} catch (NumberFormatException e){e.printStackTrace(); return;}

        if (!Database.Loader.postIdExists(postID)) {
            println("PostID \"" + postID + "\" does not exist.");
            return;
        }

        Display.writeUsers(Database.Loader.getLikerUsernames(postID));
    }

    private static void likes(String postIDasString) {
        if (Loginner.loginState == LoginState.SIGN_OUT){
            println("Please login first to like a post.");
            return;
        }
        int postID;
        try {postID = Integer.parseInt(postIDasString);} catch (NumberFormatException e){e.printStackTrace(); return;}

        if (!Database.Loader.postIdExists(postID)) {
            println("PostID \"" + postID + "\" does not exist.");
            return;
        }

        println("Total likes on your post [" + postID + "] is: " + Database.Loader.getNumberOfLikes(postID));
    }

    private static void like(String postIDasString) {
        if (Loginner.loginState == LoginState.SIGN_OUT){
            println("Please login first to like a post.");
            return;
        }

        int postID;
        try {postID = Integer.parseInt(postIDasString);} catch (NumberFormatException e){e.printStackTrace(); return;}

        if (!Database.Loader.postIdExists(postID)) {
            println("PostID \"" + postID + "\" does not exist.");
            return;
        }

        Loginner.loginnedUser.like(postID);
        if (Database.Loader.postIsAd(postID)) Database.Changer.addLikeStat(postID, Loginner.loginnedUser.getUsername());
    }

    private static void follow(String username) {
        if (Loginner.loginState == LoginState.SIGN_OUT){
            println("Please login first before trying to follow anyone.");
            return;
        }

        if (!Database.Loader.usernameExists(username)){
            println("Username \"@" + username + "\" does not exist.");
            return;
        }

        Database.Saver.addToFollowers(Loginner.loginnedUser.getUsername(), username);
    }

    public static void inputCommand(){
        Command command = new Command(getLine());

        while (!command.getCommandType().equals(CommandType.EXIT)){
            actOnCommand(command);
            command = new Command(getLine());
        }
    }

    public static UserType getUserType(){
        println("Please enter 1 for normal user and 2 for business user:");
        int type = scanner.nextInt();
        if (type == 1) return UserType.NORMAL;
        else if (type == 2) return UserType.BUSINESS;
        else return getUserType();
    }

    public static void println(String message){System.out.println(message);}
    public static void print(String message){System.out.print(message);}
}
