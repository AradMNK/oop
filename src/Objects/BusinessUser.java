package Objects;

public class BusinessUser extends User {


    @Override
    public UserType getUserType(){return UserType.BUSINESS;}

    public void addViewToAccount() {
        Database.Changer.addViewForUser(this.getUsername());
    }

    //new methods
        //new statistics
}