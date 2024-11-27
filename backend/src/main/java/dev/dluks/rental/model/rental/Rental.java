package dev.dluks.rental.model.rental;

import dev.dluks.rental.model.agency.Agency;
import dev.dluks.rental.model.base.BaseEntity;
import dev.dluks.rental.model.customer.Customer;
import dev.dluks.rental.model.vehicle.Vehicle;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "rentals")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Rental extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "pickup_agency_id")
    private Agency pickupAgency;

    @ManyToOne
    @JoinColumn(name = "return_agency_id")
    private Agency returnAgency;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "expected_return_date", nullable = false)
    private LocalDateTime expectedReturnDate;

    @Column(name = "actual_return_date")
    private LocalDateTime actualReturnDate;

    @Enumerated(EnumType.STRING)
    private RentalStatus status;

    @Column(name = "daily_rate", nullable = false)
    private BigDecimal dailyRate;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "final_amount")
    private BigDecimal finalAmount;

    public Rental(Vehicle vehicle, Customer customer,
                  Agency pickupAgency, Agency returnAgency,
                  LocalDateTime startDate,
                  LocalDateTime expectedReturnDate) {
        super();

        this.vehicle = vehicle;
        this.customer = customer;
        this.pickupAgency = pickupAgency;
        this.returnAgency = returnAgency;
        this.startDate = startDate;
        this.expectedReturnDate = expectedReturnDate;
        this.status = RentalStatus.ACTIVE;

        this.dailyRate = vehicle.getDailyRate();
    }


    public void complete(LocalDateTime actualReturnDate) {
        if (status != RentalStatus.ACTIVE) {
            throw new IllegalStateException("Rental is not active");
        }
        this.actualReturnDate = actualReturnDate != null ? actualReturnDate : LocalDateTime.now();

        this.totalAmount = calculateTotalAmount();
        this.discount = calculateDiscount(totalAmount);
        this.finalAmount = totalAmount.subtract(discount);
        this.status = RentalStatus.COMPLETED;
    }

    public void cancel() {
        if (status != RentalStatus.ACTIVE) {
            throw new IllegalStateException("Rental is not active");
        }
        this.status = RentalStatus.CANCELLED;
    }

    private BigDecimal calculateDiscount(BigDecimal totalAmount) {
        return totalAmount.multiply(BigDecimal.valueOf(0.0));
    }

    private BigDecimal calculateTotalAmount() {
        long days = startDate.until(actualReturnDate, java.time.temporal.ChronoUnit.DAYS);
        return dailyRate.multiply(BigDecimal.valueOf(days));
    }

    @Override
    public String toString() {
        return "Rental{" +
               "vehicle=" + vehicle +
               ", customer=" + customer +
               ", pickupAgency=" + pickupAgency +
               ", returnAgency=" + returnAgency +
               ", startDate=" + startDate +
               ", expectedReturnDate=" + expectedReturnDate +
               ", actualReturnDate=" + actualReturnDate +
               ", status=" + status +
               ", dailyRate=" + dailyRate +
               ", totalAmount=" + totalAmount +
               ", discount=" + discount +
               ", finalAmount=" + finalAmount +
               ", id=" + id +
               ", createdAt=" + createdAt +
               ", updatedAt=" + updatedAt +
               '}';
    }
}
