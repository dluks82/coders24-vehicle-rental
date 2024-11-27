package dev.dluks.rental.repository;

import dev.dluks.rental.model.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
