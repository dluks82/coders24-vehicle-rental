package dev.dluks.rental.model;

import dev.dluks.rental.model.agency.Agency;
import dev.dluks.rental.model.agency.AgencyFactory;
import dev.dluks.rental.model.customer.Customer;
import dev.dluks.rental.model.rental.Rental;
import dev.dluks.rental.model.rental.RentalStatus;
import dev.dluks.rental.model.vehicle.Vehicle;
import dev.dluks.rental.model.vehicle.VehicleFactory;
import dev.dluks.rental.model.vehicle.VehicleStatus;
import dev.dluks.rental.model.vehicle.VehicleType;
import dev.dluks.rental.service.customer.CustomerFactory;
import dev.dluks.rental.support.BaseUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Rental")
class RentalTest extends BaseUnitTest {

    private Vehicle car;
    private Customer customer;
    private Agency pickupAgency;
    private Agency returnAgency;
    private LocalDateTime startDate;
    private LocalDateTime expectedReturnDate;
    private Rental rental;

    @BeforeEach
    void setUp() {
        car = VehicleFactory.createCarVehicle();
        customer = CustomerFactory.createCorporateCustomer();
        pickupAgency = AgencyFactory.createAgency();
        returnAgency = AgencyFactory.createAgency();
        startDate = LocalDateTime.of(2024, 12, 5, 14, 30);
        expectedReturnDate = LocalDateTime.of(2024, 12, 7, 12, 0);

        rental = new Rental(car, customer, pickupAgency, returnAgency, startDate, expectedReturnDate);
    }

    @Nested
    @DisplayName("Creation")
    class Creation {

        @Test
        @DisplayName("should create rental with valid data")
        void shouldCreateRentalWithValidData() {

            assertAll(
                    () -> assertNotNull(rental.getId()),
                    () -> assertNotNull(rental.getVehicle().getId()),
                    () -> assertNotNull(rental.getCustomer().getId()),
                    () -> assertNotNull(rental.getPickupAgency().getId()),
                    () -> assertNotNull(rental.getReturnAgency().getId()),
                    () -> assertEquals(BigDecimal.valueOf(150), rental.getDailyRate()),
                    () -> assertEquals(RentalStatus.ACTIVE, rental.getStatus())
            );
        }

        @Test
        @DisplayName("should not create with invalid data")
        void shouldNotCreateRentalWithInvalidData() {

            assertAll(
                    () -> assertThrows(DateTimeException.class,
                            () -> new Rental(car, customer, pickupAgency, returnAgency, startDate, LocalDateTime.of(2024, 13, 7, 14, 30))),
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> new Rental(null, customer, pickupAgency, returnAgency, startDate, expectedReturnDate)),
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> new Rental(car, null, pickupAgency, returnAgency, startDate, expectedReturnDate)),
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> new Rental(car, customer, null, returnAgency, startDate, expectedReturnDate)),
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> new Rental(car, customer, pickupAgency, null, startDate, expectedReturnDate))
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