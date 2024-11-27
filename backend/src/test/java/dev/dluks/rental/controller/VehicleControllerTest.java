package dev.dluks.rental.controller;

import dev.dluks.rental.model.vehicle.Vehicle;
import dev.dluks.rental.model.vehicle.VehicleStatus;
import dev.dluks.rental.model.vehicle.VehicleType;
import dev.dluks.rental.service.VehicleService;
import dev.dluks.rental.service.dto.CreateVehicleRequest;
import dev.dluks.rental.service.dto.VehicleResponseFull;
import dev.dluks.rental.support.BaseControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VehicleController.class)
@DisplayName("Vehicle Controller Tests")
class VehicleControllerTest extends BaseControllerTest {

    @MockitoBean
    private VehicleService vehicleService;

    private CreateVehicleRequest createCar;
    private Vehicle car;
    private Vehicle motorcycle;

    private VehicleResponseFull carResponse;
    private VehicleResponseFull motorcycleResponse;

    @BeforeEach
    void setUp() {
        createCar = new CreateVehicleRequest("ABC1234", "Honda Civic", VehicleType.CAR);

        car = new Vehicle("ABC1234", "Honda Civic", VehicleType.CAR);
        car.setStatus(VehicleStatus.AVAILABLE);
        car.setDailyRate(new BigDecimal("150.00"));

        motorcycle = new Vehicle("XYZ9876", "Honda CB500", VehicleType.MOTORCYCLE);
        motorcycle.setStatus(VehicleStatus.AVAILABLE);
        motorcycle.setDailyRate(new BigDecimal("100.00"));

        carResponse = VehicleResponseFull.from(car);
        motorcycleResponse = VehicleResponseFull.from(motorcycle);
    }

    @Test
    @DisplayName("Should create vehicle")
    void shouldCreateVehicle() throws Exception {
        // given
        given(vehicleService.createVehicle(any(CreateVehicleRequest.class))).willReturn(motorcycleResponse);

        // when/then
        mockMvc.perform(post("/api/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(createCar)))
                .andExpect(status().isCreated())
                .andDo(document("vehicle-create",
                        requestFields(
                                fieldWithPath("plate").description("Placa do veículo (formato ABC1234)"),
                                fieldWithPath("name").description("Nome do veículo"),
                                fieldWithPath("type").description("Tipo do veículo: CAR, MOTORCYCLE, TRUCK")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.STRING).description("ID único do veículo"),
                                fieldWithPath("plate").type(JsonFieldType.STRING).description("Placa do veículo"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("Nome do veículo"),
                                fieldWithPath("type").type(JsonFieldType.STRING).description("Tipo do veículo"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("Status atual: AVAILABLE, RENTED"),
                                fieldWithPath("dailyRate").type(JsonFieldType.NUMBER).description("Valor da diária"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("Data de criação"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("Data da última atualização")
                        )));
    }

    @Test
    @DisplayName("Should find vehicle by id")
    void shouldFindVehicleById() throws Exception {
        // given
        UUID id = UUID.randomUUID();
        given(vehicleService.findById(id)).willReturn(carResponse);

        // when/then
        mockMvc.perform(get("/api/vehicles/{id}", id))
                .andExpect(status().isOk())
                .andDo(document("vehicle-get-by-id",
                        pathParameters(
                                parameterWithName("id").description("ID do veículo")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.STRING).description("ID único do veículo"),
                                fieldWithPath("plate").type(JsonFieldType.STRING).description("Placa do veículo"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("Nome do veículo"),
                                fieldWithPath("type").type(JsonFieldType.STRING).description("Tipo do veículo"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("Status atual"),
                                fieldWithPath("dailyRate").type(JsonFieldType.NUMBER).description("Valor da diária"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("Data de criação"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("Data da última atualização")
                        )));
    }

    @Test
    @DisplayName("Should find vehicles by name")
    void shouldFindVehiclesByName() throws Exception {
        // given
        List<VehicleResponseFull> vehicles = List.of(carResponse, motorcycleResponse);

        given(vehicleService.findByName("Honda")).willReturn(vehicles);

        // when/then
        mockMvc.perform(get("/api/vehicles/search")
                        .param("name", "Honda"))
                .andExpect(status().isOk())
                .andDo(document("vehicle-search",
                        queryParameters(
                                parameterWithName("name").description("Nome ou parte do nome do veículo")
                        ),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.STRING).description("ID único do veículo"),
                                fieldWithPath("[].plate").type(JsonFieldType.STRING).description("Placa do veículo"),
                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("Nome do veículo"),
                                fieldWithPath("[].type").type(JsonFieldType.STRING).description("Tipo do veículo"),
                                fieldWithPath("[].status").type(JsonFieldType.STRING).description("Status atual"),
                                fieldWithPath("[].dailyRate").type(JsonFieldType.NUMBER).description("Valor da diária"),
                                fieldWithPath("[].createdAt").type(JsonFieldType.STRING).description("Data de criação"),
                                fieldWithPath("[].updatedAt").type(JsonFieldType.STRING).description("Data da última atualização")
                        )));
    }

    @Test
    @DisplayName("Should rent vehicle")
    void shouldRentVehicle() throws Exception {
        // given
        UUID id = UUID.randomUUID();

        // when/then
        mockMvc.perform(post("/api/vehicles/{id}/rent", id))
                .andExpect(status().isOk())
                .andDo(document("vehicle-rent",
                        pathParameters(
                                parameterWithName("id").description("ID do veículo a ser alugado")
                        )));

        verify(vehicleService).rent(id);
    }

    @Test
    @DisplayName("Should return vehicle")
    void shouldReturnVehicle() throws Exception {
        // given
        UUID id = UUID.randomUUID();

        // when/then
        mockMvc.perform(post("/api/vehicles/{id}/return", id))
                .andExpect(status().isOk())
                .andDo(document("vehicle-return",
                        pathParameters(
                                parameterWithName("id").description("ID do veículo a ser devolvido")
                        )));

        verify(vehicleService).returnVehicle(id);
    }
}