package dev.dluks.rental.repository;

import dev.dluks.rental.model.rental.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RentalRepository extends JpaRepository<Rental, UUID> {
}
