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

import java.util.regex.Pattern;

@Entity
@Table(name = "agencies")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Agency extends BaseEntity {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

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

    @Builder
    public Agency(String name, String document, String phone, String email) {
        setName(name);
        setPhone(phone);
        setEmail(email);

        this.validator = initializeValidator();
        setDocument(document);
    }

    private DocumentValidatorStrategy initializeValidator() {
        return DocumentValidatorFactory.getValidator(CustomerType.CORPORATE);
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
        this.name = name;
    }

    public void setPhone(String phone) {
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone is required");
        }
        this.phone = phone;
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email;
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
