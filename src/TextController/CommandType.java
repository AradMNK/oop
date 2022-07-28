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
    UNLIKE("unlike"),
    LIKES("likes"),
    LIKERS("likers"),
    FEED("feed"),
    FOLLOW("follow"),
    UNFOLLOW("unfollow"),
    FOLLOWERS("followers"),
    FOLLOWINGS("followings"),
    STATS("stats"),

    DM("dm"),
    BLOCK("block"),
    UNBLOCK("unblock"),

    GROUPS("groups"),
    NEW_GROUP("newgroup"),

    EXIT("exit"),
    NONE("none");

    private final String name;

    @Override
    public String toString(){return name;}

    CommandType(String name){this.name = name;}

    static CommandType toCommandType(String string){
        for (CommandType commandType: CommandType.values())
            if (commandType.name.equals(string)) return commandType;
        return CommandType.NONE;
    }
}
