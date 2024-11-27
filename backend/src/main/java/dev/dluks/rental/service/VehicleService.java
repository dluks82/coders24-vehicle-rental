package dev.dluks.rental.service;

import dev.dluks.rental.model.vehicle.Vehicle;
import dev.dluks.rental.repository.VehicleRepository;
import dev.dluks.rental.service.dto.CreateVehicleRequest;
import dev.dluks.rental.service.dto.VehicleResponseFull;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    @Transactional
    public VehicleResponseFull createVehicle(CreateVehicleRequest request) {
        if (vehicleRepository.existsByPlate(request.plate())) {
            throw new IllegalArgumentException("Vehicle with plate " + request.plate() + " already exists");
        }
        Vehicle vehicle = Vehicle.builder()
                .plate(request.plate())
                .name(request.name())
                .type(request.type())
                .build();
        return VehicleResponseFull.from(vehicleRepository.save(vehicle));
    }

    @Transactional(readOnly = true)
    public VehicleResponseFull findByPlate(String plate) {
        Optional<Vehicle> vehicle = vehicleRepository.findByPlate(plate);
        if (vehicle.isEmpty()) {
            throw new EntityNotFoundException("Vehicle not found with plate: " + plate);
        }
        return VehicleResponseFull.from(vehicle.get());
    }

    @Transactional(readOnly = true)
    public VehicleResponseFull findById(UUID id) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);
        if (vehicle.isEmpty()) {
            throw new EntityNotFoundException("Vehicle not found with id: " + id);
        }
        return VehicleResponseFull.from(vehicle.get());
    }

    @Transactional(readOnly = true)
    public List<VehicleResponseFull> findByName(String name) {
        return vehicleRepository
                .findByNameContainingIgnoreCase(name).stream()
                .map(VehicleResponseFull::from)
                .toList();
    }

    @Transactional
    public void rent(UUID id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found with id: " + id));
        vehicle.rent();
        vehicleRepository.save(vehicle);
    }

    @Transactional
    public void returnVehicle(UUID id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found with id: " + id));
        vehicle.returnVehicle();
        vehicleRepository.save(vehicle);
    }
}