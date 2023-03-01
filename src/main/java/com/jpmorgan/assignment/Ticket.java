package com.jpmorgan.assignment;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author Lei
 * @version 1.0
 */

@Getter
public class Ticket {

    /**
     * A unique identifier for the ticket.
     */
    private final String ticketNumber;
    /**
     * The phone number of the person who booked the ticket.
     */
    private final String phone;
    /**
     * The show for which the ticket was booked.
     */
    private final Show show;
    /**
     * List of seats that were booked.
     */
    private final List<Seat> seats;
    /**
     * The time when the ticket was booked.
     */
    private final LocalDateTime bookingTime;
    /**
     * A flag indicating whether the ticket has been canceled (true) or not (false).
     */
    private boolean isCanceled;

    public Ticket(String phone, Show show, List<Seat> seats, LocalDateTime bookingTime) {
        this.ticketNumber = generateTicketNumber(show.getShowNumber());
        this.phone = phone;
        this.show = show;
        this.seats = seats;
        this.bookingTime = bookingTime;
    }

    /**
     * Generates a unique ticket number
     *
     * @param showNumber The show number for which the ticket is being booked.
     * @return A unique ticket number.
     */
    private static String generateTicketNumber(int showNumber) {
        String uniqueId = UUID.randomUUID().toString();
        return "TKT-" + showNumber + "-" + uniqueId.substring(0, 8);
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ticket #").append(ticketNumber).append(": ");
        sb.append("Show: ").append(show.getShowNumber()).append(", ");
        sb.append("Seats: ").append(getSeatNumbersFormatted()).append(", ");
        sb.append("Booked by: ").append(phone);
        return sb.toString();
    }

    /**
     * Returns a formatted string containing all the seat numbers of the seats that were booked for the ticket.
     *
     * @return A formatted string containing all the seat numbers of the seats that were booked for the ticket.
     */
    public String getSeatNumbersFormatted() {
        StringBuilder sb = new StringBuilder();
        for (Seat seat : seats) {
            sb.append(seat.getSeatNumber()).append(", ");
        }
        // Remove the last comma and space
        return sb.substring(0, sb.length() - 2);
    }
}



