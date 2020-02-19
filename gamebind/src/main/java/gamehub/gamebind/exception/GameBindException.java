package gamehub.gamebind.exception;

public class GameBindException extends Exception {

    public GameBindException(String message) {
        super(message);
    }

    public GameBindException(String message, Throwable th) {
        super(message, th);
    }
}
