package Objects;

public enum UserType {
    NORMAL("normal"), BUSINESS("business");

    private final String label;

    UserType(String s){label = s;}

    @Override
    public String toString(){
        return label;
    }
}
