package Builder;

import Objects.Group;

public class GroupBuilder {
    public static Group getGroupFromDatabase(int groupID){
        return getGroupFromDatabaseWithName(groupID);
    }

    public static Group getGroupFromDatabaseWithName(int groupID){
        //FIXME
        return new Group();
    }

    public static Group getGroupFromDatabaseFull(int groupID){
        //FIXME
        return new Group();
    }
}
