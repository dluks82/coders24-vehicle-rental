package dev.dluks.rental.model.customer;

import dev.dluks.rental.model.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {

    private String name;
    private String document;
    @Enumerated(EnumType.STRING)
    CustomerType type;
    private String phone;
    private String email;
//    private Address address;


    private void validateDocument() {
    }
}
