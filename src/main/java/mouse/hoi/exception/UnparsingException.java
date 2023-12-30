package mouse.hoi.exception;

public class UnparsingException extends RuntimeException{
    public UnparsingException(String message) {
        super(message);
    }

    public UnparsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
