package repositories;

import models.Ticket;
import java.util.HashMap;
import java.util.Map;

public class TicketRepository {
    private Map<Long, Ticket> ticketMap;
    private Long id;

    public TicketRepository() {
        ticketMap = new HashMap<>();
        id = 0L;
    }

    public Ticket save(Ticket ticket) {

        if (ticket.getId() == null || ticket.getId() == 0) {
            id++;
            ticket.setId(id);
        }

        ticketMap.put(ticket.getId(), ticket);

        return ticket;
    }
}
