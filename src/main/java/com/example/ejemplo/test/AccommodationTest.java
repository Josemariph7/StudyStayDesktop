package com.example.ejemplo.test;

import com.example.ejemplo.model.Accommodation;
import com.example.ejemplo.model.AccommodationPhoto;
import com.example.ejemplo.model.AccommodationReview;
import com.example.ejemplo.model.Booking;
import com.example.ejemplo.model.User;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class AccommodationTest {

    private Accommodation accommodation;
    private User owner;

    @Before
    public void setUp() {
        owner = new User("John", "Doe", "john.doe@example.com", "password123", "1234567890", null, User.Gender.MALE, "12345678X", true);
        accommodation = new Accommodation(owner, "123 Main St", "Cityville", BigDecimal.valueOf(1000.0), "Nice place", 4, "WiFi, Parking");
    }

    @Test
    public void testGetAndSetAccommodationId() {
        accommodation.setAccommodationId(1L);
        assertEquals(Optional.of(1L), Optional.ofNullable(accommodation.getAccommodationId()));
    }

    @Test
    public void testGetAndSetOwner() {
        accommodation.setOwner(owner);
        assertEquals(owner, accommodation.getOwner());
    }

    @Test
    public void testGetAndSetAddress() {
        accommodation.setAddress("123 Main St");
        assertEquals("123 Main St", accommodation.getAddress());
    }

    @Test
    public void testGetAndSetCity() {
        accommodation.setCity("Cityville");
        assertEquals("Cityville", accommodation.getCity());
    }

    @Test
    public void testGetAndSetPrice() {
        BigDecimal price = BigDecimal.valueOf(1000.0);
        accommodation.setPrice(price);
        assertEquals(price, accommodation.getPrice());
    }

    @Test
    public void testGetAndSetDescription() {
        accommodation.setDescription("Nice place");
        assertEquals("Nice place", accommodation.getDescription());
    }

    @Test
    public void testGetAndSetCapacity() {
        accommodation.setCapacity(4);
        assertEquals(4, accommodation.getCapacity());
    }

    @Test
    public void testGetAndSetServices() {
        accommodation.setServices("WiFi, Parking");
        assertEquals("WiFi, Parking", accommodation.getServices());
    }

    @Test
    public void testIsAndSetAvailability() {
        accommodation.setAvailability(true);
        assertTrue(accommodation.isAvailability());
    }

    @Test
    public void testGetAndSetRating() {
        accommodation.setRating(4.5);
        assertEquals(4.5, accommodation.getRating(), 0.01);
    }

    @Test
    public void testGetAndSetBookings() {
        Booking booking = new Booking();
        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);
        accommodation.setBookings(bookings);
        assertEquals(bookings, accommodation.getBookings());
    }

    @Test
    public void testGetAndSetReviews() {
        AccommodationReview review = new AccommodationReview();
        List<AccommodationReview> reviews = new ArrayList<>();
        reviews.add(review);
        accommodation.setReviews(reviews);
        assertEquals(reviews, accommodation.getReviews());
    }

    @Test
    public void testGetAndSetTenants() {
        User tenant = new User();
        List<User> tenants = new ArrayList<>();
        tenants.add(tenant);
        accommodation.setTenants(tenants);
        assertEquals(tenants, accommodation.getTenants());
    }

    @Test
    public void testGetAndSetPhotos() {
        AccommodationPhoto photo = new AccommodationPhoto();
        List<AccommodationPhoto> photos = new ArrayList<>();
        photos.add(photo);
        accommodation.setPhotos(photos);
        assertEquals(photos, accommodation.getPhotos());
    }

    @Test
    public void testAddTenant() {
        User tenant = new User();
        assertTrue(accommodation.addTenant(tenant));
        assertEquals(1, accommodation.getTenants().size());

        accommodation.setCapacity(1);
        assertFalse(accommodation.addTenant(new User())); // Capacity reached
    }

    @Test
    public void testEquals() {
        Accommodation anotherAccommodation = new Accommodation();
        anotherAccommodation.setAccommodationId(1L);
        accommodation.setAccommodationId(1L);
        assertEquals(accommodation, anotherAccommodation);
    }

    @Test
    public void testHashCode() {
        Accommodation anotherAccommodation = new Accommodation();
        anotherAccommodation.setAccommodationId(1L);
        accommodation.setAccommodationId(1L);
        assertEquals(accommodation.hashCode(), anotherAccommodation.hashCode());
    }

    @Test
    public void testToString() {
        String expected = "Accommodation{accommodationId=null, owner=" + owner + ", address='123 Main St', city='Cityville', price=1000.0, description='Nice place', capacity=4, services='WiFi, Parking', availability=true, rating=0.0}";
        assertEquals(expected, accommodation.toString());
    }
}
