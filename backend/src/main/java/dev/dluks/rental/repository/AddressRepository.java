package dev.dluks.rental.repository;


import dev.dluks.rental.model.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {

    Optional<Address> findAddressByZipCode(String zipCode);

}
