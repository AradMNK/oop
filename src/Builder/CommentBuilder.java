package Builder;

import Objects.Comment;
import Objects.SaveHandle;

public class CommentBuilder {
    public static Comment getCommentFromDatabase(int commentID){
        Comment comment = new Comment();
        String[] details = Database.Loader.getCommentDetails(commentID);
        comment.setCommentID(new SaveHandle(commentID));

        return comment;
    }
}
