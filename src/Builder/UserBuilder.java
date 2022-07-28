package Builder;

import Objects.User;

public class UserBuilder {
    public static User getUserFromDatabase(String username){
        return getUserFromDatabaseDetailsOnly(username); //This is the default
    }

    public static User getUserFromDatabaseFull(String username){
        //FIXME this has EVERYTHING but the pointers to other things are limited

        return new User();
    }

    public static User getUserFromDatabaseDetailsOnly(String username){
        //FIXME this only has details

        return new User();
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
