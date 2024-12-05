package dev.dluks.rental.service;

import dev.dluks.rental.model.vehicle.VehicleType;
import dev.dluks.rental.service.vehicle.CreateVehicleRequest;
import dev.dluks.rental.service.vehicle.VehicleResponseFull;
import dev.dluks.rental.service.vehicle.VehicleService;
import dev.dluks.rental.support.BaseIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Vehicle Service Integration Tests")
class VehicleServiceIT extends BaseIntegrationTest {

    @Autowired
    private VehicleService vehicleService;

    private CreateVehicleRequest createCar;

    @BeforeEach
    void setUp() {
        cleanDatabase();

        createCar = CreateVehicleRequest.builder()
                .plate("ABC1234")
                .name("Honda Civic")
                .type(VehicleType.CAR)
                .build();
    }

    @Test
    @DisplayName("should create vehicle successfully")
    void shouldCreateVehicle() {
        // when
        VehicleResponseFull created = vehicleService.createVehicle(createCar);

        // then
        assertThat(created).isNotNull();
        assertThat(created.getId()).isNotNull();
        assertThat(created.getPlate()).isEqualTo("ABC1234");
        assertThat(created.getName()).isEqualTo("Honda Civic");
        assertThat(created.getType()).isEqualTo(VehicleType.CAR);
    }

    @Test
    @DisplayName("should throw exception when plate already exists")
    void shouldThrowExceptionWhenPlateExists() {
        // given
        vehicleService.createVehicle(createCar);

        // when/then
        assertThatThrownBy(() -> vehicleService.createVehicle(createCar))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Vehicle with plate ABC1234 already exists");
    }
}