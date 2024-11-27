package dev.dluks.rental.model.validator.factory;

import dev.dluks.rental.model.customer.CustomerType;
import dev.dluks.rental.model.validator.document.CnpjValidator;
import dev.dluks.rental.model.validator.document.CpfValidator;
import dev.dluks.rental.model.validator.document.DocumentValidatorStrategy;

public class DocumentValidatorFactory {
    public static DocumentValidatorStrategy getValidator(CustomerType type) {
        return switch (type) {
            case INDIVIDUAL -> new CpfValidator();
            case CORPORATE -> new CnpjValidator();
        };
    }

    private DocumentValidatorFactory() {
    }
}