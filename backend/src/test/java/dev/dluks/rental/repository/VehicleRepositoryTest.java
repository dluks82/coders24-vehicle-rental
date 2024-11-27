package dev.dluks.rental.repository;

import dev.dluks.rental.model.vehicle.Vehicle;
import dev.dluks.rental.model.vehicle.VehicleType;
import dev.dluks.rental.support.BaseRepositoryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Vehicle Repository Tests")
class VehicleRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Should save vehicle")
    void shouldSaveVehicle() {
        // given
        Vehicle vehicle = new Vehicle("ABC1234", "Test Car", VehicleType.CAR);

        // when
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        entityManager.flush();
        entityManager.clear();

        // then
        Vehicle foundVehicle = entityManager.find(Vehicle.class, savedVehicle.getId());
        assertThat(foundVehicle).isNotNull();
        assertThat(foundVehicle.getPlate()).isEqualTo("ABC1234");
        assertThat(foundVehicle.getName()).isEqualTo("Test Car");
    }

    @Test
    @DisplayName("Should find vehicle by plate")
    void shouldFindVehicleByPlate() {
        // given
        Vehicle vehicle = new Vehicle("XYZ9876", "Test Motorcycle", VehicleType.MOTORCYCLE);
        entityManager.persist(vehicle);
        entityManager.flush();

        // when
        var found = vehicleRepository.findByPlate("XYZ9876");

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getPlate()).isEqualTo("XYZ9876");
    }

    @Test
    @DisplayName("Should find vehicles by name containing")
    void shouldFindVehiclesByNameContaining() {
        // given
        Vehicle vehicle1 = new Vehicle("ABC1234", "Honda Civic", VehicleType.CAR);
        Vehicle vehicle2 = new Vehicle("XYZ9876", "Honda CB500", VehicleType.MOTORCYCLE);
        entityManager.persist(vehicle1);
        entityManager.persist(vehicle2);
        entityManager.flush();

        // when
        var found = vehicleRepository.findByNameContainingIgnoreCase("Honda");

        // then
        assertThat(found).hasSize(2);
        assertThat(found).extracting("name")
                .containsExactlyInAnyOrder("Honda Civic", "Honda CB500");
    }
}