package dev.dluks.rental.repository;

import dev.dluks.rental.model.agency.Agency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AgencyRepository extends JpaRepository<Agency, UUID> {
}
