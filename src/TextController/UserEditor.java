package TextController;

import Login.LoginState;
import Login.Loginner;

public class UserEditor {
    final static String end = "end";

    public static void edit(){
        if (Loginner.loginState == LoginState.SIGN_OUT){
            TextController.println("Please login first before trying to edit you account.");
            return;
        }

        String next = TextController.getNext();
        while (!next.equals(end)){
            int index = next.indexOf('=');
            String tag = next.substring(0, index), value = next.substring(index + 1);
            setUserByTag(Loginner.loginnedUser.getUsername(), tag, value);

            next = TextController.getNext();
        }
    }

    private static void setUserByTag(String username, String tag, String value) {
        switch (tag){
            case "bio" -> Database.Saver.setUserBio(username, value);
            case "sub" -> Database.Saver.setUserSubtitle(username, value);
        }
    }
}
