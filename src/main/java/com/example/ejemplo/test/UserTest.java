package com.example.ejemplo.test;

import com.example.ejemplo.model.User;
import com.example.ejemplo.model.User.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class UserTest {
    private User user;

    @BeforeEach
    public void setUp() {
        // Se inicializa un nuevo usuario antes de cada prueba
        user = new User("John Doe", "john@example.com", "password", "123456789", UserRole.STUDENT);
        user.setUserId(1L);
        user.setProfilePicture("profile.jpg");
        user.setRegistrationDate(LocalDateTime.of(2022, 3, 7, 12, 0)); // Fecha y hora fija para las pruebas
        user.setBookings(new ArrayList<>());
        user.setPosts(new ArrayList<>());
        user.setComments(new ArrayList<>());
        user.setReviews(new ArrayList<>());
        user.setAccommodations(new ArrayList<>());
    }

    @Test
    public void testEquals() {
        // Se crea otro usuario igual al primero
        User sameUser = new User("John Doe", "john@example.com", "password", "123456789", UserRole.STUDENT);
        sameUser.setUserId(1L);
        sameUser.setProfilePicture("profile.jpg");
        sameUser.setRegistrationDate(LocalDateTime.of(2022, 3, 7, 12, 0));
        sameUser.setBookings(new ArrayList<>());
        sameUser.setPosts(new ArrayList<>());
        sameUser.setComments(new ArrayList<>());
        sameUser.setReviews(new ArrayList<>());
        sameUser.setAccommodations(new ArrayList<>());

        // Comprobación de que los dos usuarios son iguales
        assertEquals(user, sameUser);
    }

    @Test
    public void testNotEquals() {
        // Se crea otro usuario con diferente información
        User differentUser = new User("Jane Smith", "jane@example.com", "password123", "987654321", UserRole.OWNER);

        // Comprobación de que los dos usuarios no son iguales
        assertNotEquals(user, differentUser);
    }

    @Test
    public void testToString() {
        // Comprobación de que el método toString devuelve el resultado esperado
        String expectedToString = "User{userId=1, name='John Doe', email='john@example.com', password='password', phone='123456789', role=STUDENT, registrationDate=2022-03-07T12:00, profilePicture='profile.jpg'}";
        assertEquals(expectedToString, user.toString());
    }
}
