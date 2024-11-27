package dev.dluks.rental.service.dto;

import dev.dluks.rental.model.vehicle.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreateVehicleRequest(
        @NotBlank
        @Pattern(regexp = "^[A-Z]{3}\\d{4}$", message = "Plate must be in the format ABC1234")
        String plate,

        @NotBlank
        String name,

        @NotNull
        VehicleType type
) {
}
