package dev.dluks.rental.exception;

public class CustomerAlreadyRegisteredException extends RuntimeException {
  public CustomerAlreadyRegisteredException(String message) {
    super(message);
  }
}
