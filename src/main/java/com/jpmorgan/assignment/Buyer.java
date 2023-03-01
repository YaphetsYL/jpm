package com.jpmorgan.assignment;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lei
 * @version 1.0
 */

@NoArgsConstructor
@Getter
public class Buyer {

    /**
     * A map of tickets booked by the buyer, with the ticket number as key.
     */
    private Map<String, Ticket> ticketMap = new HashMap<>();


    /**
     * Displays the available seats for the given show number.
     *
     * @param showNumber the show number to display the available seats for
     * @param admin      the Admin object to access the show map and print available seats
     */
    public void showAvailableSeats(int showNumber, Admin admin) {
        if (!isShowValid(showNumber, admin)) {
            return;
        }

        Show show = admin.getShowMap().get(showNumber);
        admin.printAvailableSeats(show);
    }


    /**
     * Checks if the show number is valid and exists in the show map of the Admin object.
     *
     * @param showNumber the show number to check validity for
     * @param admin      the Admin object to access the show map
     * @return true if the show number is valid and exists in the show map, false otherwise
     */
    private boolean isShowValid(int showNumber, Admin admin) {

        if (admin.getShowMap().keySet().isEmpty()) {
            System.out.println("Please create a Show first!");
            return Boolean.FALSE;
        }
        if (!admin.getShowMap().containsKey(showNumber)) {
            System.out.println("Show Number " + showNumber + " does not exist.");
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }


    /**
     * Books tickets for the given show
     *
     * @param showNumber  the show number to book tickets for
     * @param buyerPhone  the phone number of the buyer
     * @param seatNumbers an array of seat numbers to book
     * @param admin       the Admin object to access the show and seat maps and update ticket information
     * @param bookTime    the time at which the ticket is being booked
     */
    public void bookTickets(int showNumber, String buyerPhone, String[] seatNumbers, Admin admin, LocalDateTime bookTime) {

        if (!isShowValid(showNumber, admin)) {
            return;
        }
        Show show = admin.getShowMap().get(showNumber);

        // Check if the same phone number has not booked another ticket for the same show
        for (Ticket ticket : show.getTicketMap().values()) {
            if (ticket.getPhone().equals(buyerPhone)) {
                System.out.println("Phone number " + buyerPhone + " has already booked a ticket for this show");
                return;
            }
        }

        Map<String, Seat> availableSeats = show.getAvailableSeats();
        List<Seat> selectedSeats = new ArrayList<>();
        for (String seatNumber : seatNumbers) {
            Seat seat = show.getSeatMap().get(seatNumber.trim());
            if (seat == null) {
                System.out.println("Seat " + seatNumber + " not found");
                return;
            } else if (!availableSeats.containsValue(seat)) {
                System.out.println("Seat " + seatNumber + " is not available");
                return;
            } else {
                selectedSeats.add(seat);
            }
        }

        Ticket ticket = new Ticket(buyerPhone, show, selectedSeats, bookTime);
        for (Seat seat : selectedSeats) {
            seat.setBooked(Boolean.TRUE);
        }
        show.updateTicket(ticket);
        ticketMap.put(ticket.getTicketNumber(), ticket);
        System.out.println("Ticket booked successfully");
        System.out.println("\tTicket Number: " + ticket.getTicketNumber());
        System.out.println("\tSeats: " + ticket.getSeatNumbersFormatted());
    }


    /**
     * Cancels the booking of a ticket.
     *
     * @param ticketNumber the ticket number to be cancelled
     * @param phone        the phone number of the person who booked the ticket
     * @param admin        the admin object containing the show and seat information
     * @param cancelTime   the time at which the cancellation is being made
     */
    public void cancelBooking(String ticketNumber, String phone, Admin admin, LocalDateTime cancelTime) {
        Ticket ticket = ticketMap.get(ticketNumber);
        if (ticket == null) {
            System.out.println("Ticket not found");
            return;
        }

        if (ticket.isCanceled()) {
            System.out.println("Ticket has already been cancelled");
            return;
        }

        if (!ticket.getPhone().equals(phone)) {
            System.out.println("Ticket not booked by phone number " + phone);
            return;
        }

        Duration duration = Duration.between(ticket.getBookingTime(), cancelTime);
        if (duration.toMinutes() > ticket.getShow().getCancellationWindowMinutes()) {
            System.out.println("Exceed the cancellation window of the show, unable to cancel");
            return;
        }


        // Cancel the seats and update show
        for (Seat seat : ticket.getSeats()) {
            seat.setBooked(Boolean.FALSE);
        }
        ticket.setCanceled(true);

        Show show = admin.getShowMap().get(ticket.getShow().getShowNumber());
        show.cancelTicket(ticket);
        System.out.println("Ticket cancelled successfully");

    }

}
