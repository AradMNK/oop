package TextController;

import Login.LoginState;
import Login.Loginner;

public class DmController {

    public static void attemptEntrance(String username) {
        if (Loginner.loginState == LoginState.SIGN_OUT){
            TextController.println("Please login before trying to message anyone.");
            return;
        }
        if (!Database.Loader.usernameExists(username)){
           TextController.println("Could not find match for username [@" + username + "]!");
           return;
        }

        if (Database.Loader.usersHaveDm(Loginner.loginnedUser.getUsername(), username)){

        }
    }
}
