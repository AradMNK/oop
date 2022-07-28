package TextController;

import Builder.PostBuilder;
import Builder.UserBuilder;
import Login.LoginState;
import Login.Loginner;
import Objects.*;

public class Display {
    private static final int overlineCount = 15;

    public static void accountDetails(String[] args) {
        if (Loginner.loginState == LoginState.SIGN_OUT){
            TextController.println("Please login first to see account details.");
            return;
        }

        User user;
        if (args.length > 0){
            if (Database.Loader.usernameExists(args[0])){
                TextController.println("User was not found in database. Try using a different username.");
                return;
            }

            user = (args[0].equals(Loginner.loginnedUser.getUsername())) ?
                    Loginner.loginnedUser : UserBuilder.getUserFromDatabaseDetailsOnly(args[0]);
        }
        else user = Loginner.loginnedUser;

        TextController.println(user.getName() + " (" + args[0] + ")");
        String out = user.getSubtitle();
        if (!out.equals("")) TextController.println(out);

        out = user.getBio();
        if (!out.equals("")) TextController.println("Bio: " + out);

        if (user.getDateJoined() != null) TextController.println("Joined: " + user.getDateJoined());

        TextController.println("User type: " + user.getUserType());
    }

    public static void accountPosts(String[] args){
        if (Loginner.loginState == LoginState.SIGN_OUT){
            TextController.println("Please login first to see account details.");
            return;
        }

        User user;
        if (args.length > 0){
            if (Database.Loader.usernameExists(args[0])){
                TextController.println("User was not found in database. Try using a different username.");
                return;
            }

            user = (args[0].equals(Loginner.loginnedUser.getUsername())) ?
                    Loginner.loginnedUser : UserBuilder.getUserFromDatabaseWithPosts(args[0]);
        }
        else user = Loginner.loginnedUser;

        TextController.println(user.getName() + "[@" + user.getUsername() + "]:");
        TextController.println("‾".repeat(user.getName().length() + 1));
        boolean isBusiness = (user.getUserType() == UserType.BUSINESS);
        if (isBusiness) {
            ((BusinessUser)user).addViewToAccount();
            TextController.println("** ADS **");
        }
        for (Post post: user.getPosts()) {
            TextController.print("(" + post.getDatePosted() + "):");
            TextController.println(post.getDescription());
            TextController.println("postid: " + post.getPostID() + "\n");
        }
    }

    public static void followers(String[] args) {
        if (Loginner.loginState == LoginState.SIGN_OUT){
            TextController.println("Please login first to see account details.");
            return;
        }

        User user;
        if (args.length > 0){
            if (Database.Loader.usernameExists(args[0])){
                TextController.println("User was not found in database. Try using a different username.");
                return;
            }

            user = (args[0].equals(Loginner.loginnedUser.getUsername())) ?
                    Loginner.loginnedUser : UserBuilder.getUserFromDatabaseWithFollowers(args[0]);
        }
        else user = Loginner.loginnedUser;

        for (String flw: user.getFollowers())
            TextController.println("[@" + flw + "]");
    }

    public static void followings(String[] args) {
        if (Loginner.loginState == LoginState.SIGN_OUT){
            TextController.println("Please login first to see account details.");
            return;
        }

        User user;
        if (args.length > 0){
            if (Database.Loader.usernameExists(args[0])){
                TextController.println("User was not found in database. Try using a different username.");
                return;
            }

            user = (args[0].equals(Loginner.loginnedUser.getUsername())) ?
                    Loginner.loginnedUser : UserBuilder.getUserFromDatabaseWithFollowings(args[0]);
        }
        else user = Loginner.loginnedUser;

        for (String flw: user.getFollowings())
            TextController.println(" [@" + flw + "]");
    }

    public static void writeUsers(String[] usernames){
        for (String username : usernames)
            TextController.println(Database.Loader.getUserName(username) + " [@" + username + "]");
    }

    public static void writeComments(int postID) {
        Post post = PostBuilder.getPostFromDatabaseWithComments(postID);
        TextController.println("Post:");
        writePost(post);
        TextController.println("‾".repeat(overlineCount));
        TextController.println("Comments:");
        for (Comment comment: post.getComments()) writeComment(comment);
    }

    private static void writePost(Post post){
        if (post.getPoster().getUserType() == UserType.BUSINESS) TextController.print("** AD **");
        TextController.println("(" + post.getDatePosted() + ") "
                + post.getPoster().getName() + "[@" + post.getPoster().getUsername() + "]:" );
        TextController.println(post.getDescription());
        TextController.println("postid: " + post.getPostID());
    }

    private static void writeComment(Comment comment) {
        TextController.println("(" + comment.getDate() + ") "
                + comment.getCommenter().getName() + "[@" + comment.getCommenter().getUsername() + "]:" );
        TextController.println(comment.getMsg());
    }
}
