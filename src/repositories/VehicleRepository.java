package repositories;

import models.Vehicle;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class VehicleRepository {

    private Map<Long, Vehicle> vehicleMap;
    private Map<String, Vehicle> vehicleNumberVehicleMap;
    private Long id;

    public VehicleRepository() {
        vehicleMap = new HashMap<>();
        vehicleNumberVehicleMap = new HashMap<>();
        id = 0L;
    }

    public Optional<Vehicle> findByVehicleNumber(String vehicleNumber) {
        if(vehicleNumberVehicleMap.containsKey(vehicleNumber)) {
            return Optional.of(vehicleNumberVehicleMap.get(vehicleNumber));
        }
        return Optional.empty();
    }

    public Optional<Vehicle> findById(Long vehicleId) {
        if(vehicleMap.containsKey(vehicleId)) {
            return Optional.of(vehicleMap.get(vehicleId));
        }
        return Optional.empty();
    }

    public Vehicle save(Vehicle vehicle) {

        if (vehicle.getId() == null || vehicle.getId() == 0) {
            id++;
            vehicle.setId(id);
        }

        vehicleMap.put(vehicle.getId(), vehicle);
        vehicleNumberVehicleMap.put(vehicle.getVehicleNumber(), vehicle);

        return vehicle;
    }

}
