package dev.dluks.rental.service.customer;

import dev.dluks.rental.model.customer.Customer;
import dev.dluks.rental.model.customer.CustomerType;
import dev.dluks.rental.model.validator.document.SanitizeDocument;
import dev.dluks.rental.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }


    @Override
    public Customer createCustomer(Customer customer) {
        Customer existCustomer = repository.findByDocument(customer.getDocument());

        if(existCustomer != null && existCustomer.getType() == CustomerType.INDIVIDUAL){
            throw new RuntimeException("CPF já Cadastrado");
        }

        if(existCustomer != null && existCustomer.getType() == CustomerType.CORPORATE){
            throw new RuntimeException("CNPJ já Cadastrado");
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
        document = SanitizeDocument.sanitizeDocument(document);
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
            throw new RuntimeException("Nenhum Cliente Encontrado");
        }

        return list;

    }
}
