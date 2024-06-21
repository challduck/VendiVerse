package shop.project.vendiverse.exception;

public class NotFoundUser extends RuntimeException{
    public NotFoundUser(String m) {
        super(m);
    }
}
