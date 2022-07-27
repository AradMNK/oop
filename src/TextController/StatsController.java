package TextController;

import Login.LoginState;
import Login.Loginner;
import Objects.UserType;

public class StatsController {
    public static void show(String[] args) {
        if (Loginner.loginState == LoginState.SIGN_OUT){
            TextController.println("Please login first to see your stats.");
            return;
        }

        if (Loginner.loginnedUser.getUserType() == UserType.NORMAL){
            TextController.println("You need to have a business account to use this command.");
            return;
        }

        if (args.length > 0){
            showForPostID(args[0]);
            return;
        }

        TextController.println("Today's views on your account: " + Database.Loader.getViews(Loginner.loginnedUser.getUsername()));
    }

    private static void showForPostID(String postID) {
        if (!Database.Loader.postIdExists(postID)){
            TextController.println("PostID \"" + postID + "\" does not exist.");
            return;
        }

        TextController.println("Today's likes on post \"" + postID + "\"were: " + Database.Loader.getNumberOfLikeStats(postID));
    }
}
