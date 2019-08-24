package vsv.task5;

import java.time.OffsetDateTime;

public class User {
    private String name;
    private OffsetDateTime LastLoggedIn;
    private boolean loggedIn;

    public User(String name) {
        this.name = name;
    }

    public void login() {
        loggedIn = true;
        LastLoggedIn = OffsetDateTime.now();
    }

    public void logout() {
        loggedIn = false;
    }

    public boolean canEdit(Comment comment) {
        return this.equals(comment.getAuthor());
    }

    public boolean canDelete(Comment comment) {
        return comment.getRepliedTo() != null && this.equals(comment.getRepliedTo().getAuthor());
    }

    public String getName() {
        return name;
    }

    public OffsetDateTime getLastLoggedIn() {
        return LastLoggedIn;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
}
