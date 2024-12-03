package dev.dluks.rental.repository;

import dev.dluks.rental.model.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Customer findByDocument(String document);
    List<Customer> findByNameContainingIgnoreCase(String name);
}
