package com.example.ejemplo.test;

import com.example.ejemplo.model.Booking;
import com.example.ejemplo.model.Accommodation;
import com.example.ejemplo.model.User;
import org.junit.*;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;

public class BookingTest {

    private Booking booking;
    private Booking anotherBooking;

    @Before
    public void setUp() {
        User user = new User("John", "Doe", "john.doe@example.com", "password123", "1234567890",
                java.time.LocalDate.of(1990, 1, 1), User.Gender.MALE, "12345678X", true);
        Accommodation accommodation = new Accommodation();
        LocalDateTime startDate = LocalDateTime.of(2023, 5, 1, 14, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 5, 10, 12, 0);

        booking = new Booking(accommodation, user, startDate, endDate, Booking.BookingStatus.PENDING);
        anotherBooking = new Booking(accommodation, user, startDate, endDate, Booking.BookingStatus.CONFIRMED);
    }

    @Test
    public void testGetAndSetBookingId() {
        booking.setBookingId(1L);
        assertEquals(Optional.of(1L), Optional.ofNullable(booking.getBookingId()));
    }

    @Test
    public void testGetAndSetAccommodation() {
        Accommodation accommodation = new Accommodation();
        booking.setAccommodation(accommodation);
        assertEquals(accommodation, booking.getAccommodation());
    }

    @Test
    public void testGetAndSetUser() {
        User user = new User();
        booking.setUser(user);
        assertEquals(user, booking.getUser());
    }

    @Test
    public void testGetAndSetStartDate() {
        LocalDateTime startDate = LocalDateTime.of(2023, 6, 1, 14, 0);
        booking.setStartDate(startDate);
        assertEquals(startDate, booking.getStartDate());
    }

    @Test
    public void testGetAndSetEndDate() {
        LocalDateTime endDate = LocalDateTime.of(2023, 6, 10, 12, 0);
        booking.setEndDate(endDate);
        assertEquals(endDate, booking.getEndDate());
    }

    @Test
    public void testGetAndSetStatus() {
        booking.setStatus(Booking.BookingStatus.CONFIRMED);
        assertEquals(Booking.BookingStatus.CONFIRMED, booking.getStatus());
    }

    @Test
    public void testEquals() {
        Booking anotherBookingCopy = new Booking(booking.getAccommodation(), booking.getUser(), booking.getStartDate(),
                booking.getEndDate(), booking.getStatus());
        assertEquals(booking, anotherBookingCopy);
        assertNotEquals(booking, anotherBooking);
    }

    @Test
    public void testHashCode() {
        Booking anotherBookingCopy = new Booking(booking.getAccommodation(), booking.getUser(), booking.getStartDate(),
                booking.getEndDate(), booking.getStatus());
        assertEquals(booking.hashCode(), anotherBookingCopy.hashCode());
        assertNotEquals(booking.hashCode(), anotherBooking.hashCode());
    }

    @Test
    public void testToString() {
        String expected = "Booking{bookingId=null, accommodation=" + booking.getAccommodation() +
                ", user=" + booking.getUser() +
                ", startDate=" + booking.getStartDate() +
                ", endDate=" + booking.getEndDate() +
                ", status=PENDING}";
        assertEquals(expected, booking.toString());
    }
}
