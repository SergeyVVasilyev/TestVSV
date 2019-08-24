package vsv.task5;

public class Admin extends Moderator {

    public Admin(String name) {
        super(name);
    }

    @Override
    public boolean canEdit(Comment comment) {
        return true;
    }
}
