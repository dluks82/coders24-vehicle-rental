package dev.dluks.rental.service.customer;

import dev.dluks.rental.exception.CustomerAlreadyRegisteredException;
import dev.dluks.rental.model.address.Address;
import dev.dluks.rental.model.address.AddressFactory;
import dev.dluks.rental.model.customer.Customer;
import dev.dluks.rental.model.customer.CustomerType;
import dev.dluks.rental.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl service;

    @Mock
    private CustomerRepository repository;

    private Customer customerIndividual;
    private Customer customerCorporate;
    private Address address;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerIndividual = CustomerFactory.createIndividualCustomer();
        customerCorporate = CustomerFactory.createCorporateCustomer();
        address = AddressFactory.createAddress();
    }

    @Nested
    @DisplayName("Creation")
    class Creation {

        @Test
        @DisplayName("Deve Criar um cliente individual com sucesso")
        void createIndividualCustomerSucess() {

            //given
            given(repository.save(any(Customer.class))).willReturn(customerIndividual);
            when(repository.findByDocument(customerIndividual.getDocument())).thenReturn(null);

            //when
            var createdCustomer = service.createCustomer(customerIndividual);

            //then
            assertNotNull(createdCustomer);
            assertNotNull(createdCustomer.getId());
            assertEquals(Customer.class, createdCustomer.getClass());
            assertEquals("00146729013", createdCustomer.getDocument());
            assertEquals(CustomerType.INDIVIDUAL, createdCustomer.getType());
            assertEquals("John Individual", createdCustomer.getName());
            assertEquals("12345678901", createdCustomer.getPhone());
            assertEquals("j@j.com", createdCustomer.getEmail());
            assertEquals(0, address.compareTo(createdCustomer.getAddress()));
        }

        @Test
        @DisplayName("Deve Criar um cliente Corporativo com sucesso")
        void createCorporateCustomerSucess() {

            //given
            given(repository.save(any(Customer.class))).willReturn(customerCorporate);
            when(repository.findByDocument(customerCorporate.getDocument())).thenReturn(null);

            //when
            var createdCustomer = service.createCustomer(customerCorporate);

            //then
            assertNotNull(createdCustomer);
            assertNotNull(createdCustomer.getId());
            assertEquals(Customer.class, createdCustomer.getClass());
            assertEquals("19132741000154", createdCustomer.getDocument());
            assertEquals(CustomerType.CORPORATE, createdCustomer.getType());
            assertEquals("John Corporate", createdCustomer.getName());
            assertEquals("12345678901", createdCustomer.getPhone());
            assertEquals("j@j.com", createdCustomer.getEmail());
            assertEquals(0, address.compareTo(createdCustomer.getAddress()));
        }

        @Test
        @DisplayName("Deve lançar exceção ao tentar cadastrar CPF já cadastrado")
        void createIndividualCustomerAlreadyRegistered() {
            when(repository.findByDocument("00146729013"))
                    .thenReturn(customerIndividual);

            assertThrows(CustomerAlreadyRegisteredException.class, () -> {
                service.createCustomer(customerIndividual);
            });
        }

        @Test
        @DisplayName("Deve lançar exceção ao tentar cadastrar CNPJ já cadastrado")
        void createCorporateCustomerAlreadyRegistered() {
            when(repository.findByDocument("19132741000154"))
                    .thenReturn(customerCorporate);

            assertThrows(CustomerAlreadyRegisteredException.class, () -> {
                service.createCustomer(customerCorporate);
            });
        }
    }

    @Nested
    @DisplayName("Update")
    class Update {
        @Test
        @DisplayName("Deve atualizar um cliente existente com sucesso")
        void updateCustomerSucess() {
            UUID customerId = UUID.randomUUID();

            var updateCustomer = Customer.builder()
                    .name("John Doe")
                    .document("00146729013")
                    .type(CustomerType.INDIVIDUAL)
                    .phone("12345678901")
                    .email("j@j.com")
                    .address(address)
                    .build();

            given(repository.findById(customerId)).willReturn(Optional.of(customerIndividual));
            given(repository.save(any(Customer.class))).willReturn(customerIndividual);

            var updatedCustomer = service.updateCustomer(customerId, updateCustomer);

            assertNotNull(updatedCustomer);
            assertNotNull(updatedCustomer.getId());
            assertEquals(Customer.class, updatedCustomer.getClass());
            assertEquals("00146729013", updatedCustomer.getDocument());
            assertEquals(CustomerType.INDIVIDUAL, updatedCustomer.getType());
            assertEquals("John Doe", updatedCustomer.getName());
            assertEquals("12345678901", updatedCustomer.getPhone());
            assertEquals("j@j.com", updatedCustomer.getEmail());
            assertEquals(address, updatedCustomer.getAddress());
        }

        @Test
        @DisplayName("Deve lançar exceção ao atualizar cliente inexistente")
        void updateNonExistentCustomer() {
            when(repository.findByDocument(anyString())).thenReturn(null);

            assertThrows(RuntimeException.class, () -> {
                service.updateCustomer(customerIndividual.getId(), customerIndividual);
            });
        }
    }

    @Nested
    @DisplayName("Search")
    class Search {

        @Test
        @DisplayName("Deve encontrar um cliente pelo ID")
        void findCustomerById() {
            UUID customerId = UUID.randomUUID();
            when(repository.findById(customerId))
                    .thenReturn(Optional.of(customerIndividual));

            Customer foundCustomer = service.findCustomerById(customerId);

            assertNotNull(foundCustomer);
            assertNotNull(foundCustomer.getId());
            assertEquals("John Individual", foundCustomer.getName());
            assertEquals(0, address.compareTo(foundCustomer.getAddress()));
        }

        @Test
        @DisplayName("Deve encontrar um cliente pelo Documento")
        void findCustomerByDocument() {
            when(repository.findByDocument("12345678900"))
                    .thenReturn(customerIndividual);

            Customer foundCustomer = service.findCustomerByDocument("12345678900");

            assertNotNull(foundCustomer);
            assertEquals("John Individual", foundCustomer.getName());
            assertEquals(0, address.compareTo(foundCustomer.getAddress()));
            assertEquals(CustomerType.INDIVIDUAL, foundCustomer.getType());
            assertEquals(Customer.class, foundCustomer.getClass());
        }

        @Test
        @DisplayName("Deve encontrar todos os clientes")
        void findAllCustomers() {
            List<Customer> customers = Arrays.asList(customerIndividual, customerCorporate);
            when(repository.findAll()).thenReturn(customers);

            List<Customer> foundCustomers = service.findAllCustomers();

            assertNotNull(foundCustomers);
            assertEquals(2, foundCustomers.size());
        }

        @Test
        void findCustomerByName() {
            List<Customer> customers = Arrays.asList(customerIndividual);
            when(repository.findByNameContainingIgnoreCase("John")).thenReturn(customers);

            List<Customer> foundCustomers = service.findCustomerByName("John");

            assertNotNull(foundCustomers);
            assertEquals(1, foundCustomers.size());
            assertEquals("John Individual", foundCustomers.get(0).getName());
        }
    }
}