package repositories;

import models.Gate;
import models.ParkingLot;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ParkingLotRepository {

    private Map<Long, ParkingLot> parkingLotMap;
    private Long id;

    public ParkingLotRepository() {
        parkingLotMap = new HashMap<>();
        id = 0L;
    }

    public ParkingLot save(ParkingLot parkingLot) {
        if (parkingLot.getId() == null || parkingLot.getId() == 0) {
            id++;
            parkingLot.setId(id);
        }

        parkingLotMap.put(parkingLot.getId(), parkingLot);

        return parkingLot;
    }

    public Optional<ParkingLot> findParkingLotByGateId(Long gateId) {

        for (ParkingLot parkingLot : parkingLotMap.values()) {

            for (Gate gate : parkingLot.getGates()) {

                if (gate.getId().equals(gateId)) {
                    return Optional.of(parkingLot);
                }

            }
        }

        return Optional.empty();
    }
}