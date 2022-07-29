package Objects;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;

public class User {
    private String name, username, subtitle, bio;
    private Handle pfp, theme;
    private Feed feed;
    private final HashSet<Post> posts = new HashSet<>();
    private final HashSet<String> blocklist = new HashSet<>(), followers = new HashSet<>(), followings = new HashSet<>();
    private final HashSet<Group> groups = new HashSet<>();
    private LocalDate dateJoined;

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getSubtitle() {return subtitle;}
    public void setSubtitle(String subtitle) {this.subtitle = subtitle;}

    public String getBio() {return bio;}
    public void setBio(String bio) {this.bio = bio;}

    public Handle getPfp() {return pfp;}
    public void setPfp(Handle pfp) {this.pfp = pfp;}

    public Handle getTheme() {return theme;}
    public void setTheme(Handle theme) {this.theme = theme;}

    public HashSet<Post> getPosts() {return posts;}
    public HashSet<String> getBlocklist() {return blocklist;}
    public HashSet<String> getFollowers() {return followers;}
    public HashSet<String> getFollowings() {return followings;}
    public HashSet<Group> getGroups() {return groups;}

    public LocalDate getDateJoined() {return dateJoined;}
    public void setDateJoined(LocalDate dateJoined) {this.dateJoined = dateJoined;}

    public Feed getFeed() {return feed;}
    public void setFeed(Feed feed) {this.feed = feed;}

    public UserType getUserType(){return UserType.NORMAL;}

    public void post(String description){
        LocalDateTime dateTime = LocalDateTime.now();
        int handle = Database.Saver.addToPosts(username, dateTime, description, getUserType().toString());
        posts.add(new Post(handle, description, dateTime, this));
        for (String usernames: followers)
            Database.Saver.updateFeedsFromPost(usernames, handle);
    }

    public void comment(int postID, String msg){
        int handle = Database.Saver.addToComments(username, LocalDateTime.now(), postID, msg);
        for (String usernames: followers)
            Database.Saver.updateFeedsFromComment(usernames, handle);
    }

    public boolean like(int postID){
        if (Database.Loader.isPostLiked(postID, this.username)) return false;

        Database.Saver.addToLikes(postID, this.username);
        for (String usernames: followers)
            Database.Saver.updateFeedsFromLike(usernames, postID);
        return true;
    }
    public boolean unlike(int postID){
        if (!Database.Loader.isPostLiked(postID, this.username)) return false;

        Database.Changer.removeLike(postID, this.username);
        return true;
    }

    public boolean follow (String username){
        if (Database.Loader.userFollows(this.username, username)) return false;

        Database.Saver.addToFollowers(this.username, username);
        followers.add(username);
        return true;
    }
    public boolean unfollow (String username){
        if (!Database.Loader.userFollows(this.username, username)) return false;

        Database.Changer.removeFromFollowers(this.username, username);
        followers.remove(username);
        return true;
    }

    public boolean block(String username) {
        if (Database.Loader.isUserBlocked(this.username, username)) return false;

        blocklist.add(username);
        Database.Saver.addToBlocklist(this.username, username);
        return true;
    }
    public boolean unblock(String username) {
        if (!Database.Loader.isUserBlocked(this.username, username)) return false;

        blocklist.remove(username);
        Database.Changer.removeFromBlockList(this.username, username);
        return true;
    }
}
