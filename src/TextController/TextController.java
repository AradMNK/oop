package TextController;

import Login.Creator;
import Login.LoginState;
import Login.Loginner;
import Objects.Group;
import Objects.SaveHandle;
import Objects.UserType;
import Recommender.UserRecommender;

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
            case UNLIKE -> unlike(command.getArgs()[0]);

            case LIKES -> likes(command.getArgs()[0]);
            case LIKERS -> likers(command.getArgs()[0]);

            case FEED -> FeedController.show();
            case FOLLOW -> follow(command.getArgs()[0]);
            case UNFOLLOW -> unfollow(command.getArgs()[0]);
            case FOLLOWERS -> Display.followers(command.getArgs());
            case FOLLOWINGS -> Display.followings(command.getArgs());
            case STATS -> StatsController.show(command.getArgs());

            case DM -> DmController.attemptEntrance(command.getArgs()[0]);
            case BLOCK -> block(command.getArgs()[0]);
            case UNBLOCK -> unblock(command.getArgs()[0]);

            case GROUPS -> GroupController.showGroups();
            case NEW_GROUP -> newGroup();
            case RECOMMEND_USER -> recommendUser();

            case HELP -> writeHelp();
            case EXIT -> println("Paradoxical");
            case NONE -> println("You have typed in /" + CommandType.NONE + "! This command does nothing you idiot!");

            default -> println("No match for command /" + command.getCommandType());
        }
    }

    private static void recommendUser() {
        if (Loginner.loginState == LoginState.SIGN_OUT){
            TextController.println("Please login before trying to get recommended users for you.");
            return;
        }

        String[] recommendedUsers = UserRecommender.recommendUser();

        println("The top " + recommendedUsers.length + " usernames for you are (in order):");
        for (String username: recommendedUsers) {
            println("[@" + username + "]");
        }
    }

    private static void writeHelp() {
        println("hi");
    }

    private static void newGroup() {
        if (Loginner.loginState == LoginState.SIGN_OUT){
            TextController.println("Please login before trying to create a group chat.");
            return;
        }

        Group group = new Group();
        TextController.println("Please enter a name for your group chat:");
        group.setName(TextController.getLine());
        TextController.println("Please enter a joining ID for inviting people.");
        group.setGroupJoiner(TextController.getLine());
        if (Database.Loader.groupJoinedExists(group.getGroupJoiner())){
            TextController.println("Joining ID already exists. Please choose a new one. Aborting command /"
                    + CommandType.NEW_GROUP);
            return;
        }
        group.setGroupID(new SaveHandle(Database.Saver.createGroup
                (Loginner.loginnedUser.getUsername(), group.getName(), group.getGroupJoiner())));
        group.getParticipants().add(Loginner.loginnedUser);

        GroupController.attemptEntrance(group);
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
            println("Please login first to see likes.");
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

        if (!Loginner.loginnedUser.like(postID)){
            println("You have already liked this post.");
            return;
        }
        if (Database.Loader.postIsAd(postID)) Database.Changer.addLikeStat(postID, Loginner.loginnedUser.getUsername());
    }
    private static void unlike(String postIDasString) {
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

        if (!Loginner.loginnedUser.unlike(postID)) println("You did not like this post in the first place.");
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

        if (!Loginner.loginnedUser.follow(username)) println("You already follow [@" + username + "].");
    }
    private static void unfollow(String username) {
        if (Loginner.loginState == LoginState.SIGN_OUT){
            println("Please login first before trying to unfollow anyone.");
            return;
        }

        if (!Database.Loader.usernameExists(username)){
            println("Username \"@" + username + "\" does not exist.");
            return;
        }

        if (!Loginner.loginnedUser.unfollow(username)) println("You don't follow [@" + username + "] anyway.");
    }

    public static void inputCommand(){
        Command command;
        try{command = new Command(getLine());}
        catch (CommandException e){
            TextController.println("What you just typed in was not defined." +
                "\nUse /" + CommandType.HELP + " to see the available commands.");
            inputCommand();
            return;
        }


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
