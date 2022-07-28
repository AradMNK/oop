package Objects;

import java.time.LocalDate;
import java.util.HashSet;

public class User {
    private String name, username, subtitle, bio;
    private Handle pfp, theme;
    private Feed feed;
    private final HashSet<DirectMessenger> dms = new HashSet<>();
    private final HashSet<Post> posts = new HashSet<>();
    private final HashSet<User> blocklist = new HashSet<>(), followers = new HashSet<>(), followings = new HashSet<>();
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

    public HashSet<DirectMessenger> getDms() {return dms;}
    public HashSet<Post> getPosts() {return posts;}
    public HashSet<User> getBlocklist() {return blocklist;}
    public HashSet<User> getFollowers() {return followers;}
    public HashSet<User> getFollowings() {return followings;}
    public HashSet<Group> getGroups() {return groups;}

    public LocalDate getDateJoined() {return dateJoined;}
    public void setDateJoined(LocalDate dateJoined) {this.dateJoined = dateJoined;}

    public Feed getFeed() {return feed;}
    public void setFeed(Feed feed) {this.feed = feed;}

    public UserType getUserType(){return UserType.NORMAL;}

    public void post(String description){
        LocalDate date = LocalDate.now();
        int handle = Database.Saver.addToPosts(username, name, LocalDate.now(),
                description, getUserType().toString());
        for (User user: followers)
            Database.Saver.updateFeedsFromPost(user.username, handle);
    }

    public void comment(int postID, String msg){
        LocalDate date = LocalDate.now();
        int handle = Database.Saver.addToComments(username, name, LocalDate.now(), postID, msg);
        for (User user: followers)
            Database.Saver.updateFeedsFromComment(user.username, handle);
    }

    public void like(int postID){
        int handle = Database.Saver.addToLikes(postID, this.username);
        for (User user: followers)
            Database.Saver.updateFeedsFromLike(user.username, handle);
    }
}
