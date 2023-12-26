package mouse.hoi.exception;

public class PropertyParseException extends RuntimeException{
    public PropertyParseException(String message) {
        super(message);
    }

    public PropertyParseException(String message, IllegalAccessException e) {
        super(message, e);
    }
}
