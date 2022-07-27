package TextController;

public enum CommandType {
    LOGIN("login"),
    CREATE_ACC("create"),
    EDIT_ACC("edit"),
    RELOAD("reload"),
    SIGNOUT("signout"),

    DETAILS("details"),
    POST("post"),
    POSTS("posts"),
    COMMENT("comment"),
    LIKE("like"),
    LIKES("likes"),
    LIKERS("likers"),
    FEED("feed"),
    FOLLOW("follow"),
    FOLLOWERS("followers"),
    FOLLOWINGS("followings"),
    STATS("stats"),
    DM("dm"),

    EXIT("exit"),
    NONE("none");

    private final String name;

    @Override
    public String toString(){return name;}

    CommandType(String name){this.name = name;}

    static CommandType toCommandType(String string){
        if (string.equals(LOGIN.name)) return LOGIN;
        return NONE;
    }
}
