package TextController;

public enum CommandType {
    LOGIN("login"),
    CREATE_ACC("create"),
    EDIT_ACC("edit"),
    RELOAD("reload"),
    SIGNOUT("signout"),

    DETAILS("details"),
    FEED("feed"),
    STATS("stats"),

    POST("post"),
    COMMENT("comment"),
    LIKE("like"),
    UNLIKE("unlike"),
    FOLLOW("follow"),
    UNFOLLOW("unfollow"),

    LIKES("likes"),
    LIKERS("likers"),
    POSTS("posts"),
    FOLLOWERS("followers"),
    FOLLOWINGS("followings"),

    DM("dm"),
    BLOCK("block"),
    UNBLOCK("unblock"),

    GROUPS("groups"),
    NEW_GROUP("newgroup"),

    RECOMMEND_USER("recommend_user"),
    RECOMMEND_AD("ad"),

    HELP("help"),
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
