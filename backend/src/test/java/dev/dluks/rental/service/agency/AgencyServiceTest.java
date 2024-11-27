package dev.dluks.rental.service.agency;

import dev.dluks.rental.model.agency.Agency;
import dev.dluks.rental.repository.AgencyRepository;
import dev.dluks.rental.support.BaseUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Agency Service")
@MockitoSettings(strictness = Strictness.LENIENT)
class AgencyServiceTest extends BaseUnitTest {

    @Mock
    private AgencyRepository agencyRepository;

    @InjectMocks
    private AgencyService agencyService;

    private CreateAgencyRequest createAgencyRequest;

    private Agency agency;


    @BeforeEach
    void setUp() {

        createAgencyRequest = CreateAgencyRequest.builder()
                .name("Agency Name")
                .document("12345678000195")
                .phone("1234567890")
                .email("agency@email.com")
                .build();

        agency = Agency.builder()
                .name(createAgencyRequest.getName())
                .document(createAgencyRequest.getDocument())
                .phone(createAgencyRequest.getPhone())
                .email(createAgencyRequest.getEmail())
                .build();
    }

    @Nested
    @DisplayName("Creation")
    class Creation {

        @Test
        @DisplayName("should create agency with valid data")
        void shouldCreateAgencyWithValidData() {
            // given
            given(agencyRepository.save(
                    any(Agency.class))).willReturn(agency);

            // when
            Agency createdAgency = agencyService.create(createAgencyRequest);

            // then
            assertAll(
                    () -> assertNotNull(createdAgency.getId()),
                    () -> assertEquals("Agency Name", createdAgency.getName()),
                    () -> assertEquals("12345678000195", createdAgency.getDocument()),
                    () -> assertEquals("1234567890", createdAgency.getPhone())
            );
            verify(agencyRepository).save(any(Agency.class));
        }

        @Test
        @DisplayName("should not create agency with invalid document")
        void shouldNotCreateAgencyWithInvalidDocument() {
            // given
            String invalidDocument = "12345678000194";
            createAgencyRequest.setDocument(invalidDocument);

            // then
            assertThrows(IllegalArgumentException.class,
                    () -> agencyService.create(createAgencyRequest)
            );
            verify(agencyRepository, never()).save(any(Agency.class));
        }

        @Test
        @DisplayName("should not create agency with invalid email")
        void shouldNotCreateAgencyWithInvalidEmail() {
            // given
            String invalidEmail = "agencyemail.com";
            createAgencyRequest.setEmail(invalidEmail);

            // then
            assertThrows(IllegalArgumentException.class,
                    () -> agencyService.create(createAgencyRequest)
            );
            verify(agencyRepository, never()).save(any(Agency.class));
        }
    }

}