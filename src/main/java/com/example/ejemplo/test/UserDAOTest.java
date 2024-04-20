package com.example.ejemplo.test;
import com.example.ejemplo.dao.UserDAO;
import com.example.ejemplo.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/studystaydb_test?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static Connection connection;
    private static UserDAO userDAO;

    @BeforeAll
    static void setUp() throws SQLException {
        connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        userDAO = new UserDAO();
        createTable();
    }

    @AfterAll
    static void tearDown() throws SQLException {
        dropTable();
        connection.close();
    }

    @Test
    void testCreateAndGetAll() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setPhone("1234567890");
        user.setRole(User.UserRole.OWNER);
        user.setRegistrationDate(LocalDateTime.now());
        user.setProfilePicture("profile.jpg");

        assertTrue(userDAO.create(user), "User creation should succeed");

        List<User> userList = userDAO.getAll();
        assertEquals(1, userList.size(), "There should be one user in the database");
        assertEquals(user.getName(), userList.get(0).getName(), "User names should match");
        assertEquals(user.getEmail(), userList.get(0).getEmail(), "User emails should match");
        assertEquals(user.getPassword(), userList.get(0).getPassword(), "User passwords should match");
        assertEquals(user.getPhone(), userList.get(0).getPhone(), "User phones should match");
        assertEquals(user.getRole(), userList.get(0).getRole(), "User roles should match");
        assertEquals(user.getRegistrationDate(), userList.get(0).getRegistrationDate(), "User registration dates should match");
        assertEquals(user.getProfilePicture(), userList.get(0).getProfilePicture(), "User profile pictures should match");
    }

    private static void createTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE users (UserId BIGINT AUTO_INCREMENT PRIMARY KEY, Name VARCHAR(255), Email VARCHAR(255), Password VARCHAR(255), Phone VARCHAR(255), Role VARCHAR(255), RegistrationDate TIMESTAMP, ProfilePicture VARCHAR(255))");
        }
    }

    private static void dropTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
        }
    }
}
