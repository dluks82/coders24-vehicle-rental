package dev.dluks.rental.controller;

import dev.dluks.rental.service.vehicle.VehicleService;
import dev.dluks.rental.service.vehicle.CreateVehicleRequest;
import dev.dluks.rental.service.vehicle.VehicleResponseFull;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<VehicleResponseFull> createVehicle(
            @RequestBody @Valid CreateVehicleRequest request,
            UriComponentsBuilder uriBuilder) {
        VehicleResponseFull vehicle = vehicleService.createVehicle(request);
        URI uri = uriBuilder.path("/api/vehicles/{id}")
                .buildAndExpand(vehicle.getId())
                .toUri();
        return ResponseEntity.created(uri).body(vehicle);
    }

    @GetMapping
    public ResponseEntity<Page<VehicleResponseFull>> findAll(
            @RequestParam(required = false, defaultValue = "name") String sort,
            @PageableDefault(
                    size = 10,
                    direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        return ResponseEntity.ok(vehicleService.findAll(
                PageRequest.of(pageable.getPageNumber(),
                        pageable.getPageSize(), Sort.by(sort))
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseFull> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(vehicleService.findById(id));
    }

    @GetMapping("/plate/{plate}")
    public ResponseEntity<VehicleResponseFull> findByPlate(@PathVariable String plate) {
        return ResponseEntity.ok(vehicleService.findByPlate(plate));
    }

    @GetMapping("/search")
    public ResponseEntity<List<VehicleResponseFull>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(vehicleService.findByName(name));
    }

    @PostMapping("/{id}/rent")
    public ResponseEntity<Void> rentVehicle(@PathVariable UUID id) {
        vehicleService.rent(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<Void> returnVehicle(@PathVariable UUID id) {
        vehicleService.returnVehicle(id);
        return ResponseEntity.ok().build();
    }
}