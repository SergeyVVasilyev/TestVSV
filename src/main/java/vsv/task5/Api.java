package vsv.task5;

import java.time.OffsetDateTime;

public class Api {

    public OffsetDateTime getLastLoggedIn(User user) {
        return user.getLastLoggedIn();
    }

    public boolean canEditComment(User user, Comment comment) {
        return user.canEdit(comment);
    }

    public boolean canDeleteComment(User user, Comment comment) {
        return user.canDelete(comment);
    }

    public Comment createComment(User author, String message) {
        return new Comment(author, message, null);
    }

    public Comment createComment(User author, String message, Comment replyTo) {
        return new Comment(author, message, replyTo);
    }

    public Comment getReplyTo(Comment comment) {
        return comment.getRepliedTo();
    }
}
