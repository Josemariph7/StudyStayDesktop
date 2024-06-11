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

import com.example.ejemplo.dao.AccommodationDAO;
import com.example.ejemplo.dao.BookingDAO;
import com.example.ejemplo.dao.UserDAO;
import com.example.ejemplo.model.Accommodation;
import com.example.ejemplo.model.Booking;
import com.example.ejemplo.model.User;
import org.junit.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class BookingDAOTest {

    private BookingDAO bookingDAO;
    private AccommodationDAO accommodationDAO;
    private UserDAO userDAO;
    private final String JDBC_URL = "jdbc:mysql://localhost:3306/studystaydb?useSSL=false&serverTimezone=UTC";
    private final String USERNAME = "root";
    private final String PASSWORD = "";

    @Before
    public void setUp() {
        bookingDAO = new BookingDAO();
        accommodationDAO = new AccommodationDAO();
        userDAO = new UserDAO();
        clearDatabase();
        insertSampleData();
    }

    @After
    public void tearDown() {
        clearDatabase();
    }

    private void clearDatabase() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement1 = connection.prepareStatement("DELETE FROM Bookings");
             PreparedStatement statement2 = connection.prepareStatement("DELETE FROM Accommodations");
             PreparedStatement statement3 = connection.prepareStatement("DELETE FROM Users")) {
            statement1.executeUpdate();
            statement2.executeUpdate();
            statement3.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertSampleData() {
        // Insert sample user
        User user = new User("John", "Doe", "john.doe@example.com", "password123", "1234567890", LocalDateTime.of(1990, 1, 1, 0, 0).toLocalDate(), User.Gender.MALE, "12345678X", true);
        userDAO.create(user);

        // Insert sample accommodation
        Accommodation accommodation = new Accommodation(user, "123 Street", "City", new BigDecimal("100.00"), "Nice place", 4, "WiFi, Pool");
        accommodationDAO.create(accommodation);

        // Insert sample booking
        Booking booking = new Booking(accommodation, user, LocalDateTime.now().minusDays(5), LocalDateTime.now().plusDays(5), Booking.BookingStatus.PENDING);
        bookingDAO.create(booking);
    }

    @Test
    public void testGetAll() {
        List<Booking> bookings = bookingDAO.getAll();
        assertNotNull(bookings);
        assertEquals(1, bookings.size());
    }

    @Test
    public void testCreate() {
        User user = userDAO.getAll().get(0);
        Accommodation accommodation = accommodationDAO.getAll().get(0);
        Booking booking = new Booking(accommodation, user, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1), Booking.BookingStatus.CONFIRMED);
        boolean result = bookingDAO.create(booking);
        assertTrue(result);
        assertNotNull(booking.getBookingId());
        assertEquals(2, bookingDAO.getAll().size());
    }

    @Test
    public void testUpdate() {
        Booking booking = bookingDAO.getAll().get(0);
        booking.setStatus(Booking.BookingStatus.CONFIRMED);
        boolean result = bookingDAO.update(booking);
        assertTrue(result);
        Booking updatedBooking = bookingDAO.getAll().get(0);
        assertEquals(Booking.BookingStatus.CONFIRMED, updatedBooking.getStatus());
    }

    @Test
    public void testDelete() {
        Booking booking = bookingDAO.getAll().get(0);
        boolean result = bookingDAO.delete(booking.getBookingId());
        assertTrue(result);
        assertEquals(0, bookingDAO.getAll().size());
    }
}
