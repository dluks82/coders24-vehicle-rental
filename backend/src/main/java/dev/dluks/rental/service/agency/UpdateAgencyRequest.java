package dev.dluks.rental.service.agency;

import dev.dluks.rental.service.address.UpdateAddressRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateAgencyRequest {

    private String name;
    private String document;
    private String phone;

    @Email(message = "Invalid email")
    private String email;

    @Valid
    private UpdateAddressRequestDTO updateAddress;

}
