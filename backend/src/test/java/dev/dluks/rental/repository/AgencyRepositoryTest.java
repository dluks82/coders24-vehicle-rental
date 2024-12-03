package dev.dluks.rental.repository;

import dev.dluks.rental.model.address.Address;
import dev.dluks.rental.model.agency.Agency;
import dev.dluks.rental.support.BaseRepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Agency Repository Tests")
class AgencyRepositoryTest  extends BaseRepositoryTest {

    @Autowired
    AgencyRepository agencyRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    private TestEntityManager entityManager;

    private final String name = "Agency Name";
    private final String document = "12345678000195";
    private final String phone = "1234567890";
    private final String email = "agency@email.com";

    private Address address;
    private Agency agency;

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

        Address savedAddress =  addressRepository.save(address);

        agency = Agency.builder()
                .name(name)
                .document(document)
                .phone(phone)
                .email(email)
                .address(savedAddress)
                .build();
    }

    @Test
    @DisplayName("Should save agency")
    void shouldSaveAgency() {
        Agency savedAgency = agencyRepository.save(agency);
        entityManager.flush();
        entityManager.clear();

        Agency foundAgency = entityManager.find(Agency.class, savedAgency.getId());

        assertAll(() -> {
            assertThat(foundAgency).isNotNull();
            assertThat(foundAgency.getName()).isEqualTo(name);
            assertThat(foundAgency.getDocument()).isEqualTo(document);
            assertThat(foundAgency.getPhone()).isEqualTo(phone);
            assertThat(foundAgency.getEmail()).isEqualTo(email);
        });
    }

    @Test
    @DisplayName("Should find agency by id")
    void shouldFindAgencyById() {
        Agency savedAgency = agencyRepository.save(agency);
        entityManager.persist(savedAgency);
        entityManager.flush();

        Optional<Agency> foundAgency = agencyRepository.findById(savedAgency.getId());

        assertThat(foundAgency).isPresent();
        assertThat(foundAgency.get().getId()).isEqualTo(savedAgency.getId());
    }

    @Test
    @DisplayName("Should find agency by name")
    void shouldFindAgencyByName() {
        Agency savedAgency = agencyRepository.save(agency);
        entityManager.persist(savedAgency);
        entityManager.flush();

        Optional<Agency> foundAgency = agencyRepository.findByNameIgnoreCase(savedAgency.getName());

        assertThat(foundAgency).isPresent();
        assertThat(foundAgency.get().getName()).isEqualTo(savedAgency.getName());
    }

    @Test
    @DisplayName("Should find agency by name ignore case")
    void shouldFindAgencyByNameIgnoreCase() {
        Agency savedAgency = agencyRepository.save(agency);
        entityManager.persist(savedAgency);
        entityManager.flush();

        String upperName = savedAgency.getName().toUpperCase();
        savedAgency.setName(upperName);

        Optional<Agency> foundAgency = agencyRepository.findByNameIgnoreCase(upperName);

        assertThat(foundAgency).isPresent();
        assertThat(foundAgency.get().getName()).isEqualTo(savedAgency.getName());
    }

    @Test
    @DisplayName("Should find agency by document")
    void shouldFindAgencyByDocument() {
        Agency savedAgency = agencyRepository.save(agency);
        entityManager.persist(savedAgency);
        entityManager.flush();

        Optional<Agency> foundAgency = agencyRepository.findByDocument(savedAgency.getDocument());

        assertThat(foundAgency).isPresent();
        assertThat(foundAgency.get().getDocument()).isEqualTo(savedAgency.getDocument());
    }

}
