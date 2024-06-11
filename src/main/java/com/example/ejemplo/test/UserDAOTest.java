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

import com.example.ejemplo.dao.UserDAO;
import com.example.ejemplo.model.User;
import org.junit.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class UserDAOTest {

    private static UserDAO userDAO;

    @BeforeClass
    public static void setUpClass() {
        userDAO = new UserDAO();
        setUpDatabase();
    }

    @AfterClass
    public static void tearDownClass() {
        tearDownDatabase();
    }

    @Before
    public void setUp() {
        clearDatabase();
    }

    @Test
    public void testGetAll() {
        User user = createUser();
        userDAO.create(user);

        List<User> users = userDAO.getAll();
        assertNotNull(users);
        assertEquals(1, users.size());
    }

    @Test
    public void testCreate() {
        User user = createUser();
        boolean result = userDAO.create(user);
        assertTrue(result);

        List<User> users = userDAO.getAll();
        assertEquals(1, users.size());
    }

    @Test
    public void testUpdate() {
        User user = createUser();
        userDAO.create(user);

        user.setName("Updated Name");
        boolean result = userDAO.update(user);
        assertTrue(result);

        User updatedUser = userDAO.getById(user.getUserId());
        assertEquals("Updated Name", updatedUser.getName());
    }

    @Test
    public void testDelete() {
        User user = createUser();
        userDAO.create(user);

        boolean result = userDAO.delete(user.getUserId());
        assertTrue(result);

        List<User> users = userDAO.getAll();
        assertTrue(users.isEmpty());
    }

    @Test
    public void testGetById() {
        User user = createUser();
        userDAO.create(user);

        User fetchedUser = userDAO.getById(user.getUserId());
        assertNotNull(fetchedUser);
        assertEquals(user.getUserId(), fetchedUser.getUserId());
    }

    @Test
    public void testFindByEmail() {
        User user = createUser();
        userDAO.create(user);

        User fetchedUser = userDAO.findByEmail(user.getEmail());
        assertNotNull(fetchedUser);
        assertEquals(user.getEmail(), fetchedUser.getEmail());
    }

    @Test
    public void testAuthenticate() {
        User user = createUser();
        userDAO.create(user);

        boolean result = userDAO.authenticate(user.getEmail(), user.getPassword());
        assertTrue(result);

        boolean failedResult = userDAO.authenticate(user.getEmail(), "wrongpassword");
        assertFalse(failedResult);
    }

    private User createUser() {
        return new User("John", "Doe", "john.doe@example.com", "password123", "1234567890",
                LocalDate.of(1990, 1, 1), LocalDateTime.now(), User.Gender.MALE, "12345678X", null, "Bio", true);
    }

    private static void setUpDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS studystaydb");
            statement.executeUpdate("USE studystaydb");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Users (" +
                    "UserId BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                    "Name VARCHAR(255), " +
                    "LastName VARCHAR(255), " +
                    "Email VARCHAR(255) UNIQUE, " +
                    "Password VARCHAR(255), " +
                    "Phone VARCHAR(50), " +
                    "BirthDate DATE, " +
                    "RegistrationDate TIMESTAMP, " +
                    "Gender VARCHAR(10), " +
                    "DNI VARCHAR(20), " +
                    "ProfilePicture BLOB, " +
                    "Bio TEXT, " +
                    "isAdmin BOOLEAN)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void tearDownDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP DATABASE IF EXISTS studystaydb");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/studystaydb", "root", "");
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM Users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
