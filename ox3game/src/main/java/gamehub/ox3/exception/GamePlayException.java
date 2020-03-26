package gamehub.ox3.exception;

public class GamePlayException extends Exception {

    public GamePlayException(String message) {
        super(message);
    }

    public GamePlayException(String message, Throwable th) {
        super(message, th);
    }
}
