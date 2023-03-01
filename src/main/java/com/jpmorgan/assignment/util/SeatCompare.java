package com.jpmorgan.assignment.util;

import com.jpmorgan.assignment.Seat;

import java.util.Comparator;

/**
 * @author Lei
 * @version 1.0
 */
public class SeatCompare implements Comparator<Seat> {

    @Override
    public int compare(Seat s1, Seat s2) {
        if (s1.getRowNumber() == s2.getRowNumber()) {
            return s1.getColNumber() - s2.getColNumber();
        } else {
            return s1.getRowNumber() - s2.getRowNumber();
        }
    }
}
