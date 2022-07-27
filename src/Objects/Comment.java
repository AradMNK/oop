package Objects;

import java.time.LocalDate;

public class Comment {
    private String msg;
    private User commenter;
    private SaveHandle commentID;
    private Post post;
    private LocalDate date;

    public Post getPost() {return post;}
    public void setPost(Post post) {this.post = post;}

    public String getMsg() {return msg;}
    public void setMsg(String msg) {this.msg = msg;}

    public User getCommenter() {return commenter;}
    public void setCommenter(User commenter) {this.commenter = commenter;}

    public SaveHandle getCommentID() {return commentID;}
    public void setCommentID(SaveHandle commentID) {this.commentID = commentID;}

    public LocalDate getDate() {return date;}
    public void setDate(LocalDate date) {this.date = date;}
}
