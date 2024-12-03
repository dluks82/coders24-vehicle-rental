package dev.dluks.rental.model.agency;

import dev.dluks.rental.model.address.Address;
import dev.dluks.rental.support.BaseUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Agency")
class AgencyTest extends BaseUnitTest {

    private Agency agency;
    private Address address;

    private final String name = "Agency Name";
    private final String document = "12345678000195";
    private final String phone = "1234567890";
    private final String email = "agency@email.com";

    @BeforeEach
    void setUp() {

        address = Address.builder()
                .street("Pine Street")
                .number("11")
                .complement("Penthouse")
                .neighborhood("Old Town")
                .city("Chicago")
                .state("IL")
                .zipCode("60614-111")
                .build();

        agency = Agency.builder()
                .name(name)
                .document(document)
                .phone(phone)
                .email(email)
                .address(address)
                .build();

    }

    @Nested
    @DisplayName("Creation")
    class Creation {

        @Test
        @DisplayName("should create agency with valid data")
        void shouldCreateAgencyWithValidData() {
            // given

            // then
            assertAll(
                    () -> assertNotNull(agency.getId()),
                    () -> assertEquals("Agency Name", agency.getName()),
                    () -> assertEquals("12345678000195", agency.getDocument()),
                    () -> assertEquals("1234567890", agency.getPhone())
            );
        }

        @Test
        @DisplayName("should not create agency with invalid document")
        void shouldNotCreateAgencyWithInvalidDocument() {
            // given
            String invalidDocument = "12345678000194";

            // then
            assertThrows(IllegalArgumentException.class,
                    () -> new Agency(
                            name,
                            invalidDocument,
                            phone,
                            email,
                            address
                    )
            );
        }

    }

    @Nested
    @DisplayName("Update")
    class Update {

        @Test
        @DisplayName("should update agency with valid data")
        void shouldUpdateAgencyWithValidData() {
            // given
            String newName = "New Agency Name";
            String newPhone = "9876543210";

            // when
            agency.setName(newName);
            agency.setPhone(newPhone);

            // then
            assertAll(
                    () -> assertEquals("New Agency Name", agency.getName()),
                    () -> assertEquals("9876543210", agency.getPhone())
            );
        }

        @Test
        @DisplayName("should not update agency with invalid document")
        void shouldNotUpdateAgencyWithInvalidDocument() {
            // given
            String invalidDocument = "12345678000194";

            // when & then
            assertThrows(IllegalArgumentException.class,
                    () -> agency.setDocument(invalidDocument)
            );
        }

        @Test
        @DisplayName("should not update agency if null name")
        void shouldNotUpdateAgencyIfNullName() {
            // when & then
            assertThrows(IllegalArgumentException.class,
                    () -> agency.setName(null)
            );
        }

        @Test
        @DisplayName("should not update agency if null document")
        void shouldNotUpdateAgencyIfNullDocument() {
            // when & then
            assertThrows(NullPointerException.class,
                    () -> agency.setDocument(null)
            );
        }

        @Test
        @DisplayName("should not update agency with invalid email")
        void shouldNotUpdateAgencyWithInvalidEmail() {
            // given
            String invalidEmail = "invalidemail.com";

            // when & then
            assertThrows(IllegalArgumentException.class,
                    () -> agency.setEmail(invalidEmail)
            );
        }
    }

}