package dev.dluks.rental.service.vehicle;

import dev.dluks.rental.model.vehicle.Vehicle;
import dev.dluks.rental.repository.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public VehicleResponseFull createVehicle(CreateVehicleRequest dto) {
        if (vehicleRepository.existsByPlate(dto.getPlate())) {
            throw new IllegalArgumentException("Vehicle with plate " + dto.getPlate() + " already exists");
        }
        Vehicle vehicle = Vehicle.builder()
                .plate(dto.getPlate())
                .name(dto.getName())
                .type(dto.getType())
                .build();
        return VehicleResponseFull.from(vehicleRepository.save(vehicle));
    }

    @Transactional(readOnly = true)
    public Page<VehicleResponseFull> findAll(Pageable pageable) {
        Page<Vehicle> vehicles = vehicleRepository.findAll(pageable);

        return new PageImpl<>(vehicles.getContent().stream()
                .map(VehicleResponseFull::from)
                .toList(), vehicles.getPageable(), vehicles.getTotalElements());
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