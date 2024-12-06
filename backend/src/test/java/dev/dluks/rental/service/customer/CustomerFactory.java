package dev.dluks.rental.service.customer;

import dev.dluks.rental.model.address.Address;
import dev.dluks.rental.model.address.AddressFactory;
import dev.dluks.rental.model.customer.Customer;
import dev.dluks.rental.model.customer.CustomerType;

public class CustomerFactory {

    static Address address = AddressFactory.createAddress();

    public static Customer createIndividualCustomer() {
        return Customer.builder()
                .name("John Individual")
                .document("00146729013")
                .type(CustomerType.INDIVIDUAL)
                .phone("12345678901")
                .email("")
                .address(address).build();
    }

    public static Customer createCorporateCustomer() {
        return Customer.builder()
                .name("John Individual")
                .document("19132741000154")
                .type(CustomerType.CORPORATE)
                .phone("12345678901")
                .email("")
                .address(address).build();
    }
}
