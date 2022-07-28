package Login;

import Objects.User;
import Objects.UserType;
import TextController.*;

import java.time.LocalDate;

public class Creator {
    public static void attemptCreate(String[] args){
        String username, password, name;

        if (args.length == 0) {
            TextController.println("Please enter credentials and name: (username) (password) (name)");
            attemptCreate(TextController.getLine().split("\\s"));
            return;
        }
        if (args.length == 3){
            username = args[0];
            password = args[1];
            name = args[2];
        }
        else{
            TextController.println("Wrong syntax. Please try again.");
            return;
        }

        attemptCreate(username, password, name);
    }

    public static void attemptCreate(String user, String pass, String name) {
        if (Loginner.loginState == LoginState.SIGN_IN) {
            TextController.println("You can't create a new account when already signed in.");
            return;
        }

        if (Database.Loader.usernameExists(user)){
            TextController.println("Username already exists. Choose another username please.");
            return;
        }

        UserType userType;
        try {userType = TextController.getUserType();}
        catch (Exception e){
            TextController.println("You did not type a number. Please try again with /" + CommandType.CREATE_ACC);
            return;
        }

        Database.Saver.saveLogin(user, Hasher.hash(pass), name, LocalDate.now(), userType.toString());

        Loginner.loginnedUser = new User();
        Loginner.loginnedUser.setUsername(user);
        Loginner.loginnedUser.setDateJoined(LocalDate.now());
        Loginner.loginnedUser.setName(name);
        Loginner.loginState = LoginState.SIGN_IN;

        TextController.println("Successfully created user.");
    }
}
