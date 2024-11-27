package dev.dluks.rental.model.agency;

import dev.dluks.rental.model.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "agencies")
public class Agency extends BaseEntity {

    private String name;
    private String document;
    private String phone;
    private String email;
//    private Address address;

    private void validateDocument() {
    }

}
