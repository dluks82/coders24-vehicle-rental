package dev.dluks.rental.service.address;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class UpdateAddressRequestDTO {

    @Size(max = 200, message = "Street cannot exceed 200 characters.")
    private String street;

    @Size(max = 20, message = "Number cannot exceed 20 characters.")
    private String number;

    @Size(max = 100, message = "Complement cannot exceed 100 characters.")
    private String complement;

    @Size(max = 100, message = "Neighborhood cannot exceed 100 characters.")
    private String neighborhood;

    @Size(max = 100, message = "City cannot exceed 100 characters.")
    private String city;

    @Pattern(regexp = "^[A-Z]{2}$", message = "State must be a valid two-letter abbreviation.")
    private String state;

}
