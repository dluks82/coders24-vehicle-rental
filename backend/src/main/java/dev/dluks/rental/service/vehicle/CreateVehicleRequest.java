package dev.dluks.rental.service.vehicle;

import dev.dluks.rental.model.vehicle.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateVehicleRequest {

    @NotBlank(message = "Plate is required")
    @Pattern(regexp = "^[A-Z]{3}\\d{4}$", message = "Plate must be in the format ABC1234")
    private String plate;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Type is required")
    private VehicleType type;

}
