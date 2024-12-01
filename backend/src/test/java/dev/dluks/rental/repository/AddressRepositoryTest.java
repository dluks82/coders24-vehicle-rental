package dev.dluks.rental.repository;

import dev.dluks.rental.model.address.Address;
import dev.dluks.rental.support.BaseRepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Address Repository Tests")
class AddressRepositoryTest extends BaseRepositoryTest {


    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private TestEntityManager entityManager;

    private final String street = "Pine Street";
    private final String number = "11";
    private final String complement = "Penthouse";
    private final String neighborhood = "Old Town";
    private final String city = "Chicago";
    private final String state = "IL";
    private final String zipCode = "60614-111";

    Address address;

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

    @Test
    @DisplayName("Should save address")
    void shouldSaveAddress() {
        Address savedAddress = addressRepository.save(address);
        entityManager.flush();
        entityManager.clear();

        Address foundAddress = entityManager.find(Address.class, savedAddress.getId());

        assertAll(
                () -> assertThat(foundAddress).isNotNull(),
                () -> assertThat(foundAddress.getStreet()).isEqualTo(street),
                () -> assertThat(foundAddress.getNumber()).isEqualTo(number),
                () -> assertThat(foundAddress.getComplement()).isEqualTo(complement),
                () -> assertThat(foundAddress.getNeighborhood()).isEqualTo(neighborhood),
                () -> assertThat(foundAddress.getCity()).isEqualTo(city),
                () -> assertThat(foundAddress.getState()).isEqualTo(state),
                () -> assertThat(foundAddress.getZipCode()).isEqualTo(zipCode)
        );
    }

    @Test
    @DisplayName("Should find address by id")
    void shouldFindAddressById() {
        Address savedAddress = addressRepository.save(address);
        entityManager.persist(savedAddress);
        entityManager.flush();

        Optional<Address> addressFound = addressRepository.findById(savedAddress.getId());

        assertThat(addressFound).isPresent();
        assertThat(addressFound.get().getId()).isEqualTo(savedAddress.getId());
    }

}