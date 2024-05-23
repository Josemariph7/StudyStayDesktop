package com.example.ejemplo.test;

import com.example.ejemplo.dao.AccommodationPhotoDAO;
import com.example.ejemplo.dao.AccommodationDAO;
import com.example.ejemplo.dao.UserDAO;
import com.example.ejemplo.model.Accommodation;
import com.example.ejemplo.model.AccommodationPhoto;
import com.example.ejemplo.model.User;
import org.junit.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class AccommodationPhotoDAOTest {

    private AccommodationPhotoDAO photoDAO;
    private AccommodationDAO accommodationDAO;
    private UserDAO userDAO;
    private final String JDBC_URL = "jdbc:mysql://localhost:3306/studystaydb?useSSL=false&serverTimezone=UTC";
    private final String USERNAME = "root";
    private final String PASSWORD = "";

    @Before
    public void setUp() {
        photoDAO = new AccommodationPhotoDAO();
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
             PreparedStatement statement1 = connection.prepareStatement("DELETE FROM AccommodationPhotos");
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
        User user = new User("John", "Doe", "john.doe@example.com", "password123", "1234567890", LocalDate.of(1990, 1, 1), User.Gender.MALE, "12345678X", true);
        userDAO.create(user);

        // Insert sample accommodation
        Accommodation accommodation = new Accommodation(user, "123 Street", "City", new BigDecimal("100.00"), "Nice place", 4, "WiFi, Pool");
        accommodationDAO.create(accommodation);

        // Insert sample photo
        AccommodationPhoto photo = new AccommodationPhoto(accommodation, new byte[]{1, 2, 3, 4, 5});
        photoDAO.create(photo);
    }

    @Test
    public void testGetAll() {
        List<AccommodationPhoto> photos = photoDAO.getAll();
        assertNotNull(photos);
        assertEquals(1, photos.size());
    }

    @Test
    public void testCreate() {
        Accommodation accommodation = accommodationDAO.getAll().get(0);
        AccommodationPhoto photo = new AccommodationPhoto(accommodation, new byte[]{6, 7, 8, 9, 10});
        boolean result = photoDAO.create(photo);
        assertTrue(result);
        assertEquals(2, photoDAO.getAll().size());
    }

    @Test
    public void testUpdate() {
        AccommodationPhoto photo = photoDAO.getAll().get(0);
        photo.setPhotoData(new byte[]{10, 9, 8, 7, 6});
        boolean result = photoDAO.update(photo);
        assertTrue(result);
        AccommodationPhoto updatedPhoto = photoDAO.getAll().get(0);
        assertArrayEquals(new byte[]{10, 9, 8, 7, 6}, updatedPhoto.getPhotoData());
    }

    @Test
    public void testDelete() {
        AccommodationPhoto photo = photoDAO.getAll().get(0);
        boolean result = photoDAO.delete(photo.getPhotoId());
        assertTrue(result);
        assertEquals(0, photoDAO.getAll().size());
    }
}
