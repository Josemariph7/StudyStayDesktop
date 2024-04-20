package com.example.ejemplo.test;

import com.example.ejemplo.model.Booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BookingTest {

    private Booking booking;

    @BeforeEach
    public void setUp() {
        booking = new Booking(1L, null, null, LocalDateTime.now(), LocalDateTime.now().plusDays(2), Booking.BookingStatus.PENDING);
    }

    @Test
    public void testBookingConstructor() {
        assertNotNull(booking);
    }

    @Test
    public void testBookingAttributes() {
        assertEquals(1L, booking.getBookingId());
        assertNotNull(booking.getStartDate());
        assertNotNull(booking.getEndDate());
        assertNotNull(booking.getStatus());
    }

    @Test
    public void testSettersAndGetters() {
        LocalDateTime newStartDate = LocalDateTime.now().plusDays(1);
        booking.setStartDate(newStartDate);
        assertEquals(newStartDate, booking.getStartDate());

        LocalDateTime newEndDate = LocalDateTime.now().plusDays(3);
        booking.setEndDate(newEndDate);
        assertEquals(newEndDate, booking.getEndDate());

        Booking.BookingStatus newStatus = Booking.BookingStatus.CONFIRMED;
        booking.setStatus(newStatus);
        assertEquals(newStatus, booking.getStatus());
    }

    @Test
    public void testEqualsAndHashCode() {
        Booking sameBooking = new Booking(1L, null, null, LocalDateTime.now(), LocalDateTime.now().plusDays(2), Booking.BookingStatus.PENDING);
        Booking differentBooking = new Booking(2L, null, null, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1), Booking.BookingStatus.CONFIRMED);

        assertEquals(booking, sameBooking);
        assertEquals(booking.hashCode(), sameBooking.hashCode());
        assertNotEquals(booking, differentBooking);
        assertNotEquals(booking.hashCode(), differentBooking.hashCode());
    }

    @Test
    public void testToString() {
        String expectedString = "Booking{" +
                "bookingId=1, " +
                "accommodation=null, " +
                "user=null, " +
                "startDate=" + booking.getStartDate() + ", " +
                "endDate=" + booking.getEndDate() + ", " +
                "status=PENDING" +
                "}";
        assertEquals(expectedString, booking.toString());
    }
}
