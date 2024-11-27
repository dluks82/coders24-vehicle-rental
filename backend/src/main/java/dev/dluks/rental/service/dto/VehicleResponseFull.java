package dev.dluks.rental.service.dto;

import dev.dluks.rental.model.vehicle.Vehicle;
import dev.dluks.rental.model.vehicle.VehicleStatus;
import dev.dluks.rental.model.vehicle.VehicleType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class VehicleResponseFull {

    private String id;
    private String plate;
    private String name;
    private VehicleType type;
    private VehicleStatus status;
    private BigDecimal dailyRate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static VehicleResponseFull from(Vehicle vehicle) {
        return VehicleResponseFull.builder()
                .id(vehicle.getId().toString())
                .plate(vehicle.getPlate())
                .name(vehicle.getName())
                .type(vehicle.getType())
                .status(vehicle.getStatus())
                .dailyRate(vehicle.getDailyRate())
                .createdAt(vehicle.getCreatedAt())
                .updatedAt(vehicle.getUpdatedAt())
                .build();
    }

}
