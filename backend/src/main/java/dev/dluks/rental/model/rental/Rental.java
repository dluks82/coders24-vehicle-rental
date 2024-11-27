package dev.dluks.rental.model.rental;

import dev.dluks.rental.model.agency.Agency;
import dev.dluks.rental.model.base.BaseEntity;
import dev.dluks.rental.model.customer.Customer;
import dev.dluks.rental.model.vehicle.Vehicle;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Rental extends BaseEntity {

    private Vehicle vehicle;
    private Customer customer;
    private Agency pickupAgency;
    private Agency returnAgency;
    private LocalDateTime startDate;
    private LocalDateTime expectedReturnDate;
    private LocalDateTime actualReturnDate;
    private RentalStatus status;
    private BigDecimal dailyRate;
    private BigDecimal totalAmount;
    private BigDecimal discount;
    private BigDecimal finalAmount;

    public void complete() {
    }

    public void cancel() {
    }

    private BigDecimal calculateDiscount() {
        return null;
    }

    private BigDecimal calculateTotalAmount() {
        return null;
    }

}
