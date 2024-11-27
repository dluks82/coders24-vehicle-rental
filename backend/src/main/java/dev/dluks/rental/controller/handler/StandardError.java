package dev.dluks.rental.controller.handler;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StandardError {
    private Integer status;
    private LocalDateTime timestamp;
    private String message;

    public StandardError(Integer status, LocalDateTime timestamp, String message) {
        this.status = status;
        this.timestamp = timestamp;
        this.message = message;
    }
}