package dev.dluks.rental.model.address;

import dev.dluks.rental.support.BaseUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Address")
class AddressTest extends BaseUnitTest {

    private Address address;

    private final String street = "Pine Street";
    private final String number = "11";
    private final String complement = "Penthouse";
    private final String neighborhood = "Old Town";
    private final String city = "Chicago";
    private final String state = "IL";
    private final String zipCode = "60614-111";

    @BeforeEach
    void setUp() {
        address = Address.builder()
            .street(street)
            .number(number)
            .complement(complement)
            .neighborhood(neighborhood)
            .city(city)
            .state(state)
            .zipCode(zipCode)
            .build();
    }

    @Nested
    @DisplayName("Creation")
    class Creation {

        @Test
        @DisplayName("Must validate if it allows creating class with null data with builder")
        void mustValidateIfItAllowsCreatingClassWithNullDataWithBuilder() {
            assertThrows(IllegalArgumentException.class, Address.builder()::build);
        }

        @Test
        @DisplayName("Must validate if it allows creating class with null data with Constructor")
        void mustValidateIfItAllowsCreatingClassWithNullDataWithConstructor() {
            assertThrows(IllegalArgumentException.class, Address::new);
        }

        @Test
        @DisplayName("Should create Address with valid data")
        void shouldCreateAddressWithValidData() {
            assertAll(
                    () -> assertNotNull(address.getId()),
                    () -> assertEquals(street, address.getStreet()),
                    () -> assertEquals(number, address.getNumber()),
                    () -> assertEquals(complement, address.getComplement()),
                    () -> assertEquals(neighborhood, address.getNeighborhood()),
                    () -> assertEquals(city, address.getCity()),
                    () -> assertEquals(state, address.getState()),
                    () -> assertEquals(zipCode, address.getZipCode())
            );
        }

    }

    @Nested
    @DisplayName("Update")
    class Update {

        @Test
        @DisplayName("Should update Address with valid data")
        void shouldUpdateAddressWithValidData() {

            String newStreet = "Highland Road";
            String newNumber = "321B";
            String newComplement = "Building A";
            String newNeighborhood = "Westwood";
            String newCity = "Seattle";
            String newState = "WA";
            String newZipCode = "54321-987";

            address.setStreet(newStreet);
            address.setNumber(newNumber);
            address.setComplement(newComplement);
            address.setNeighborhood(newNeighborhood);
            address.setCity(newCity);
            address.setState(newState);
            address.setZipCode(newZipCode);

            assertAll(
                    () -> assertNotNull(address.getId()),
                    () -> assertEquals(newStreet, address.getStreet()),
                    () -> assertEquals(newNumber, address.getNumber()),
                    () -> assertEquals(newComplement, address.getComplement()),
                    () -> assertEquals(newNeighborhood, address.getNeighborhood()),
                    () -> assertEquals(newCity, address.getCity()),
                    () -> assertEquals(newState, address.getState()),
                    () -> assertEquals(newZipCode, address.getZipCode())
            );

        }

        @Test
        @DisplayName("Should not update Address with null value")
        void shouldNotUpdateAddressWithNullValue() {
            assertAll(
                    () -> assertThrows(IllegalArgumentException.class, () -> address.setStreet(null)),
                    () -> assertThrows(IllegalArgumentException.class, () -> address.setNumber(null)),
                    () -> assertThrows(IllegalArgumentException.class, () -> address.setNeighborhood(null)),
                    () -> assertThrows(IllegalArgumentException.class, () -> address.setCity(null)),
                    () -> assertThrows(IllegalArgumentException.class, () -> address.setState(null)),
                    () -> assertThrows(IllegalArgumentException.class, () -> address.setZipCode(null))
            );
        }

        @Test
        @DisplayName("should Update Address With Null Value in complement attribute")
        void shouldUpdateAddressWithNullValueInComplementAttribute() {
            assertDoesNotThrow(() -> address.setComplement(null));
        }

    }

}
