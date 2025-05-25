package pl.kurs.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.kurs.dto.ExceptionResponseDto;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoCarFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleCarNotFoundException(NoCarFoundException exception) {
        ExceptionResponseDto response = new ExceptionResponseDto(exception.getMessage(), HttpStatus.NOT_FOUND.toString(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
    }

    @ExceptionHandler(NullFieldException.class)
    public ResponseEntity<ExceptionResponseDto> handleNullFieldException(NullFieldException exception) {
        ExceptionResponseDto response = new ExceptionResponseDto(exception.getMessage(), HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponseDto> handleConstraintViolationException(ConstraintViolationException exception) {
        String errorMessages = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("; "));

        ExceptionResponseDto response = new ExceptionResponseDto(errorMessages, HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseDto> handleValidationException(MethodArgumentNotValidException exception) {
        String errorMessages = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        ExceptionResponseDto response = new ExceptionResponseDto(errorMessages, HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response);
    }



//    @Aspect("")
//    public void join() {
//
//    }
}
