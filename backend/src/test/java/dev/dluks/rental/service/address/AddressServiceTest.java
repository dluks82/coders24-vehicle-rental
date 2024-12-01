package dev.dluks.rental.service.address;

import dev.dluks.rental.exception.AddressNotFoundException;
import dev.dluks.rental.model.address.Address;
import dev.dluks.rental.repository.AddressRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Address Service Tests")
@MockitoSettings(strictness = Strictness.LENIENT)
class AddressServiceTest extends BaseUnitTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService;

    private final String street = "Pine Street";
    private final String number = "11";
    private final String complement = "Penthouse";
    private final String neighborhood = "Old Town";
    private final String city = "Chicago";
    private final String state = "IL";
    private final String zipCode = "60614-111";

    private AddressRequestDTO addressRequestDTO;
    private Address address;

    @BeforeEach
    void setUp() {
        addressRequestDTO = AddressRequestDTO.builder()
                .street(street)
                .number(number)
                .complement(complement)
                .neighborhood(neighborhood)
                .city(city)
                .state(state)
                .zipCode(zipCode)
                .build();

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
    @DisplayName("Creation Tests")
    class Creation {

        @Test
        @DisplayName("Should save address")
        void shouldSaveAddress() {
            given(addressRepository.save(any(Address.class))).willReturn(address);
            Address createdAddress = addressService.saveAddress(addressRequestDTO);

            assertAll(
                    () -> assertThat(createdAddress).isNotNull(),
                    () -> assertThat(createdAddress.getStreet()).isEqualTo(street),
                    () -> assertThat(createdAddress.getNumber()).isEqualTo(number),
                    () -> assertThat(createdAddress.getComplement()).isEqualTo(complement),
                    () -> assertThat(createdAddress.getNeighborhood()).isEqualTo(neighborhood),
                    () -> assertThat(createdAddress.getCity()).isEqualTo(city),
                    () -> assertThat(createdAddress.getState()).isEqualTo(state),
                    () -> assertThat(createdAddress.getZipCode()).isEqualTo(zipCode),
                    () -> verify(addressRepository).save(any(Address.class))
            );
        }

        @Test
        @DisplayName("Should throw exception when null value has passed")
        void shouldThrowExceptionWhenNullValueHasPassed() {
            AddressRequestDTO addressRequestDTOWithNullValue = AddressRequestDTO.builder().build();
            assertThatThrownBy(() -> addressService.saveAddress(addressRequestDTOWithNullValue))
                    .isInstanceOf(IllegalArgumentException.class);
        }

    }

    @Nested
    @DisplayName("Search Tests")
    class Search {

        @Test
        @DisplayName("Should search address")
        void shouldSearchAddress() {
            UUID addressId = UUID.randomUUID();
            given(addressRepository.findById(addressId)).willReturn(Optional.of(address));

            Address addressFound = addressService.getAddressById(addressId);

            assertAll(
                    () -> assertThat(addressFound).isNotNull(),
                    () -> verify(addressRepository).findById(addressId)
            );
        }

        @Test
        @DisplayName("Should throw exception when address not found")
        void shouldThrowExceptionWhenAddressNotFound() {
            UUID nonExistentId = UUID.randomUUID();

            given(addressRepository.findById(nonExistentId)).willReturn(Optional.empty());

            assertThatThrownBy(() -> addressService.getAddressById(nonExistentId))
                    .isInstanceOf(AddressNotFoundException.class);
        }

    }

    @Nested
    @DisplayName("Delete Tests")
    class Delete {

        @Test
        @DisplayName("Should delete Address when id exists")
        void shouldDeleteAddressWhenIdExists() {
            UUID existingId = UUID.randomUUID();
            given(addressRepository.findById(existingId)).willReturn(Optional.of(address));

            addressService.deleteAddress(existingId);

            verify(addressRepository).delete(address);
        }

        @Test
        @DisplayName("Should throw exception when id does not exist")
        void shouldThrowExceptionWhenIdDoesNotExist() {
            UUID nonExistentId = UUID.randomUUID();

            assertThatThrownBy(() -> addressService.deleteAddress(nonExistentId))
                    .isInstanceOf(AddressNotFoundException.class);

            verify(addressRepository, never()).delete(any(Address.class));
        }

    }

    @Nested
    @DisplayName("Update Tests")
    class Update {

        private UpdateAddressRequestDTO updateAddressRequestDTO;

        private final String newStreet = "Highland Road";
        private final String newNumber = "321B";
        private final String newComplement = "Building A";
        private final String newNeighborhood = "Westwood";
        private final String newCity = "Seattle";
        private final String newState = "WA";
        private final String newZipCode = "54321-987";

        @BeforeEach
        void setUp() {
            AddressServiceTest.this.setUp();
            updateAddressRequestDTO = UpdateAddressRequestDTO.builder()
                    .street(newStreet)
                    .number(newNumber)
                    .complement(newComplement)
                    .neighborhood(newNeighborhood)
                    .city(newCity)
                    .state(newState)
                    .zipCode(newZipCode)
                    .build();
        }

        @Test
        @DisplayName("Should update address successfully")
        void shouldUpdateAddressSuccessfully() {
            UUID id = UUID.randomUUID();
            when(addressRepository.findById(id)).thenReturn(Optional.of(address));
            Address updatedAddress = addressService.updateAddress(id, updateAddressRequestDTO);

            assertAll(
                    () -> assertThat(updatedAddress.getStreet()).isEqualTo(newStreet),
                    () -> assertThat(updatedAddress.getNumber()).isEqualTo(newNumber),
                    () -> assertThat(updatedAddress.getComplement()).isEqualTo(newComplement),
                    () -> assertThat(updatedAddress.getNeighborhood()).isEqualTo(newNeighborhood),
                    () -> assertThat(updatedAddress.getCity()).isEqualTo(newCity),
                    () -> assertThat(updatedAddress.getState()).isEqualTo(newState),
                    () -> assertThat(updatedAddress.getZipCode()).isEqualTo(newZipCode),
                    () -> verify(addressRepository).save(address)
            );
        }

        @Test
        @DisplayName("Should throw exception when address does not exist")
        void shouldThrowExceptionWhenAddressDoesNotExist() {
            UUID nonExistentId = UUID.randomUUID();
            given(addressRepository.findById(nonExistentId)).willReturn(Optional.empty());

            assertThatThrownBy(() ->addressService.updateAddress(nonExistentId, updateAddressRequestDTO))
                    .isInstanceOf(AddressNotFoundException.class);

            verify(addressRepository, never()).save(any(Address.class));
        }

        @Test
        @DisplayName("Should not update fields when dto FieldsAreNull")
        void shouldNotUpdateFieldsWhenDtoFieldsAreNull() {
            UUID id = UUID.randomUUID();
            UpdateAddressRequestDTO updateDTO = UpdateAddressRequestDTO.builder()
                    .street(null)
                    .build();
            given(addressRepository.findById(id)).willReturn(Optional.of(address));

            Address updatedAddress = addressService.updateAddress(id, updateDTO);

            assertEquals(street, updatedAddress.getStreet());
            assertEquals(city, updatedAddress.getCity());
            verify(addressRepository).save(address);
        }

    }

}
