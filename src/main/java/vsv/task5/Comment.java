package vsv.task5;

import java.time.OffsetDateTime;
import java.util.List;

public class Comment {
    private OffsetDateTime created;
    private User author;
    private String message;
    private Comment repliedTo;

    public Comment(User author, String message, Comment repliedTo) {
        this.author = author;
        this.message = message;
        this.repliedTo = repliedTo;
    }

    public OffsetDateTime getCreated() {
        return created;
    }

    public User getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    public Comment getRepliedTo() {
        return repliedTo;
    }

    @Override
    public String toString() {
        String res = String.format("%s by %s", message, author.getName());
        if (repliedTo != null) {
            res = String.format("%s (replied to %s)", res, repliedTo.getAuthor().getName());
        }
        return res;
    }
}
