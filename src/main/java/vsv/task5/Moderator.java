package vsv.task5;

public class Moderator extends User {

    public Moderator(String name) {
        super(name);
    }

    @Override
    public boolean canDelete(Comment comment) {
        return true;
    }
}
