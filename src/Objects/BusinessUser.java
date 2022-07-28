package Objects;

public class BusinessUser extends User {


    @Override
    public UserType getUserType(){return UserType.BUSINESS;}

    public void addViewToAccount() {
        for (Post post: getPosts()) Database.Changer.addViewForUser(this.getUsername(), post.getPostID().getHandle());
    }

    public void addViewToAccount(Post post) {
        Database.Changer.addViewForUser(this.getUsername(), post.getPostID().getHandle());
    }

    //new methods
        //new statistics
}