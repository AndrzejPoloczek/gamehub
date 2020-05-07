package gamehub.gamebind.exception;

public class GameCancelException extends Exception {

    public GameCancelException(String message) {
        super(message);
    }

    public GameCancelException(String message, Throwable th) {
        super(message, th);
    }
}
