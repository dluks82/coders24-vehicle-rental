package dev.dluks.rental.exception;

public class AgencyNotFoundException extends RuntimeException {
    public AgencyNotFoundException(String message) {
        super(message);
    }
}
