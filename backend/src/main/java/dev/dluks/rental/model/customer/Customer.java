package dev.dluks.rental.model.customer;

import dev.dluks.rental.model.base.BaseEntity;

public class Customer extends BaseEntity {

    private String name;
    private String document;
    CustomerType type;
    private String phone;
    private String email;
//    private Address address;


    private void validateDocument() {
    }
}
