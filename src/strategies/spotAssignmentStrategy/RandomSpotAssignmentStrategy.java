package strategies.spotAssignmentStrategy;

import models.Gate;
import models.ParkingSpot;
import models.enums.VehicleType;

public class RandomSpotAssignmentStrategy implements SpotAssignmentStrategy{

    @Override
    public ParkingSpot assignSpot(VehicleType vehicleType, Gate gate) {

        ParkingSpot spot = new ParkingSpot();

        int randomSpot = 100 + (int)(Math.random() * 900);

        spot.setParkingSpotNumber("P-" + randomSpot);

        return spot;
    }
}
