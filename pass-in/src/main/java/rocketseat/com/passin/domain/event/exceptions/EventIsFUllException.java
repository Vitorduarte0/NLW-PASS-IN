package rocketseat.com.passin.domain.event.exceptions;

public class EventIsFUllException extends RuntimeException{
    public EventIsFUllException(String message) {
        super(message);
    }
}
