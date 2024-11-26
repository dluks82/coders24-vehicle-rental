package dev.dluks.rental.model.vehicle;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum VehicleType {
    CAR(new BigDecimal("150.00")),
    MOTORCYCLE(new BigDecimal("100.00")),
    TRUCK(new BigDecimal("200.00"));

    private final BigDecimal dailyRate;

    VehicleType(BigDecimal dailyRate) {
        this.dailyRate = dailyRate;
    }
}
