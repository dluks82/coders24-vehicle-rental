package dev.dluks.rental.model.vehicle;

public class VehicleFactory {

    public static Vehicle createCarVehicle() {
        return Vehicle.builder()
                .plate("ABC1234")
                .name("Civic")
                .type(VehicleType.CAR)
                .build();
    }

    public static Vehicle createMotorcycleVehicle() {
        return Vehicle.builder()
                .plate("XYZ9876")
                .name("Harley Davidson")
                .type(VehicleType.MOTORCYCLE)
                .build();
    }

    public static Vehicle createTruckVehicle() {
        return Vehicle.builder()
                .plate("DEF5678")
                .name("Volvo")
                .type(VehicleType.TRUCK)
                .build();
    }
}
