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
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

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
                    () -> assertEquals(0, rental.getDailyRate().compareTo(BigDecimal.valueOf(150))),
                    () -> assertEquals(RentalStatus.ACTIVE, rental.getStatus())
            );
        }

        @Test
        @DisplayName("should not create with invalid data")
        void shouldNotCreateRentalWithInvalidData() {
            assertThrows(DateTimeException.class,
                            () -> new Rental(
                                    car,
                                    customer,
                                    pickupAgency,
                                    returnAgency,
                                    startDate,
                                    LocalDateTime.of(2024, 13, 7, 14, 30)));
        }
    }

    @Nested
    @DisplayName("Operation")
    class Calculation {

        @Test
        @DisplayName("should complete a rental when valid data")
        void shouldCompleteRentalWhenValidData() {
            LocalDateTime actualReturnDate = LocalDateTime.of(2024, 12, 7, 14, 30);
            rental.complete(actualReturnDate);

            assertEquals(VehicleStatus.AVAILABLE, rental.getVehicle().getStatus());
            assertEquals(RentalStatus.COMPLETED, rental.getStatus());
            assertEquals(0, rental.getTotalAmount().compareTo(BigDecimal.valueOf(300)));
            assertEquals(0, rental.getDiscount().compareTo(BigDecimal.valueOf(0)));
        }

        @Test
        @DisplayName("should cancel a rental when asked to")
        void shouldCancelRental() {
            rental.cancel();
            assertEquals(RentalStatus.CANCELLED, rental.getStatus());
        }

        @Test
        @DisplayName("should print rental information correctly")
        void shouldPrintCorrectInformationAboutRental() {
            assertEquals("Rental{" +
                    "vehicle=" + rental.getVehicle() +
                    ", customer=" + rental.getCustomer() +
                    ", pickupAgency=" + rental.getPickupAgency() +
                    ", returnAgency=" + rental.getReturnAgency() +
                    ", startDate=" + rental.getStartDate() +
                    ", expectedReturnDate=" + rental.getExpectedReturnDate() +
                    ", actualReturnDate=" + rental.getActualReturnDate() +
                    ", status=" + rental.getStatus() +
                    ", dailyRate=" + rental.getDailyRate() +
                    ", totalAmount=" + rental.getTotalAmount() +
                    ", discount=" + rental.getDiscount() +
                    ", finalAmount=" + rental.getFinalAmount() +
                    ", id=" + rental.getId() +
                    ", createdAt=" + rental.getCreatedAt() +
                    ", updatedAt=" + rental.getUpdatedAt() +
                    '}', rental.toString());
        }
    }
}