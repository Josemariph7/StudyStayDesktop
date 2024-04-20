package com.example.ejemplo.test;

import com.example.ejemplo.model.Accommodation;
import com.example.ejemplo.model.Booking;
import com.example.ejemplo.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AccommodationTest {

    private Accommodation accommodation;

    @BeforeEach
    public void setUp() {
        accommodation = new Accommodation(1L, null, "123 Main St", 100.0, "Cozy apartment");
    }

    @Test
    public void testAccommodationConstructor() {
        assertNotNull(accommodation);
    }

    @Test
    public void testAccommodationAttributes() {
        assertEquals(1L, accommodation.getAccommodationId());
        assertEquals("123 Main St", accommodation.getAddress());
        assertEquals(100.0, accommodation.getPrice());
        assertEquals("Cozy apartment", accommodation.getDescription());
    }

    @Test
    public void testSettersAndGetters() {
        User owner = new User();
        owner.setName("Eustaquio");
        accommodation.setOwner(owner);
        assertEquals(owner, accommodation.getOwner());

        accommodation.setAddress("456 Elm St");
        assertEquals("456 Elm St", accommodation.getAddress());

        accommodation.setPrice(150.0);
        assertEquals(150.0, accommodation.getPrice());

        accommodation.setDescription("Spacious house");
        assertEquals("Spacious house", accommodation.getDescription());

        List<Booking> bookings = new ArrayList<>();
        accommodation.setBookings(bookings);
        assertEquals(bookings, accommodation.getBookings());

        List<Review> reviews = new ArrayList<>();
        accommodation.setReviews(reviews);
        assertEquals(reviews, accommodation.getReviews());
    }

    @Test
    public void testEqualsAndHashCode() {
        Accommodation sameAccommodation = new Accommodation(1L, null, "123 Main St", 100.0, "Cozy apartment");
        Accommodation differentAccommodation = new Accommodation(2L, null, "456 Elm St", 150.0, "Spacious house");

        assertEquals(accommodation, sameAccommodation);
        assertEquals(accommodation.hashCode(), sameAccommodation.hashCode());
        assertNotEquals(accommodation, differentAccommodation);
        assertNotEquals(accommodation.hashCode(), differentAccommodation.hashCode());
    }

    @Test
    public void testToString() {
        String expectedString = "Accommodation{" +
                "accommodationId=1, " +
                "owner=null, " +
                "address='123 Main St', " +
                "price=100.0, " +
                "description='Cozy apartment'" +
                "}";
        assertEquals(expectedString, accommodation.toString());
    }
}
