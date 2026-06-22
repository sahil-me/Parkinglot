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

        System.out.println("Program started");

        GateRepository gateRepository = new GateRepository();
        TicketRepository ticketRepository = new TicketRepository();
        ParkingLotRepository parkingLotRepository = new ParkingLotRepository();
        VehicleRepository vehicleRepository = new VehicleRepository();

        // Create a gate and save it
        Gate gate = new Gate();
        gate.setGateNumber(1);
        gate = gateRepository.save(gate);

        // Create a parking lot and save it
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setSpotAssignmentStrategyType(
                SpotAssignmentStrategyType.CHEAPEST
        );

        parkingLot.setGates(List.of(gate));

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

            IssueTicketRequestDto issueTicketRequestDto =
                    new IssueTicketRequestDto();

            issueTicketRequestDto.setGateId(gate.getId());

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
