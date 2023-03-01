package com.jpmorgan.assignment;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Lei
 * @version 1.0
 * The Seat class represents a seat
 */

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class Seat {

    /**
     * The unique identifier for the seat, e.g. "A1".
     * first char represents the rowNumber in Alphabet sequence, so A means row 1, B means row 2
     * second char represents the colNumber
     */
    private String seatNumber;
    /**
     * Indicates whether the seat is currently booked (true) or available (false).
     */
    private boolean isBooked;
    /**
     * The row number of the seat,  ranging from 1 to 26.
     */
    private int rowNumber;
    /**
     * The column number of the seat, ranging from 1 to 10.
     */
    private int colNumber;
}




