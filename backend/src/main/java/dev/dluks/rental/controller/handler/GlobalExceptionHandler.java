package dev.dluks.rental.controller.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        return ResponseEntity.badRequest().body(
                new ValidationError(
                        HttpStatus.BAD_REQUEST.value(),
                        LocalDateTime.now(),
                        "Validation failed",
                        errors));
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<StandardError> handleBusinessExceptions(RuntimeException ex) {
        log.error("Business error: ", ex);
        return ResponseEntity.badRequest().body(
                new StandardError(
                        HttpStatus.BAD_REQUEST.value(),
                        LocalDateTime.now(),
                        ex.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardError> handleMessageNotReadable(HttpMessageNotReadableException ex) {
        String message = "Invalid request format";
        if (ex.getCause() instanceof InvalidFormatException invalidFormat) {
            message = String.format("Invalid value '%s' for field '%s'. Allowed values are: %s",
                    invalidFormat.getValue(),
                    invalidFormat.getPath().get(0).getFieldName(),
                    Arrays.toString(invalidFormat.getTargetType().getEnumConstants()));
        }

        return ResponseEntity.badRequest().body(
                new StandardError(HttpStatus.BAD_REQUEST.value(),
                        LocalDateTime.now(),
                        message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> handleUnexpectedError(Exception ex) {
        log.error("Unexpected error: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new StandardError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        LocalDateTime.now(),
                        "An unexpected error occurred"));
    }

    @Getter
    @AllArgsConstructor
    public static class Problem {
        private Integer status;
        private LocalDateTime timestamp;
        private String message;
        private List<String> errors;
    }
}