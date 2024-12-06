package dev.dluks.rental.model.agency;

import dev.dluks.rental.support.BaseUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Agency")
class AgencyTest extends BaseUnitTest {

    private Agency agency;

    @BeforeEach
    void setUp() {
        agency = AgencyFactory.createAgency();
    }

    @Nested
    @DisplayName("Creation")
    class Creation {

        @Test
        @DisplayName("should create agency with valid data")
        void shouldCreateAgencyWithValidData() {
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
                            agency.getName(),
                            invalidDocument,
                            agency.getPhone(),
                            agency.getEmail(),
                            agency.getAddress()
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