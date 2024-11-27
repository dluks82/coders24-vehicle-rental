package dev.dluks.rental.controller;

import dev.dluks.rental.service.address.AddressRequestDTO;
import dev.dluks.rental.service.address.UpdateAddressRequestDTO;
import dev.dluks.rental.model.address.Address;
import dev.dluks.rental.service.address.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping("")
    public ResponseEntity<List<Address>> getAllAddress() {
        return ResponseEntity.ok(addressService.getAllAddress());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable UUID id) {
        return ResponseEntity.ok(addressService.getAddressById(id));
    }

    @PostMapping("")
    public ResponseEntity<Address> saveAddress(@RequestBody AddressRequestDTO address) {
        Address savedAddress = addressService.saveAddress(address);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedAddress.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedAddress);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable UUID id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable UUID id, @RequestBody UpdateAddressRequestDTO address) {
        return ResponseEntity.ok(addressService.updateAddress(id, address));
    }

}
