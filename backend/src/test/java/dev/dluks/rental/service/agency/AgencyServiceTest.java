package dev.dluks.rental.service.agency;

import dev.dluks.rental.exception.AgencyNotFoundException;
import dev.dluks.rental.model.address.Address;
import dev.dluks.rental.model.agency.Agency;
import dev.dluks.rental.repository.AgencyRepository;
import dev.dluks.rental.service.address.AddressRequestDTO;
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

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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

    private AddressRequestDTO addressRequestDTO;
    private Address address;

    @BeforeEach
    void setUp() {

        addressRequestDTO = AddressRequestDTO.builder()
                .street("Pine Street")
                .number("11")
                .complement("Penthouse")
                .neighborhood("Old Town")
                .city("Chicago")
                .state("IL")
                .zipCode("60614-111")
                .build();

        address = Address.builder()
                .street(addressRequestDTO.getStreet())
                .number(addressRequestDTO.getNumber())
                .complement(addressRequestDTO.getComplement())
                .neighborhood(addressRequestDTO.getNeighborhood())
                .city(addressRequestDTO.getCity())
                .state(addressRequestDTO.getState())
                .zipCode(addressRequestDTO.getZipCode())
                .build();

        createAgencyRequest = CreateAgencyRequest.builder()
                .name("Agency Name")
                .document("12345678000195")
                .phone("1234567890")
                .email("agency@email.com")
                .addressRequestDTO(addressRequestDTO)
                .build();

        agency = Agency.builder()
                .name(createAgencyRequest.getName())
                .document(createAgencyRequest.getDocument())
                .phone(createAgencyRequest.getPhone())
                .email(createAgencyRequest.getEmail())
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
            given(agencyRepository.save(any(Agency.class))).willReturn(agency);

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

    @Nested
    @DisplayName("Search")
    class Search {

        @Test
        @DisplayName("Should find by document successfully")
        void shouldFindByDocumentSuccessfully() {
            String document = agency.getDocument();
            given(agencyRepository.findByDocument(document)).willReturn(Optional.of(agency));

            Agency result = agencyService.findByDocument(document);

            assertThat(agency).isEqualTo(result);
            verify(agencyRepository, times(1)).findByDocument(document);
        }

        @Test
        @DisplayName("Should throw exception when document not found")
        void shouldThrowExceptionWhenDocumentNotFound() {
            String document = "123456789";
            given(agencyRepository.findByDocument(document)).willReturn(Optional.empty());

            assertThatThrownBy(() -> agencyService.findByDocument(document))
                    .isInstanceOf(AgencyNotFoundException.class);

            verify(agencyRepository, times(1)).findByDocument(document);
        }

        @Test
        @DisplayName("Should find by name successfully")
        void shouldFindByNameSuccessfully() {
            String name = agency.getName();
            given(agencyRepository.findByNameIgnoreCase(name)).willReturn(Optional.of(agency));

            Agency result = agencyService.findByName(name);

            assertThat(agency).isEqualTo(result);
            verify(agencyRepository, times(1)).findByNameIgnoreCase(name);
        }

        @Test
        @DisplayName("Should throw exception when name not found")
        void shouldThrowExceptionWhenNameNotFound() {
            String name = "Agency Name";
            given(agencyRepository.findByNameIgnoreCase(name)).willReturn(Optional.empty());

            assertThatThrownBy(() -> agencyService.findByName(name))
                    .isInstanceOf(AgencyNotFoundException.class);

            verify(agencyRepository, times(1)).findByNameIgnoreCase(name);
        }

    }

    @Nested
    @DisplayName("Update")
    class Update {

        @Test
        @DisplayName("Should update agency successfully")
        void shouldUpdateAgencySuccessfully() {
            UUID agencyId = UUID.randomUUID();

            UpdateAgencyRequest updateRequest = UpdateAgencyRequest.builder()
                    .name("New Name")
                    .document("12345678000195")
                    .phone("New Phone")
                    .email("new@example.com")
                    .build();

            given(agencyRepository.findById(agencyId)).willReturn(Optional.of(agency));

            Agency updatedAgency = agencyService.updateAgency(agencyId, updateRequest);

            assertAll(() -> {
                assertThat(updatedAgency).isNotNull();
                assertThat(updatedAgency.getName()).isEqualTo("New Name");
                assertThat(updatedAgency.getDocument()).isEqualTo("12345678000195");
                assertThat(updatedAgency.getPhone()).isEqualTo("New Phone");
                assertThat(updatedAgency.getEmail()).isEqualTo("new@example.com");
            });

            verify(agencyRepository, times(1)).findById(agencyId);
            verify(agencyRepository, times(1)).save(agency);
        }

        @Test
        @DisplayName("Should throw exception when agency not found")
        void shouldThrowExceptionWhenAgencyNotFound() {
            UUID agencyId = UUID.randomUUID();
            UpdateAgencyRequest updateRequest = UpdateAgencyRequest.builder().build();
            given(agencyRepository.findById(agencyId)).willReturn(Optional.empty());

            assertThatThrownBy(() -> agencyService.updateAgency(agencyId, updateRequest))
                    .isInstanceOf(AgencyNotFoundException.class);

            verify(agencyRepository, times(1)).findById(agencyId);
            verify(agencyRepository, never()).save(any());
        }

        @Test
        @DisplayName("Should partially update the agency successfully")
        void shouldPartiallyUpdateTheAgencySuccessfully() {

            UUID agencyId = UUID.randomUUID();

            Agency existingAgency = Agency.builder()
                    .name("Old Name")
                    .document("12345678000195")
                    .phone("Old Phone")
                    .email("old@example.com")
                    .address(address)
                    .build();

            UpdateAgencyRequest updateRequest = UpdateAgencyRequest.builder()
                    .name("New Name")
                    .build();

            given(agencyRepository.findById(agencyId)).willReturn(Optional.of(existingAgency));

            Agency updatedAgency = agencyService.updateAgency(agencyId, updateRequest);

            assertAll(() -> {
                assertThat(updatedAgency).isNotNull();
                assertThat(updatedAgency.getName()).isEqualTo("New Name");
                assertThat(updatedAgency.getDocument()).isEqualTo("12345678000195");
                assertThat(updatedAgency.getPhone()).isEqualTo("Old Phone");
                assertThat(updatedAgency.getEmail()).isEqualTo("old@example.com");
            });

            verify(agencyRepository, times(1)).findById(agencyId);
            verify(agencyRepository, times(1)).save(existingAgency);

        }

    }

}