package com.jpmorgan.assignment;

import java.time.LocalDateTime;
import java.util.Scanner;

import static com.jpmorgan.assignment.util.Constants.INVALID_INPUT_TRY_AGAIN;

/**
 * @author Lei
 * @version 1.0
 */

public class TakeHomeAssignmentApplication {

    private static Admin admin = new Admin();
    private static Buyer buyer = new Buyer();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println();
            System.out.println("Enter a command:");
            String input = scanner.nextLine();
            String[] tokens = input.trim().split(" ");
            String command = tokens[0];

            try {
                switch (command) {
                    case "Setup":
                        setup(tokens);
                        break;
                    case "View":
                        view(tokens);
                        break;
                    case "Availability":
                        availability(tokens);
                        break;
                    case "Book":
                        book(tokens);
                        break;
                    case "Cancel":
                        cancel(tokens);
                        break;
                    case "Exit":
                        System.out.println("Exiting program.");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid command. Try again.");
                        System.out.println("Please enter one of the following commands:");
                        System.out.println("Admin commands:");
                        System.out.println("    Setup <Show Number> <Number of Rows> <Number of seats per row> <Cancellation window in minutes>");
                        System.out.println("    View <Show Number>");
                        System.out.println("Buyer commands:");
                        System.out.println("    Availability <Show Number>");
                        System.out.println("    Book <Show Number> <Phone#> <Comma separated list of seats>");
                        System.out.println("    Cancel <Ticket#> <Phone#>");
                        System.out.println("Exit command:");
                        System.out.println("    Exit");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void cancel(String[] tokens) {
        if (tokens.length != 3) {
            System.out.println(INVALID_INPUT_TRY_AGAIN);
            System.out.println("Hint: Cancel <Ticket#> <Phone#>");
            System.out.println("Example: Cancel TKT-1-xxxxxxxx 88888888");
            return;
        }
        try {
            String ticketNumber = tokens[1];
            String phone = tokens[2];
            buyer.cancelBooking(ticketNumber, phone, admin, LocalDateTime.now());
        } catch (NumberFormatException e) {
            System.out.println(INVALID_INPUT_TRY_AGAIN);
            System.out.println("Hint: Cancel <Ticket#> <Phone#>");
        }
    }

    private static void book(String[] tokens) {
        if (tokens.length != 4) {
            System.out.println(INVALID_INPUT_TRY_AGAIN);
            System.out.println("Hint: Book <Show Number> <Phone#> <Comma separated list of seats>");
            System.out.println("Example: Book 1 88888888 A1,A2");
            return;
        }

        try {
            int showNumber = Integer.parseInt(tokens[1]);
            String phone = tokens[2];
            String[] seats = tokens[3].split(",");
            buyer.bookTickets(showNumber, phone, seats, admin, LocalDateTime.now());
        } catch (NumberFormatException e) {
            System.out.println(INVALID_INPUT_TRY_AGAIN);
            System.out.println("Hint: Book <Show Number> <Phone#> <Comma separated list of seats>");
        }
    }

    private static void availability(String[] tokens) {
        if (tokens.length != 2) {
            System.out.println(INVALID_INPUT_TRY_AGAIN);
            System.out.println("Hint: Availability <Show Number>");
            System.out.println("Example: Availability 1");
            return;
        }
        try {
            int showNumber = Integer.parseInt(tokens[1]);
            buyer.showAvailableSeats(showNumber, admin);
        } catch (NumberFormatException e) {
            System.out.println(INVALID_INPUT_TRY_AGAIN);
            System.out.println("Hint: Availability <Show Number>");
        }
    }


    private static void setup(String[] tokens) {
        if (tokens.length != 5) {
            System.out.println(INVALID_INPUT_TRY_AGAIN);
            System.out.println("Hint: Setup <Show Number> <Number of Rows> <Number of seats per row> <Cancellation window in minutes>");
            System.out.println("Example: Setup 1 26 10 2");
            return;
        }

        try {
            int showNumber = Integer.parseInt(tokens[1]);
            int numRows = Integer.parseInt(tokens[2]);
            int numSeatsPerRow = Integer.parseInt(tokens[3]);
            int cancellationWindowInMinutes = Integer.parseInt(tokens[4]);
            admin.setupShow(showNumber, numRows, numSeatsPerRow, cancellationWindowInMinutes);
            System.out.println("Show " + showNumber + " setup complete.");
        } catch (NumberFormatException e) {
            System.out.println(INVALID_INPUT_TRY_AGAIN);
            System.out.println("Hint: Setup <Show Number> <Number of Rows> <Number of seats per row> <Cancellation window in minutes>");
            System.out.println("<Show Number> only accepts positive integers");
            System.out.println("<Number of Rows> 1 - 26, integer only");
            System.out.println("<Number of seats per row> 1 - 10, integer only");
            System.out.println("<Cancellation window in minutes> only accepts positive integers");
        }
    }

    private static void view(String[] tokens) {
        if (tokens.length != 2) {
            System.out.println(INVALID_INPUT_TRY_AGAIN);
            System.out.println("Hint: View <Show Number>");
            System.out.println("Example: View 1");
            return;
        }
        try {
            int showNumber = Integer.parseInt(tokens[1]);
            admin.viewShows(showNumber);
        } catch (NumberFormatException e) {
            System.out.println(INVALID_INPUT_TRY_AGAIN);
            System.out.println("Hint: View <Show Number>");
            System.out.println("<Show Number> only accepts integers");
            System.out.println("if <Show Number> is less than 1, then it will list all shows");
        }
    }

}
