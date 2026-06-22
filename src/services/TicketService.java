package services;

import exceptions.GateNotFoundException;
import exceptions.ParkingLotNotFoundException;
import factories.SpotAssignmentStrategyFactory;
import models.*;
import models.enums.VehicleType;
import repositories.GateRepository;
import repositories.ParkingLotRepository;
import repositories.TicketRepository;
import repositories.VehicleRepository;
import strategies.spotAssignmentStrategy.SpotAssignmentStrategy;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public class TicketService {
    private GateRepository gateRepository;
    private VehicleRepository vehicleRepository;
    private ParkingLotRepository parkingLotRepository;
    private TicketRepository ticketRepository;

    public TicketService(
            GateRepository gateRepository,
            VehicleRepository vehicleRepository,
            ParkingLotRepository parkingLotRepository,
            TicketRepository ticketRepository) {
        this.gateRepository = gateRepository;
        this.vehicleRepository = vehicleRepository;
        this.parkingLotRepository = parkingLotRepository;
        this.ticketRepository = ticketRepository;
    }

    public Ticket issueTicket(
            String vehicleNumber,
            Long gateId,
            String ownerName,
            VehicleType vehicleType)
            throws GateNotFoundException, ParkingLotNotFoundException {

        Ticket ticket = new Ticket();
        ticket.setEntryTime(new Date());
        Optional<Gate> optionalGate = gateRepository.findById(gateId);

        if (optionalGate.isEmpty()) {
            throw new GateNotFoundException("Please enter a valid gate number");
        }

        Gate gate = optionalGate.get();;
        ticket.setGate(gate);

        Optional<Vehicle> vehicleOptional = vehicleRepository.findByVehicleNumber(vehicleNumber);
        Vehicle newVehicle = null;
        if(vehicleOptional.isEmpty()) {
            // Create a new Vehicle
            Vehicle vehicle = new Vehicle();
            vehicle.setVehicleNumber(vehicleNumber);
            vehicle.setVehicleType(vehicleType);
            vehicle.setOwnerName(ownerName);
            newVehicle = vehicleRepository.save(vehicle);
        }
        else {
            newVehicle = vehicleOptional.get();
        }

        ticket.setVehicle(newVehicle);
        ticket.setNumber("TKT-" + UUID.randomUUID().toString().substring(0, 8));

        Optional<ParkingLot> optionalParkingLot = parkingLotRepository.findParkingLotByGateId(gateId);

        if (optionalParkingLot.isEmpty()) {
            // throw exception
            throw new ParkingLotNotFoundException("Parking lot not found");
        }

        ParkingLot parkingLot = optionalParkingLot.get();

        SpotAssignmentStrategy spotAssignmentStrategy =
                SpotAssignmentStrategyFactory.getSpotAssignmentStrategy(
                        parkingLot.getSpotAssignmentStrategyType()
                );

        ParkingSpot parkingSpot = spotAssignmentStrategy.assignSpot(vehicleType, gate);

        ticket.setParkingSpot(parkingSpot);

        return ticketRepository.save(ticket);
    }
}
