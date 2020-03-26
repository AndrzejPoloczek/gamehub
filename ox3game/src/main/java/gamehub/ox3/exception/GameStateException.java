package gamehub.ox3.exception;

public class GameStateException extends Exception {

    public GameStateException(String message) {
        super(message);
    }

    public GameStateException(String message, Throwable th) {
        super(message, th);
    }
}
