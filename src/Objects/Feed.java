package Objects;

import java.util.HashMap;
import java.util.HashSet;

public class Feed {
    private final HashSet<Post> posts = new HashSet<>();
    private final HashSet<Comment> comments = new HashSet<>();
    private final HashMap<Post, Integer> likes = new HashMap<>();

    public HashSet<Post> getPosts() {return posts;}
    public HashSet<Comment> getComments() {return comments;}
    public HashMap<Post, Integer> getLikers() {return likes;}
}