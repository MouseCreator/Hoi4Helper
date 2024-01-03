package mouse.hoi.exception;

public class GameFileParseException extends RuntimeException{
    public GameFileParseException(String message) {
        super(message);
    }

    public GameFileParseException(Exception e) {
        super(e);
    }
}
