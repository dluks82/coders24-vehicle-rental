package dev.dluks.rental.model.vehicle;

import dev.dluks.rental.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.regex.Pattern;

@Entity
@Table(name = "vehicles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vehicle extends BaseEntity {

    private static final Pattern PLATE_PATTERN = Pattern.compile("^[A-Z]{3}\\d{4}$");

    @Column(nullable = false, unique = true, length = 7)
    private String plate;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleStatus status;

    @Column(name = "daily_rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal dailyRate;

    public Vehicle(String plate, String name, VehicleType type) {
        validadePlate(plate);

        this.plate = plate.toUpperCase();
        this.name = name;
        this.type = type;
        this.status = VehicleStatus.AVAILABLE;
        this.dailyRate = type.getDailyRate();
    }

    private void validadePlate(String plate) {
        if (plate == null || !PLATE_PATTERN.matcher(plate.toUpperCase()).matches()) {
            throw new IllegalArgumentException("Invalid plate format. Must be ABC1234");
        }
    }

    public void rent() {
        if (status == VehicleStatus.RENTED) {
            throw new IllegalStateException("Vehicle is already rented");
        }
        status = VehicleStatus.RENTED;
    }

    public void returnVehicle() {
        if (status == VehicleStatus.AVAILABLE) {
            throw new IllegalStateException("Vehicle is already available");
        }
        status = VehicleStatus.AVAILABLE;
    }

}