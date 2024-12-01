package dev.dluks.rental.service.customer;

import dev.dluks.rental.model.customer.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    Customer createCustomer(Customer customer);

    Customer updateCustomer(Customer customer);

    Customer findCustomerById(UUID id);

    Customer findCustomerByDocument(String document);

    List<Customer> findAllCustomers();

    List<Customer> findCustomerByName(String name);
}




