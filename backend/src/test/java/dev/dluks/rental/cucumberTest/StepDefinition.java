//package dev.dluks.rental.cucumberTest;
//
//
//
//import dev.dluks.rental.model.vehicle.VehicleType;
//import dev.dluks.rental.service.vehicle.CreateVehicleRequest;
//import dev.dluks.rental.service.vehicle.VehicleService;
//
//import dev.dluks.rental.support.BaseIntegrationTest;
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//import io.cucumber.spring.CucumberContextConfiguration;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//
//
//
//@CucumberContextConfiguration
//public class StepDefinition extends BaseIntegrationTest {
//
//    @Autowired
//    private VehicleService vehicleService;
//
//    private CreateVehicleRequest createCar;
//
//
//    @Given("O banco de dados deve estar vazio")
//    public void o_banco_de_dados_deve_estar_vazio() {
//        //cleanDatabase();
//    }
//
//
//    @When("Um carro com placa ABC1234, nome Honda Civic e tipo CAR deve ser criado")
//    public void um_carro_com_placa_abc1234_nome_honda_civic_e_tipo_car_deve_ser_criado() {
//        createCar = CreateVehicleRequest.builder()
//                .plate("ABC1234")
//                .name("Honda Civic")
//                .type(VehicleType.CAR)
//                .build();
//
//        vehicleService.createVehicle(createCar);
//    }
//
//    @Then("Um carro com placa ABC1234, nome Honda Civic e tipo CAR deve ser encontrado")
//    public void um_carro_com_placa_abc1234_nome_honda_civic_e_tipo_car_deve_ser_encontrado() {
//       var vehicle = vehicleService.findByPlate("ABC1234");
//
//       assertNotNull(vehicle);
//       assertEquals("ABC1234", vehicle.getPlate());
//       assertEquals("Honda Civic", vehicle.getName());
//       assertEquals(VehicleType.CAR, vehicle.getType());
//    }
//
//
//}
