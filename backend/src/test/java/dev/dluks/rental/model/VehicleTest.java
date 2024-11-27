package dev.dluks.rental.model;

import dev.dluks.rental.model.vehicle.Vehicle;
import dev.dluks.rental.model.vehicle.VehicleStatus;
import dev.dluks.rental.model.vehicle.VehicleType;
import dev.dluks.rental.support.BaseUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Vehicle")
class VehicleTest extends BaseUnitTest {

    private Vehicle car;
    private Vehicle motorcycle;
    private Vehicle truck;

    @BeforeEach
    void setUp() {
        car = new Vehicle("ABC1234", "Civic", VehicleType.CAR);
        motorcycle = new Vehicle("XYZ9876", "Harley Davidson", VehicleType.MOTORCYCLE);
        truck = new Vehicle("DEF5678", "Volvo", VehicleType.TRUCK);
    }

    @Nested
    @DisplayName("Creation")
    class Creation {

        @Test
        @DisplayName("should create car with valid data")
        void shouldCreateCarWithValidData() {
            // then
            assertAll(
                    () -> assertEquals("ABC1234", car.getPlate()),
                    () -> assertEquals("Civic", car.getName()),
                    () -> assertEquals(VehicleType.CAR, car.getType()),
                    () -> assertEquals(VehicleStatus.AVAILABLE, car.getStatus()),
                    () -> assertEquals(new BigDecimal("150.00"), car.getDailyRate())
            );
        }

        @Test
        @DisplayName("should create motorcycle with valid data")
        void shouldCreateMotorcycleWithValidData() {
            // then
            assertAll(
                    () -> assertEquals("XYZ9876", motorcycle.getPlate()),
                    () -> assertEquals(VehicleType.MOTORCYCLE, motorcycle.getType()),
                    () -> assertEquals(new BigDecimal("100.00"), motorcycle.getDailyRate()),
                    () -> assertEquals(VehicleStatus.AVAILABLE, motorcycle.getStatus())
            );
        }

        @Test
        @DisplayName("should create truck with valid data")
        void shouldCreateTruckWithValidData() {
            // then
            assertAll(
                    () -> assertEquals("DEF5678", truck.getPlate()),
                    () -> assertEquals(VehicleType.TRUCK, truck.getType()),
                    () -> assertEquals(new BigDecimal("200.00"), truck.getDailyRate()),
                    () -> assertEquals(VehicleStatus.AVAILABLE, truck.getStatus())
            );
        }

        @Test
        @DisplayName("should not create with invalid plate")
        void shouldNotCreateWithInvalidPlate() {
            // given & when & then
            assertAll(
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> new Vehicle("123", "Invalid", VehicleType.CAR)),
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> new Vehicle("ABCD123", "Invalid", VehicleType.CAR)),
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> new Vehicle("ABC12345", "Invalid", VehicleType.CAR)),
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> new Vehicle(null, "Invalid", VehicleType.CAR))
            );
        }
    }

    @Nested
    @DisplayName("Status Management")
    class StatusManagement {

        @Test
        @DisplayName("should rent available vehicle")
        void shouldRentAvailableVehicle() {
            // given
            Vehicle vehicle = car;

            // when
            vehicle.rent();

            // then
            assertEquals(VehicleStatus.RENTED, vehicle.getStatus());
        }

        @Test
        @DisplayName("should not rent already rented vehicle")
        void shouldNotRentAlreadyRentedVehicle() {
            // given
            Vehicle vehicle = truck;
            vehicle.rent();

            // when & then
            assertThrows(IllegalStateException.class, vehicle::rent);
        }

        @Test
        @DisplayName("should return rented vehicle")
        void shouldReturnRentedVehicle() {
            // given
            Vehicle vehicle = motorcycle;
            vehicle.rent();

            // when
            vehicle.returnVehicle();

            // then
            assertEquals(VehicleStatus.AVAILABLE, vehicle.getStatus());
        }

        @Test
        @DisplayName("should not return already available vehicle")
        void shouldNotReturnAlreadyAvailableVehicle() {
            // given
            Vehicle vehicle = car;

            // when & then
            assertThrows(IllegalStateException.class, vehicle::returnVehicle);
        }
    }
}