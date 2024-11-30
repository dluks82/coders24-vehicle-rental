package dev.dluks.rental.service.address;

import dev.dluks.rental.exception.AddressNotFoundException;
import dev.dluks.rental.model.address.Address;
import dev.dluks.rental.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public Address getAddressById(UUID id) {
        return addressRepository.findById(id)
            .orElseThrow(() -> new AddressNotFoundException(id.toString() + " not found."));
    }

    public Address saveAddress(AddressRequestDTO addressRequestDTO) {
        Address address = Address.builder()
                .street(addressRequestDTO.getStreet())
                .number(addressRequestDTO.getNumber())
                .complement(addressRequestDTO.getComplement())
                .neighborhood(addressRequestDTO.getNeighborhood())
                .city(addressRequestDTO.getCity())
                .state(addressRequestDTO.getState())
                .zipCode(addressRequestDTO.getZipCode())
                .build();
        return addressRepository.save(address);
    }

    public void deleteAddress(UUID id) {
        Address address = getAddressById(id);
        addressRepository.delete(address);
    }

    private void updateField(Consumer<String> setter, String value) {
        if (value != null && !value.isEmpty()) {
            setter.accept(value);
        }
    }

    public Address updateAddress(UUID id, UpdateAddressRequestDTO updateAddressRequestDTO) {
        Address address = getAddressById(id);

        updateField(address::setStreet, updateAddressRequestDTO.getStreet());
        updateField(address::setNumber, updateAddressRequestDTO.getNumber());
        updateField(address::setComplement, updateAddressRequestDTO.getComplement());
        updateField(address::setNeighborhood, updateAddressRequestDTO.getNeighborhood());
        updateField(address::setCity, updateAddressRequestDTO.getCity());
        updateField(address::setState, updateAddressRequestDTO.getState());
        updateField(address::setZipCode, updateAddressRequestDTO.getZipCode());

        addressRepository.save(address);

        return address;
    }

}
