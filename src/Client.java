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

        System.out.println("Program started \n");

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
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setSpotAssignmentStrategyType(
                SpotAssignmentStrategyType.CHEAPEST
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

//        IssueTicketRequestDto issueTicketRequestDto = new IssueTicketRequestDto();
//        issueTicketRequestDto.setGateId(gate.getId());
//        issueTicketRequestDto.setOwnerName("Sahil Sharma");
//        issueTicketRequestDto.setVehicleNumber("DL 8CAF 9999");
//        issueTicketRequestDto.setVehicleType(VehicleType.LARGE);
//
//        IssueTicketResponseDto responseDto = ticketController.issueTicket(issueTicketRequestDto);
//
//        System.out.println("Ticket: " + responseDto.getTicket());

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

            issueTicketRequestDto.setVehicleType(VehicleType.LARGE);

            IssueTicketResponseDto responseDto =
                    ticketController.issueTicket(issueTicketRequestDto);

            System.out.println("Ticket: " + responseDto.getTicket());

            System.out.print("\nIssue another ticket? (Y/N): ");
            String choice = sc.nextLine();

            if (choice.equalsIgnoreCase("N")) {
                break;
            }
        }

    }
}
