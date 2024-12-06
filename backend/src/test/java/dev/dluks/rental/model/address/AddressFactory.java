package dev.dluks.rental.model.address;

public class AddressFactory {

    public static Address createAddress() {
        return Address.builder()
                .street("Pine Street")
                .number("11")
                .complement("Penthouse")
                .neighborhood("Old Town")
                .city("Chicago")
                .state("IL")
                .zipCode("60614-111")
                .build();
    }
}
