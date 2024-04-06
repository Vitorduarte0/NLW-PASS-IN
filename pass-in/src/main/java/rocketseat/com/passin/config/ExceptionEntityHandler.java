package rocketseat.com.passin.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import rocketseat.com.passin.domain.attendee.exceptions.AttendeeAlreadyExistException;
import rocketseat.com.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import rocketseat.com.passin.domain.checkin.exceptions.CheckinAlreadyExistsException;
import rocketseat.com.passin.domain.event.exceptions.EventIsFUllException;
import rocketseat.com.passin.domain.event.exceptions.EventNotFoundException;
import rocketseat.com.passin.dto.general.ErrorHttpResponseDTO;

@ControllerAdvice
public class ExceptionEntityHandler {
    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity handleEventNotFound(EventNotFoundException eventNotFoundException) {
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(EventIsFUllException.class)
    public ResponseEntity<ErrorHttpResponseDTO> EventIsFUllException(EventIsFUllException eventNotFoundException) {
        return ResponseEntity.badRequest().body(new ErrorHttpResponseDTO(eventNotFoundException.getMessage()));
    }
    @ExceptionHandler(AttendeeNotFoundException.class)
    public ResponseEntity AttendeeNotFound(AttendeeNotFoundException eventNotFoundException) {
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(AttendeeAlreadyExistException.class)
    public ResponseEntity AttendeeAlreadyExist(AttendeeAlreadyExistException eventNotFoundException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
    @ExceptionHandler(CheckinAlreadyExistsException.class)
    public ResponseEntity CheckinAlreadyExist(CheckinAlreadyExistsException eventNotFoundException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

}
