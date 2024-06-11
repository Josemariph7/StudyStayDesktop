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

import com.example.ejemplo.model.User;
import com.example.ejemplo.model.Booking;
import com.example.ejemplo.model.ForumComment;
import com.example.ejemplo.model.AccommodationReview;
import com.example.ejemplo.model.Accommodation;

import org.junit.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class UserTest {

    private User user=new User("John", "Doe", "john.doe@example.com", "password123", "1234567890", LocalDate.of(1990, 1, 1), User.Gender.MALE, "12345678X", true);
    ;
    private User anotherUser=new User("John", "Doe", "john.doe@example.com", "password123", "1234567890", LocalDate.of(1990, 1, 1), User.Gender.MALE, "12345678X", true);
    ;


    @Test
    public void testGetAndSetUserId() {
        user.setUserId(1L);
        assertEquals(Optional.of(1L), user.getUserId());
    }

    @Test
    public void testGetAndSetName() {
        user.setName("John");
        assertEquals("John", user.getName());
    }

    @Test
    public void testGetAndSetLastName() {
        user.setLastName("Doe");
        assertEquals("Doe", user.getLastName());
    }

    @Test
    public void testGetAndSetEmail() {
        user.setEmail("john.doe@example.com");
        assertEquals("john.doe@example.com", user.getEmail());
    }

    @Test
    public void testGetAndSetPassword() {
        user.setPassword("password123");
        assertEquals("password123", user.getPassword());
    }

    @Test
    public void testGetAndSetPhone() {
        user.setPhone("1234567890");
        assertEquals("1234567890", user.getPhone());
    }

    @Test
    public void testGetAndSetBirthDate() {
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        user.setBirthDate(birthDate);
        assertEquals(birthDate, user.getBirthDate());
    }

    @Test
    public void testGetAndSetRegistrationDate() {
        LocalDateTime registrationDate = LocalDateTime.now();
        user.setRegistrationDate(registrationDate);
        assertEquals(registrationDate, user.getRegistrationDate());
    }

    @Test
    public void testGetAndSetGender() {
        user.setGender(User.Gender.MALE);
        assertEquals(User.Gender.MALE, user.getGender());
    }

    @Test
    public void testGetAndSetDni() {
        user.setDni("12345678X");
        assertEquals("12345678X", user.getDni());
    }

    @Test
    public void testGetAndSetProfilePicture() {
        byte[] profilePicture = {1, 2, 3, 4, 5};
        user.setProfilePicture(profilePicture);
        assertArrayEquals(profilePicture, user.getProfilePicture());
    }

    @Test
    public void testGetAndSetBio() {
        user.setBio("This is a bio.");
        assertEquals("This is a bio.", user.getBio());
    }

    @Test
    public void testIsAndSetAdmin() {
        user.setAdmin(true);
        assertTrue(user.isAdmin());
    }

    @Test
    public void testGetAndSetBookings() {
        Booking booking = new Booking();
        List<Booking> bookings = Arrays.asList(booking);
        user.setBookings(bookings);
        assertEquals(bookings, user.getBookings());
    }

    @Test
    public void testGetAndSetPosts() {
        ForumComment post = new ForumComment();
        List<ForumComment> posts = Arrays.asList(post);
        user.setPosts(posts);
        assertEquals(posts, user.getPosts());
    }

    @Test
    public void testGetAndSetReviews() {
        AccommodationReview review = new AccommodationReview();
        List<AccommodationReview> reviews = Arrays.asList(review);
        user.setReviews(reviews);
        assertEquals(reviews, user.getReviews());
    }

    @Test
    public void testGetAndSetAccommodations() {
        Accommodation accommodation = new Accommodation();
        List<Accommodation> accommodations = Arrays.asList(accommodation);
        user.setAccommodations(accommodations);
        assertEquals(accommodations, user.getAccommodations());
    }

    @Test
    public void testEquals() {
        User anotherUserCopy = new User("John", "Doe", "john.doe@example.com", "password123", "1234567890", LocalDate.of(1990, 1, 1), User.Gender.MALE, "12345678X", true);
        assertEquals(anotherUser, anotherUserCopy);
        assertNotEquals(user, anotherUser);
    }

    @Test
    public void testHashCode() {
        User anotherUserCopy = new User("John", "Doe", "john.doe@example.com", "password123", "1234567890", LocalDate.of(1990, 1, 1), User.Gender.MALE, "12345678X", true);
        assertEquals(anotherUser.hashCode(), anotherUserCopy.hashCode());
        assertNotEquals(user.hashCode(), anotherUser.hashCode());
    }

    @Test
    public void testToString() {
        String expected = "User{userId=null, name='John', lastName='Doe', email='john.doe@example.com', password='password123', phone='1234567890', birthDate=1990-01-01, registrationDate=null, gender=MALE, dni='12345678X', profilePicture='null', bio='null', isAdmin=true}";
        assertEquals(expected, anotherUser.toString());
    }
}
