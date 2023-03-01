package com.jpmorgan.assignment;


import com.jpmorgan.assignment.util.SeatCompare;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.jpmorgan.assignment.util.Constants.*;

/**
 * @author Lei
 * @version 1.0
 */

@Getter
@AllArgsConstructor
public class Admin {

    /**
     * A map of shows, with the show number as the key.
     */
    private Map<Integer, Show> showMap;

    public Admin() {
        this.showMap = new HashMap<>();
    }

    /**
     * Sets up a new show with the given show number, number of rows, number of seats per row,
     * and cancellation window in minutes. Add the new show to the map of shows.
     *
     * @param showNumber         the show number
     * @param numRows            the number of rows
     * @param numSeatsPerRow     the number of seats per row
     * @param cancellationWindow the cancellation window in minutes
     * @throws IllegalArgumentException if any of the input parameters are invalid
     */
    public void setupShow(int showNumber, int numRows, int numSeatsPerRow, int cancellationWindow) {
        // validation checks
        if (showNumber < MINIMUM_NUMBER_1) {
            throw new IllegalArgumentException("Show Number only accepts positive integers");
        }
        if (showMap.containsKey(showNumber)) {
            throw new IllegalArgumentException(SHOW_NUMBER + showNumber + " exists! Please use a different Show Number");
        }

        if (numRows > NUMBER_OF_ROWS_MAX) {
            throw new IllegalArgumentException("Number of rows cannot exceed 26");
        }
        if (numRows < MINIMUM_NUMBER_1) {
            throw new IllegalArgumentException("Number of rows cannot be less than 0");
        }

        if (numSeatsPerRow > NUMBER_OF_SEAT_PER_ROW_MAX) {
            throw new IllegalArgumentException("Number of seats cannot exceed 10");
        }
        if (numSeatsPerRow < MINIMUM_NUMBER_1) {
            throw new IllegalArgumentException("Number of seats cannot be less than 0");
        }

        if (cancellationWindow < MINIMUM_NUMBER_1) {
            throw new IllegalArgumentException("Cancellation window in minutes is at least 1");
        }

        Show show = new Show(showNumber, numRows, numSeatsPerRow, cancellationWindow);
        showMap.put(showNumber, show);
        System.out.println(SHOW_NUMBER + showNumber + " has been set up with " + numRows + " rows and " + numSeatsPerRow + " seats per row.");
    }


    /**
     * Prints out information about the specified show, including ticket and seat availability.
     *
     * @param showNumber the show number to view, or use number less than 1 to view all shows
     */
    public void viewShows(int showNumber) {

        // checks for empty show map and invalid show numbers
        if (showMap.keySet().isEmpty()) {
            System.out.println("Please create a Show first!");
            return;
        }
        if (showNumber < 1) {
            //list all shows
            System.out.println("List of show(s)");
            for (int showNum : showMap.keySet()) {
                System.out.println(SHOW_NUMBER + showNum);
            }

        } else {
            // prints out information for the specified show
            if (!showMap.containsKey(showNumber)) {
                System.out.println(SHOW_NUMBER + showNumber + " does not exist.");
                return;
            }
            Show show = showMap.get(showNumber);

            System.out.println(SHOW_NUMBER + showNumber);

            System.out.println("Ticket(s):");
            if (show.getTicketMap().keySet().isEmpty()) {
                System.out.println("\tNo ticket has been sold");
            } else {
                for (Ticket ticket : show.getTicketMap().values()) {
                    System.out.println("\tTicket: " + ticket.getTicketNumber() + ", Buyer Phone: " + ticket.getPhone() +
                            ", Seat Number(s): " + ticket.getSeatNumbersFormatted());
                }
            }

            printAvailableSeats(show);

        }
    }

    /**
     * Prints the available seats sorted by row for the given show.
     *
     * @param show the show to check for available seats
     */
    public void printAvailableSeats(Show show) {
        System.out.println("Available seat(s) for Show " + show.getShowNumber() + ":");
        Map<String, Seat> availableSeats = show.getAvailableSeats();
        if (CollectionUtils.isEmpty(availableSeats)) {
            System.out.println("\tNo seats available.");
        } else {

            Map<Integer, List<Seat>> seatsByRow = availableSeats.values()
                    .stream()
                    .sorted(new SeatCompare())
                    .collect(Collectors.groupingBy(Seat::getRowNumber));

            for (Map.Entry<Integer, List<Seat>> entry : seatsByRow.entrySet()) {
                StringBuilder rowString = new StringBuilder();
                List<Seat> seatsInRow = entry.getValue();

                // Add the seat numbers to the row string, separated by a space
                for (Seat seat : seatsInRow) {
                    rowString.append(seat.getSeatNumber()).append(" ");
                }

                // Print the completed row string
                System.out.println(rowString.toString().trim());
            }

        }
    }

}
