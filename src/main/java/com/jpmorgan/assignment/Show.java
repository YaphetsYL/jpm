package com.jpmorgan.assignment;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Lei
 * @version 1.0
 */


@Getter
@EqualsAndHashCode
public class Show {

    private int showNumber;
    /**
     * The number of minutes when tickets can no longer be cancelled after booking.
     */
    private int cancellationWindowMinutes;
    private int numRows;
    private int numSeatsPerRow;
    /**
     * A map of seats associated with the show, with the seat number as the key.
     */
    private Map<String, Seat> seatMap = new HashMap<>();
    /**
     * A map of tickets associated with the show, with the ticket number as the key.
     */
    private Map<String, Ticket> ticketMap = new HashMap<>();

    public Show(int showNumber, int numRows, int numSeatsPerRow, int cancellationWindowMinutes) {
        this.showNumber = showNumber;
        this.cancellationWindowMinutes = cancellationWindowMinutes;
        this.numRows = numRows;
        this.numSeatsPerRow = numSeatsPerRow;
        setupSeats(numRows, numSeatsPerRow);
    }

    /**
     * Sets up the seats for the show based on the number of rows and number of seats per row.
     *
     * @param numRows        the number of rows in the show's seating arrangement.
     * @param numSeatsPerRow the number of seats in each row of the show's seating arrangement.
     */
    private void setupSeats(int numRows, int numSeatsPerRow) {
        for (int row = 1; row <= numRows; row++) {
            for (int seatNum = 1; seatNum <= numSeatsPerRow; seatNum++) {
                char seatLetter = (char) ('A' + row - 1);
                String seatNumber = String.format("%c%d", seatLetter, seatNum);
                seatMap.put(seatNumber, new Seat(seatNumber, Boolean.FALSE, row, seatNum));
            }
        }
    }

    /**
     * Returns a map of available seats for the show, with the seat number as the key.
     *
     * @return a map of available seats for the show.
     */
    public Map<String, Seat> getAvailableSeats() {
        Map<String, Seat> availableSeats = new HashMap<>(seatMap.values().size());
        for (Seat seat : new ArrayList<>(seatMap.values())) {
            if (!seat.isBooked()) {
                availableSeats.put(seat.getSeatNumber(), seat);
            }
        }
        return availableSeats;
    }

    /**
     * Add the ticket to the map of tickets associated with the show
     *
     * @param ticket the ticket to update.
     */
    public void updateTicket(Ticket ticket) {
        ticketMap.put(ticket.getTicketNumber(), ticket);
    }

    /**
     * Removes the given ticket from the ticket map.
     *
     * @param ticket the ticket to cancel.
     */
    public void cancelTicket(Ticket ticket) {
        ticketMap.remove(ticket.getTicketNumber());
    }

}

