package dev.dluks.rental.controller;

import dev.dluks.rental.model.customer.Customer;
import dev.dluks.rental.model.customer.CustomerType;
import dev.dluks.rental.service.customer.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;
    @Mock
    private CustomerService customerService;

    private Customer customer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

         customer = Customer.builder()
                 .name("John Doe")
                .document("00146729013")
                .type(CustomerType.INDIVIDUAL)
                .phone("12345678901")
                .email("j@j.com")
                .build();

    }

    @Test
    void createCustomer() {

        when(customerService.createCustomer(customer)).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.createCustomer(customer);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(customer, response.getBody());
        verify(customerService).createCustomer(customer);
    }

    @Test
    void updateCustomer() {

    }

    @Test
    void findCustomerById() {
    }

    @Test
    void findCustomerByDocument() {
    }

    @Test
    void findAllCustomers() {
    }

    @Test
    void findCustomerByName() {
    }
}