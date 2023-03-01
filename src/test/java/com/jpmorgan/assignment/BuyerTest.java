package com.jpmorgan.assignment;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author Lei
 * @version 1.0
 */
class BuyerTest {

    @Test
    void testShowAvailableSeats() {
        Admin admin = new Admin();
        admin.setupShow(1, 3, 5, 2);
        Buyer buyer = new Buyer();

        buyer.showAvailableSeats(1, admin);
        assertEquals(15, admin.getShowMap().get(1).getAvailableSeats().values().size());

    }

    @Test
    void testBookTickets() {
        Admin admin = new Admin();
        admin.setupShow(1, 3, 5, 2);
        Buyer buyer = new Buyer();

        buyer.showAvailableSeats(1, admin);
        assertEquals(15, admin.getShowMap().get(1).getAvailableSeats().values().size());

        // success booking
        buyer.bookTickets(1, "12345678", new String[]{"A1", "A2"}, admin, LocalDateTime.now());
        assertEquals(13, admin.getShowMap().get(1).getAvailableSeats().values().size());
        assertEquals(1, buyer.getTicketMap().values().size());

        // fail booking
        buyer.bookTickets(1, "12345678", new String[]{"A1", "A2"}, admin, LocalDateTime.now());
        assertEquals(13, admin.getShowMap().get(1).getAvailableSeats().values().size());
        assertEquals(1, buyer.getTicketMap().values().size());
    }

}