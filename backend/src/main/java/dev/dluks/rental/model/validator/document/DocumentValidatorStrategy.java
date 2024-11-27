package dev.dluks.rental.model.validator.document;

public interface DocumentValidatorStrategy {
    boolean isValid(String document);
}
