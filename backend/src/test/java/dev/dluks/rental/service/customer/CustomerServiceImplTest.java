package dev.dluks.rental.service.customer;

import dev.dluks.rental.exception.CustomerAlreadyRegisteredException;
import dev.dluks.rental.model.address.Address;
import dev.dluks.rental.model.customer.Customer;
import dev.dluks.rental.model.customer.CustomerType;
import dev.dluks.rental.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;


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
        startCustomer();
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
        assertEquals(address, createdCustomer.getAddress());
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
        assertEquals(address, createdCustomer.getAddress());
         }

         @Test
        @DisplayName("Deve lançar exceçãpo ao tentar cadastrar CPF já cadastrado")
        void createIndividualCustomerAlreadyRegistered() {
           when (repository.findByDocument(anyString())).thenThrow(new CustomerAlreadyRegisteredException("CPF ja cadastrado"));

            try{
                repository.findByDocument("00146729013");
            }catch (Exception e){
                assertEquals(CustomerAlreadyRegisteredException.class, e.getClass());
                assertEquals("CPF ja cadastrado", e.getMessage());
            }

        }

        @Test
        @DisplayName("Deve lançar exceçãpo ao tentar cadastrar CNPJ já cadastrado")
        void createCorporateCustomerAlreadyRegistered() {
            when (repository.findByDocument(anyString())).thenThrow(new CustomerAlreadyRegisteredException("CNPJ ja cadastrado"));

            try{
                repository.findByDocument("19132741000154");
            }catch (Exception e){
                assertEquals(CustomerAlreadyRegisteredException.class, e.getClass());
                assertEquals("CNPJ ja cadastrado", e.getMessage());
            }

        }



    }



    @Nested
    @DisplayName("Update")
    class Update {
        @Test
        @DisplayName("Deve atualizar um cliente existente com sucesso")
        void updateCustomerSucess() {
        }


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

    private void startCustomer() {
         address = new Address("street", "number", "complement", "neighborhood", "city", "state", "zipCode");
         customerIndividual = new Customer("John Individual", "00146729013", CustomerType.INDIVIDUAL, "12345678901", "j@j.com", address);
         customerCorporate = new Customer("John Corporate", "19132741000154", CustomerType.CORPORATE, "12345678901", "j@j.com", address);
    }
}