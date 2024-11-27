package dev.dluks.rental.repository;

import dev.dluks.rental.model.vehicle.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {

    Optional<Vehicle> findByPlate(String plate);

    @Query("SELECT v FROM Vehicle v WHERE LOWER(v.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Vehicle> findByNameContainingIgnoreCase(@Param("name") String name);

    boolean existsByPlate(String plate);
}