package dev.dluks.rental.service.customer;

import dev.dluks.rental.exception.CustomerAlreadyRegisteredException;
import dev.dluks.rental.exception.CustomerNotFoundException;
import dev.dluks.rental.model.address.Address;
import dev.dluks.rental.model.customer.Customer;
import dev.dluks.rental.model.customer.CustomerType;
import dev.dluks.rental.repository.CustomerRepository;
import jakarta.persistence.Embedded;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static dev.dluks.rental.model.validator.document.SanitizeDocument.sanitizeDocument;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    @Embedded
    private Address address;

    @Autowired
    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }


    @Override
    public Customer createCustomer(Customer customer) {
       customer.setDocument(sanitizeDocument(customer.getDocument()));
       Customer existCustomer = repository.findByDocument(customer.getDocument());

        if(existCustomer != null && existCustomer.getType() == CustomerType.INDIVIDUAL){
            throw new CustomerAlreadyRegisteredException("CPF já Cadastrado");
        }

        if(existCustomer != null && existCustomer.getType() == CustomerType.CORPORATE){
            throw new CustomerAlreadyRegisteredException("CNPJ já Cadastrado");
        }


        return repository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        Customer existCustomer = repository.findByDocument(customer.getDocument());

        if(existCustomer == null){
            throw new RuntimeException("Nenhum Cliente Encontrado");
        }
        existCustomer.setName(customer.getName());
        existCustomer.setPhone(customer.getPhone());
        existCustomer.setEmail(customer.getEmail());
        return repository.save(existCustomer);
    }

    @Override
    public Customer findCustomerById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Customer findCustomerByDocument(String document) {
        document = sanitizeDocument(document);
        return repository.findByDocument(document);
    }

    @Override
    public List<Customer> findAllCustomers() {
        return repository.findAll();
    }

    @Override
    public List<Customer> findCustomerByName(String name) {
        List<Customer> list = repository.findByNameContainingIgnoreCase(name);

        if(list.isEmpty()){
            throw new CustomerNotFoundException("Nenhum Cliente Encontrado");
        }

        return list;

    }
}
