package strategies.spotAssignmentStrategy;

import models.Gate;
import models.ParkingSpot;
import models.enums.VehicleType;

public interface SpotAssignmentStrategy {
    public ParkingSpot assignSpot(VehicleType vehicleType, Gate gate);
}
