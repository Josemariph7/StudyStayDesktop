package com.example.ejemplo.test;

import com.example.ejemplo.dao.AccommodationDAO;
import com.example.ejemplo.dao.AccommodationReviewDAO;
import com.example.ejemplo.dao.UserDAO;
import com.example.ejemplo.model.Accommodation;
import com.example.ejemplo.model.AccommodationReview;
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

public class AccommodationReviewDAOTest {

    private AccommodationReviewDAO reviewDAO;
    private AccommodationDAO accommodationDAO;
    private UserDAO userDAO;
    private final String JDBC_URL = "jdbc:mysql://localhost:3306/studystaydb?useSSL=false&serverTimezone=UTC";
    private final String USERNAME = "root";
    private final String PASSWORD = "";

    @Before
    public void setUp() {
        reviewDAO = new AccommodationReviewDAO();
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
             PreparedStatement statement1 = connection.prepareStatement("DELETE FROM AccommodationReviews");
             PreparedStatement statement2 = connection.prepareStatement("DELETE FROM Bookings");
             PreparedStatement statement3 = connection.prepareStatement("DELETE FROM Accommodations");
             PreparedStatement statement4 = connection.prepareStatement("DELETE FROM Users")) {
            statement1.executeUpdate();
            statement2.executeUpdate();
            statement3.executeUpdate();
            statement4.executeUpdate();
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

        // Insert sample review
        AccommodationReview review = new AccommodationReview(accommodation, user, 4.5, "Great place!", LocalDateTime.now());
        reviewDAO.create(review);
    }

    @Test
    public void testGetAll() {
        List<AccommodationReview> reviews = reviewDAO.getAll();
        assertNotNull(reviews);
        assertEquals(1, reviews.size());
    }

    @Test
    public void testCreate() {
        User user = userDAO.getAll().get(0);
        Accommodation accommodation = accommodationDAO.getAll().get(0);
        AccommodationReview review = new AccommodationReview(accommodation, user, 5.0, "Excellent stay!", LocalDateTime.now());
        boolean result = reviewDAO.create(review);
        assertTrue(result);
        assertNotNull(review.getReviewId());
        assertEquals(2, reviewDAO.getAll().size());
    }

    @Test
    public void testUpdate() {
        AccommodationReview review = reviewDAO.getAll().get(0);
        review.setComment("Updated comment");
        boolean result = reviewDAO.update(review);
        assertTrue(result);
        AccommodationReview updatedReview = reviewDAO.getAll().get(0);
        assertEquals("Updated comment", updatedReview.getComment());
    }

    @Test
    public void testDelete() {
        AccommodationReview review = reviewDAO.getAll().get(0);
        boolean result = reviewDAO.delete(review.getReviewId());
        assertTrue(result);
        assertEquals(0, reviewDAO.getAll().size());
    }

    @Test
    public void testGetReviewsForAccommodation() {
        Accommodation accommodation = accommodationDAO.getAll().get(0);
        List<AccommodationReview> reviews = reviewDAO.getReviewsForAccommodation(accommodation.getAccommodationId());
        assertNotNull(reviews);
        assertEquals(1, reviews.size());
    }
}
