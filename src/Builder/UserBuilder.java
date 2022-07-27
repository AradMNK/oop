package Builder;

import Objects.User;

public class UserBuilder {
    public static User getUserFromDatabase(String username){
        return getUserFromDatabaseDetailsOnly(username); //This is the default
    }

    public static User getUserFromDatabaseFull(String username){
        //FIXME you want to implement the others, then set the user posts and feed and etc. to them while building the user

        return new User();
    }

    public static User getUserFromDatabaseDetailsOnly(String username){
        //FIXME you want to implement the others, then set the user posts and feed and etc. to them while building the user

        return new User();
    }

    public static User getUserFromDatabaseWithPosts(String username){
        //FIXME you want to implement the others, then set the user posts and feed and etc. to them while building the user

        return new User();
    }

    public static User getUserFromDatabaseWithFollowers(String username){
        //FIXME you want to implement the others, then set the user posts and feed and etc. to them while building the user

        return new User();
    }

    public static User getUserFromDatabaseWithFollowings(String username){
        //FIXME you want to implement the others, then set the user posts and feed and etc. to them while building the user

        return new User();
    }
}
