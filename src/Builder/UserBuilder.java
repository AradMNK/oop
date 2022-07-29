package Builder;

import Objects.BusinessUser;
import Objects.User;
import Objects.UserType;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

public class UserBuilder {
    public static User getUserFromDatabase(String username){
        return getUserFromDatabaseDetailsOnly(username); //This is the default
    }

    public static User getUserFromDatabaseFull(String username){
        User user = getUserFromDatabaseWithPosts(username);
        user.setFeed(FeedBuilder.getFeedFromDatabase(username));
        user.setFollowers(Arrays.stream(Database.Loader.getBlocklist(username)).collect(Collectors.toSet()));
        user.setFollowers(Arrays.stream(Database.Loader.getFollowers(username)).collect(Collectors.toSet()));
        user.setFollowings(Arrays.stream(Database.Loader.getFollowings(username)).collect(Collectors.toSet()));
        addGroups(user);
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
        User user = getUserFromDatabaseDetailsOnly(username);
        int[] postIDs = Database.Loader.getUserPosts();
        for (int postID: postIDs) user.getPosts().add(PostBuilder.getPostFromDatabaseWithComments(postID));
        return user;
    }

    public static User getUserFromDatabaseWithFollowers(String username){
        User user = getUserFromDatabaseDetailsOnly(username);
        user.setFollowers(Arrays.stream(Database.Loader.getFollowers(username)).collect(Collectors.toSet()));
        return user;
    }

    public static User getUserFromDatabaseWithFollowings(String username){
        User user = getUserFromDatabaseDetailsOnly(username);
        user.setFollowings(Arrays.stream(Database.Loader.getFollowings(username)).collect(Collectors.toSet()));
        return user;
    }

    private static void addGroups(User user) {
        int[] groupIDs = Database.Loader.getGroupsOfUser(user.getUsername());
        for (int groupID : groupIDs) user.getGroups().add(GroupBuilder.getGroupFromDatabase(groupID));
    }
}
