package dev.dluks.rental.model.agency;

import dev.dluks.rental.model.address.AddressFactory;

public class AgencyFactory {

    public static Agency createAgency() {
        return Agency.builder()
                .name("Agency Name")
                .document("12345678000195")
                .phone("1234567890")
                .email("agency@email.com")
                .address(AddressFactory.createAddress())
                .build();
    }
}
