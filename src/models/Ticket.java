package models;

import java.util.Date;

public class Ticket extends BaseModel {
    private String number;
    private Date entryTime;
    private Vehicle vehicle;
    private ParkingSpot parkingSpot;
    private Gate gate;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public Gate getGate() {
        return gate;
    }

    public void setGate(Gate gate) {
        this.gate = gate;
    }

    @Override
    public String toString() {
        return """
            =========================================
                         PARKING RECEIPT
            =========================================

            Ticket ID      : %d
            Ticket Number  : %s
            Entry Time     : %s
            Gate Number    : %d
            Vehicle Number : %s
            Vehicle Type   : %s
            Owner Name     : %s

            Status         : ACTIVE
            Parking Spot   : %s

            =========================================
            Thank you for using Parking Lot!
            Have a nice day!
            =========================================
            """
                .formatted(
                        getId(),
                        number,
                        entryTime,
                        gate.getGateNumber(),
                        vehicle.getVehicleNumber(),
                        vehicle.getVehicleType(),
                        vehicle.getOwnerName(),
                        parkingSpot != null
                                ? parkingSpot.getParkingSpotNumber()
                                : "N/A"
                );
    }
}