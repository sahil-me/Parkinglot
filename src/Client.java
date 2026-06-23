import controllers.TicketController;
import dtos.IssueTicketRequestDto;
import dtos.IssueTicketResponseDto;
import models.Gate;
import models.ParkingLot;
import models.enums.SpotAssignmentStrategyType;
import java.util.List;
import models.enums.VehicleType;
import repositories.GateRepository;
import repositories.ParkingLotRepository;
import repositories.TicketRepository;
import repositories.VehicleRepository;
import services.TicketService;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=========================================");
        System.out.println("           PARKING LOT SYSTEM");
        System.out.println("=========================================\n");

        System.out.println("Welcome to Parking Lot!\n");

        GateRepository gateRepository = new GateRepository();
        TicketRepository ticketRepository = new TicketRepository();
        ParkingLotRepository parkingLotRepository = new ParkingLotRepository();
        VehicleRepository vehicleRepository = new VehicleRepository();

        // Create gates and save them
        Gate gate1 = new Gate();
        gate1.setGateNumber(1);
        gate1 = gateRepository.save(gate1);

        Gate gate2 = new Gate();
        gate2.setGateNumber(2);
        gate2 = gateRepository.save(gate2);

        Gate gate3 = new Gate();
        gate3.setGateNumber(3);
        gate3 = gateRepository.save(gate3);

        Gate gate4 = new Gate();
        gate4.setGateNumber(4);
        gate4 = gateRepository.save(gate4);

        // Create a parking lot and save it
        System.out.print("Select Spot (CHEAPEST/RANDOM): ");
        String strategy = sc.nextLine().toUpperCase();

        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setSpotAssignmentStrategyType(
                SpotAssignmentStrategyType.valueOf(strategy)
        );

        parkingLot.setGates(List.of(gate1, gate2, gate3, gate4));

        parkingLotRepository.save(parkingLot);

        // Create service
        TicketService ticketService = new TicketService(
                gateRepository,
                vehicleRepository,
                parkingLotRepository,
                ticketRepository
        );

        TicketController ticketController = new TicketController(ticketService);

        while (true) {

            System.out.print("Gate Number (1-4): ");
            Long gateId = Long.parseLong(sc.nextLine());

            IssueTicketRequestDto issueTicketRequestDto =
                    new IssueTicketRequestDto();

            issueTicketRequestDto.setGateId(gateId);

            System.out.print("Owner Name: ");
            issueTicketRequestDto.setOwnerName(sc.nextLine());

            System.out.print("Vehicle Number: ");
            issueTicketRequestDto.setVehicleNumber(sc.nextLine());

            System.out.print("Vehicle Type (SMALL/MEDIUM/LARGE): ");
            VehicleType vehicleType =
                    VehicleType.valueOf(sc.nextLine().toUpperCase());

            issueTicketRequestDto.setVehicleType(vehicleType);

            IssueTicketResponseDto responseDto =
                    ticketController.issueTicket(issueTicketRequestDto);

            if (responseDto.getTicket() != null) {
                System.out.println();
                System.out.println(responseDto.getTicket());
            } else {
                System.out.println("Ticket generation failed.");
            }

            System.out.print("\nIssue another ticket? (Y/N): ");
            String choice = sc.nextLine();

            if (choice.equalsIgnoreCase("N")) {
                break;
            }
        }

    }
}
