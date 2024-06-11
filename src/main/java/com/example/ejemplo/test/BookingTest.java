/*
 * StudyStay © 2024
 *
 * All rights reserved.
 *
 * This software and associated documentation files (the "Software") are owned by StudyStay. Unauthorized copying, distribution, or modification of this Software is strictly prohibited.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this Software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * StudyStay
 * José María Pozo Hidalgo
 * Email: josemariph7@gmail.com
 *
 *
 */

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
