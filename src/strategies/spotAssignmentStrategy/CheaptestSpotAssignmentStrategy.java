package strategies.spotAssignmentStrategy;

import models.Gate;
import models.ParkingSpot;
import models.enums.VehicleType;

public class CheaptestSpotAssignmentStrategy implements SpotAssignmentStrategy{

    @Override
    public ParkingSpot assignSpot(VehicleType vehicleType, Gate gate) {

        ParkingSpot spot = new ParkingSpot();
        spot.setParkingSpotNumber("P-101");

        return spot;
    }
}
