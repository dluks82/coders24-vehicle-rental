package dev.dluks.rental.model.customer;

import dev.dluks.rental.model.address.Address;
import dev.dluks.rental.model.base.BaseEntity;
import dev.dluks.rental.model.validator.document.DocumentValidatorStrategy;
import dev.dluks.rental.model.validator.factory.DocumentValidatorFactory;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "customers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String document;

    @Enumerated(EnumType.STRING)
    CustomerType type;

    @Column(nullable = false)
    private String phone;

    @Email
    @Column(nullable = false)
    private String email;

    @Transient
    private DocumentValidatorStrategy validator;

    @Embedded
    private Address address;

    @Builder
    public Customer(
            @NonNull String name,
            @NonNull String document,
            @NonNull CustomerType type,
            @NonNull String phone,
            @NonNull String email,
            Address address) {
        this.name = name;
        this.type = type;
        this.phone = phone;
        this.email = email;
        this.validator = initializeValidator();
        setDocument(document);
        this.address = address;
         }

    private DocumentValidatorStrategy initializeValidator() {
        return DocumentValidatorFactory.getValidator(type);
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
            throw new IllegalArgumentException("Invalid document for customer type " + type);
        }
    }
}
