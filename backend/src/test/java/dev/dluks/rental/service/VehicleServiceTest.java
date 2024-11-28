package dev.dluks.rental.service;

import dev.dluks.rental.model.vehicle.Vehicle;
import dev.dluks.rental.model.vehicle.VehicleStatus;
import dev.dluks.rental.model.vehicle.VehicleType;
import dev.dluks.rental.repository.VehicleRepository;
import dev.dluks.rental.service.vehicle.CreateVehicleRequest;
import dev.dluks.rental.service.vehicle.VehicleResponseFull;
import dev.dluks.rental.service.vehicle.VehicleService;
import dev.dluks.rental.support.BaseUnitTest;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Vehicle Service Tests")
@MockitoSettings(strictness = Strictness.LENIENT)
class VehicleServiceTest extends BaseUnitTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleService vehicleService;

    private CreateVehicleRequest createCar;
    private Vehicle car;
    private Vehicle motorcycle;

    @BeforeEach
    void setUp() {
        createCar = CreateVehicleRequest.builder()
                .plate("ABC1234")
                .name("Honda Civic")
                .type(VehicleType.CAR)
                .build();

        car = new Vehicle("ABC1234", "Honda Civic", VehicleType.CAR);
        car.setStatus(VehicleStatus.AVAILABLE);
        car.setDailyRate(new BigDecimal("150.00"));

        motorcycle = new Vehicle("XYZ9876", "Honda CB500", VehicleType.MOTORCYCLE);
        motorcycle.setStatus(VehicleStatus.AVAILABLE);
        motorcycle.setDailyRate(new BigDecimal("100.00"));
    }

    @Nested
    @DisplayName("Create Vehicle")
    class CreateVehicle {

        @Test
        @DisplayName("should create vehicle successfully")
        void shouldCreateVehicle() {
            // given
            given(vehicleRepository.existsByPlate("ABC1234")).willReturn(false);
            given(vehicleRepository.save(any(Vehicle.class))).willReturn(car);

            // when
            VehicleResponseFull created = vehicleService.createVehicle(createCar);

            // then
            assertThat(created).isNotNull();
            assertThat(created.getId()).isNotNull();
            assertThat(created.getPlate()).isEqualTo("ABC1234");
            verify(vehicleRepository).save(any(Vehicle.class));
        }

        @Test
        @DisplayName("should throw exception when plate already exists")
        void shouldThrowExceptionWhenPlateExists() {
            // given
            given(vehicleRepository.existsByPlate("ABC1234")).willReturn(true);

            // when/then
            assertThatThrownBy(() -> vehicleService.createVehicle(createCar))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Vehicle with plate ABC1234 already exists");
        }
    }

    @Nested
    @DisplayName("Find Vehicle")
    class FindVehicle {

        @Test
        @DisplayName("should find vehicle by plate")
        void shouldFindVehicleByPlate() {
            // given
            String plate = "ABC1234";
            given(vehicleRepository.findByPlate(plate)).willReturn(Optional.of(car));

            // when
            VehicleResponseFull found = vehicleService.findByPlate(plate);

            // then
            assertThat(found.getId()).isNotNull();
            assertThat(found).isNotNull();
            assertThat(found.getPlate()).isEqualTo(plate);
        }

        @Test
        @DisplayName("should find vehicles by name")
        void shouldFindVehiclesByName() {
            // given
            String name = "Honda";
            List<Vehicle> vehicles = List.of(car, motorcycle);
            given(vehicleRepository.findByNameContainingIgnoreCase(name)).willReturn(vehicles);

            // when
            List<VehicleResponseFull> found = vehicleService.findByName(name);

            // then
            assertThat(found).hasSize(2);
            assertThat(found).extracting("name")
                    .containsExactlyInAnyOrder("Honda Civic", "Honda CB500");
        }
    }

    @Nested
    @DisplayName("Rental Operations")
    class RentalOperations {

        @Test
        @DisplayName("should rent available vehicle")
        void shouldRentAvailableVehicle() {
            // given
            UUID id = UUID.randomUUID();
            Vehicle vehicle = new Vehicle("ABC1234", "Test Car", VehicleType.CAR);
            given(vehicleRepository.findById(id)).willReturn(Optional.of(vehicle));
            given(vehicleRepository.save(any(Vehicle.class))).willReturn(vehicle);

            // when
            vehicleService.rent(id);

            // then
            verify(vehicleRepository).save(any(Vehicle.class));
        }

        @Test
        @DisplayName("should throw exception when vehicle not found for rental")
        void shouldThrowExceptionWhenVehicleNotFoundForRental() {
            // given
            UUID id = UUID.randomUUID();
            given(vehicleRepository.findById(id)).willReturn(Optional.empty());

            // when/then
            assertThatThrownBy(() -> vehicleService.rent(id))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("Vehicle not found with id: " + id);
        }
    }
}