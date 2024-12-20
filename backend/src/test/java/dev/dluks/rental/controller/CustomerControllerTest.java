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

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;
    @Mock
    private CustomerService service;

    private Customer customer;
   private  UUID id = UUID.randomUUID();

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

        when(service.createCustomer(customer)).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.createCustomer(customer);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(customer, response.getBody());
        verify(service).createCustomer(customer);
    }

    @Test
    void updateCustomer() {

        when(service.updateCustomer(id, customer)).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.updateCustomer(id, customer);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
        verify(service).updateCustomer(id, customer);

    }

    @Test
    void findCustomerById() {

        when(service.findCustomerById(id)).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.findCustomerById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
        verify(service).findCustomerById(id);
    }

    @Test
    void findCustomerByDocument() {
        String document = "12345";
        when(service.findCustomerByDocument(document)).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.findCustomerByDocument(document);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
        verify(service).findCustomerByDocument(document);
    }

    @Test
    void findAllCustomers() {
        List<Customer> customers = Arrays.asList(customer, customer);
        when(service.findAllCustomers()).thenReturn(customers);

        ResponseEntity<List<Customer>> response = customerController.findAllCustomers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customers, response.getBody());
        verify(service).findAllCustomers();
    }

    @Test
    void findCustomerByName() {
        String name = "John Doe";
        List<Customer> customers = Arrays.asList(customer, customer);
        when(service.findCustomerByName(name)).thenReturn(customers);

        ResponseEntity<List<Customer>> response = customerController.findCustomerByName(name);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customers, response.getBody());
        verify(service).findCustomerByName(name);
    }
}