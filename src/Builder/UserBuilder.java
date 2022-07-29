package Builder;

import Objects.BusinessUser;
import Objects.User;
import Objects.UserType;

import java.time.LocalDate;

public class UserBuilder {
    public static User getUserFromDatabase(String username){
        return getUserFromDatabaseDetailsOnly(username); //This is the default
    }

    public static User getUserFromDatabaseFull(String username){
        User user = getUserFromDatabase(username);
        //aaaaa

        return user;
    }

    public static User getUserFromDatabaseDetailsOnly(String username){
        String[] details = Database.Loader.getUserDetails(username);
        User user;
        if (details[4].equals(UserType.BUSINESS.toString())) user = new BusinessUser();
        else user = new User();
        user.setUsername(username);
        int i = 0;
        user.setName(details[i++]);
        user.setBio(details[i++]);
        user.setSubtitle(details[i++]);
        user.setDateJoined(LocalDate.parse(details[i]));
        return user;
    }

    public static User getUserFromDatabaseWithPosts(String username){
        //FIXME this only has posts and username

        return new User();
    }

    public static User getUserFromDatabaseWithFollowers(String username){
        //FIXME this only has username and followers

        return new User();
    }

    public static User getUserFromDatabaseWithFollowings(String username){
        //FIXME you want to implement the others, then set the user posts and feed and etc. to them while building the user

        return new User();
    }
}
