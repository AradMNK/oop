package Login;

import Objects.User;
import TextController.TextController;
import Builder.UserBuilder;

public class Loginner {
    public static LoginState loginState = LoginState.SIGN_OUT;
    public static User loginnedUser = null;

    public static void attemptLogin(String[] args){
        String username, password;

        if (args.length == 0) {
            TextController.println("Please enter username and password: (username) (password)");
            attemptLogin(TextController.getLine().split("\\s"));
            return;
        }
        if (args.length < 2){
            username = args[0];
            TextController.println("Enter password for " + username + ":");
            String newLine = TextController.getLine();
            password = newLine.substring(0, newLine.indexOf(' '));
        }
        else{
            username = args[0];
            password = args[1];
        }

        attemptLogin(username, password);
    }

    public static void attemptLogin(String user, String pass) {
        if (loginState == LoginState.SIGN_IN) {
            TextController.println("You are already logged in as " + loginnedUser);
            return;
        }

        if (!Database.Loader.usernameExists(user)){
            TextController.println("Username does not exist. Please try again.");
            return;
        }

        boolean worked;
        worked = Database.Loader.loginMatch(user, Hasher.hash(pass));

        if (worked){
            loginState = LoginState.getLoginState(user);
            loginnedUser = UserBuilder.getUserFromDatabase(user);
            TextController.println("Successfully loginned as " + user);
        } else
            TextController.println("Could not match the credentials.");
    }

    public static void signout() {
        loginState = LoginState.SIGN_OUT;
        loginnedUser = null;
    }

    public static void reload(){
        if (loginState == LoginState.SIGN_OUT) {
            TextController.println("You have to be loginned first to reload.");
            return;
        }

        loginnedUser = UserBuilder.getUserFromDatabase(loginnedUser.getUsername());
    }
}
