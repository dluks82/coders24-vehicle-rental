package dev.dluks.rental.repository;

import dev.dluks.rental.model.vehicle.Vehicle;
import dev.dluks.rental.model.vehicle.VehicleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class VehicleRepositoryTest {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Test
    @DisplayName("Should save vehicle")
    void shouldSaveVehicle() {
        // given
        Vehicle vehicle = new Vehicle("ABC1234", "Test Car", VehicleType.CAR);

        // when
        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        // then
        assertThat(savedVehicle.getId()).isNotNull();
        assertThat(savedVehicle.getPlate()).isEqualTo("ABC1234");
        assertThat(savedVehicle.getName()).isEqualTo("Test Car");
    }

    @Test
    @DisplayName("Should find vehicle by plate")
    void shouldFindVehicleByPlate() {
        // given
        Vehicle vehicle = new Vehicle("XYZ9876", "Test Motorcycle", VehicleType.MOTORCYCLE);
        vehicleRepository.save(vehicle);

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
        vehicleRepository.save(vehicle1);
        vehicleRepository.save(vehicle2);

        // when
        var found = vehicleRepository.findByNameContainingIgnoreCase("Honda");

        // then
        assertThat(found).hasSize(2);
        assertThat(found).extracting("name")
                .containsExactlyInAnyOrder("Honda Civic", "Honda CB500");
    }
}