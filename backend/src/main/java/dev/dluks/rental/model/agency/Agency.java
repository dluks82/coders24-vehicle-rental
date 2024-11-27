package dev.dluks.rental.model.agency;

import dev.dluks.rental.model.base.BaseEntity;
import dev.dluks.rental.model.customer.CustomerType;
import dev.dluks.rental.model.validator.document.DocumentValidatorStrategy;
import dev.dluks.rental.model.validator.factory.DocumentValidatorFactory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import lombok.*;

@Entity
@Table(name = "agencies")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Agency extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String document;

    @Column(nullable = false)
    private String phone;

    @Email
    @Column(nullable = false)
    private String email;

    @Transient
    private DocumentValidatorStrategy validator;

//    private Address address;

    public Agency(String name, String document, String phone, String email) {

        this.name = name;
        this.phone = phone;
        this.email = email;

        this.validator = initializeValidator();
        setDocument(document);
    }

    private DocumentValidatorStrategy initializeValidator() {
        return DocumentValidatorFactory.getValidator(CustomerType.CORPORATE);
    }

    public void setDocument(String document) {
        validateDocument(document);
        this.document = document;
    }

    private void validateDocument(String document) {
        if (validator == null) {
            validator = initializeValidator();
        }
        if (!validator.isValid(document)) {
            throw new IllegalArgumentException("Invalid document for agency");
        }
    }
}
