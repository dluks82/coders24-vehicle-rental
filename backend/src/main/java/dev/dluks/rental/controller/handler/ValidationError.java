package dev.dluks.rental.controller.handler;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ValidationError extends StandardError {
    private List<String> errors;

    public ValidationError(Integer status, LocalDateTime timestamp, String message, List<String> errors) {
        super(status, timestamp, message);
        this.errors = errors;
    }
}